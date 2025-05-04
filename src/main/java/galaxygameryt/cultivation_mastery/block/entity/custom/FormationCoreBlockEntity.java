package galaxygameryt.cultivation_mastery.block.entity.custom;

import galaxygameryt.cultivation_mastery.block.custom.FormationCoreBlock;
import galaxygameryt.cultivation_mastery.block.entity.ModBlockEntities;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.formation_core.FormationCoreMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FormationCoreBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(9);

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int active = 0;

    public FormationCoreBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FORMATION_CORE_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                if (index != 0) return 0;
                return FormationCoreBlockEntity.this.active;
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    FormationCoreBlockEntity.this.active = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    public boolean isActive() {
        return this.active != 0;
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
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("formation_core.active", active);

        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        active = tag.getInt("formation_core.active");
    }

    public void ServerTick(Level level, BlockPos pos, BlockState state) {
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
}
