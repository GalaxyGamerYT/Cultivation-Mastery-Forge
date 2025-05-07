package galaxygameryt.cultivation_mastery.block.entity.custom;

import galaxygameryt.cultivation_mastery.block.custom.FormationCoreBlock;
import galaxygameryt.cultivation_mastery.block.entity.ModBlockEntities;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.formation_core.FormationCoreMenu;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.util.Logger;
import galaxygameryt.cultivation_mastery.util.helpers.RuneEffectResolver;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class FormationCoreBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(9);

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int active = 0;
    private int levelInt = 0;

    private final List<MobEffectInstance> effects = new ArrayList<>();

    public FormationCoreBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FORMATION_CORE_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                if (index == 0) return FormationCoreBlockEntity.this.active;
                if (index == 1) return FormationCoreBlockEntity.this.levelInt;
                return 0;
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    FormationCoreBlockEntity.this.active = value;
                }
                if (index == 1) {
                    FormationCoreBlockEntity.this.levelInt = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public boolean isActive() {
        return this.active != 0;
    }

    public int getLevelInt() {
        return this.levelInt;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (level != null) {
        }
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("menu.title.cultivation_mastery.formation_core");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new FormationCoreMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("formation_core.active", active);

        ListTag listTag = new ListTag();
        for (MobEffectInstance effect : effects) {
            CompoundTag effectTag = new CompoundTag();
            effect.save(effectTag);
            listTag.add(effectTag);
        }
        tag.put("effects", listTag);
        tag.putInt("levelInt", levelInt);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        active = tag.getInt("formation_core.active");

        effects.clear();
        if (tag.contains("effects", Tag.TAG_LIST)) {
            ListTag listTag = tag.getList("effects", Tag.TAG_COMPOUND);
            for (Tag t : listTag) {
                CompoundTag effectTag = (CompoundTag) t;
                effects.add(MobEffectInstance.load(effectTag));
            }
        }
        levelInt = tag.getInt("levelInt");
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    public void ServerTick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        ItemStack spiritStoneStack = itemHandler.getStackInSlot(8);
        if (spiritStoneStack.is(ModItems.LOW_SPIRIT_STONE.get())) this.data.set(1,1);
        if (spiritStoneStack.is(ModItems.MEDIUM_SPIRIT_STONE.get())) this.data.set(1,2);
        if (spiritStoneStack.is(ModItems.HIGH_SPIRIT_STONE.get())) this.data.set(1,3);
        if (spiritStoneStack.is(ModItems.IMMORTAL_SPIRIT_STONE.get())) this.data.set(1,4);

        evaluateRunesAndSetEffects();

        List<Player> playersInRange = level.getEntitiesOfClass(Player.class, AABB.of(new BoundingBox(pos).inflatedBy(5*levelInt)));
        if (!playersInRange.isEmpty() && isActive()) {
            playersInRange.forEach(player -> {
                for (MobEffectInstance instance : effects) {
                    MobEffectInstance existing = player.getEffect(instance.getEffect());
                    if (existing == null || existing.getDuration() < Math.max(instance.getDuration()-50, 200)) {
                        if (existing != null) {
                            player.removeEffect(existing.getEffect());
                        }
                        player.addEffect(instance);
                    }
                }
            });
        }

        boolean active = isActive();
        if (active != state.getValue(FormationCoreBlock.ACTIVE_STATE)) {
            level.setBlock(pos, state.setValue(FormationCoreBlock.ACTIVE_STATE, active), 2);
        }
    }

    public void ClientTick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) return;

        if (state.getValue(FormationCoreBlock.ACTIVE_STATE)) spawnParticles(level, pos);
    }

    private void spawnParticles(Level level, BlockPos pos) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5;

        RandomSource random = level.random;
        int randTick = random.nextIntBetweenInclusive(1, 10);
        if (randTick > 3 && randTick < 6) {
            level.addParticle(ParticleTypes.END_ROD,
                    x + (Math.random() - 0.5),
                    y + Math.random(),
                    z + (Math.random() - 0.5),
                    0, 0.02, 0
            );
        }
        if (randTick > 5 && randTick < 8) {
            level.addParticle(ParticleTypes.ENCHANT,
                    x + (Math.random() - 0.5),
                    y + Math.random(),
                    z + (Math.random() - 0.5),
                    0, 0.02, 0
            );
        }
    }

    public void setEffects(List<MobEffectInstance> newEffects) {
        effects.clear();
        effects.addAll(newEffects);
        setChanged();
    }

    public List<MobEffectInstance> getEffects() {
        return effects;
    }

    public void evaluateRunesAndSetEffects() {
        List<ItemStack> runes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            runes.add(itemHandler.getStackInSlot(i));
        }

        List<MobEffectInstance> newEffects = RuneEffectResolver.resolveEffectsFromRunes(runes, itemHandler.getStackInSlot(8), this.levelInt);
        setEffects(newEffects);
    }
}
