package galaxygameryt.cultivation_mastery.client.gui.screens.custom.formation_core;

import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.block.entity.custom.FormationCoreBlockEntity;
import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuTypes;
import galaxygameryt.cultivation_mastery.client.gui.screens.slots.FormationCoreFuelSlot;
import galaxygameryt.cultivation_mastery.client.gui.screens.slots.FormationCoreSlot;
import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class FormationCoreMenu extends AbstractContainerMenu {
    public final FormationCoreBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public FormationCoreMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public FormationCoreMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.FORMATION_CORE_MENU.get(), containerId);
//        checkContainerSize(inv, 9);
        blockEntity = (FormationCoreBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addSlots();
        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        addDataSlots(data);
    }

    public int getActive() {
        return data.get(0);
    }

    public int getLevelInt() {
        return data.get(1);
    }

    public static final int RUNE_SLOT_START= 0;
    public static final int FUEL_SLOT_INDEX = 8;
    public static final int INV_SLOT_START = 9;

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack originalStack = slot.getItem();
        ItemStack stackToMove = originalStack.copy();

        if (index < 9) {
            if (!this.moveItemStackTo(originalStack, INV_SLOT_START, this.slots.size(), false)) {
                return ItemStack.EMPTY;
            }
        }

        else {
            if (originalStack.is(ModTags.Items.USABLE_RUNE_STONES)) {
                if (!this.moveItemStackTo(originalStack, RUNE_SLOT_START, FUEL_SLOT_INDEX, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (originalStack.is(ModTags.Items.SPIRITUAL_STONES)) {
                if (!this.moveItemStackTo(originalStack, FUEL_SLOT_INDEX, INV_SLOT_START, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        }

        if (originalStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (originalStack.getCount() == stackToMove.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, originalStack);
        return stackToMove;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.FORMATION_CORE.get());
    }

    private void addSlots() {
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new FormationCoreSlot(iItemHandler, 0, 79, 7, data));
            this.addSlot(new FormationCoreSlot(iItemHandler, 1, 117, 22, data));
            this.addSlot(new FormationCoreSlot(iItemHandler, 2, 132, 60, data));
            this.addSlot(new FormationCoreSlot(iItemHandler, 3, 117, 98, data));
            this.addSlot(new FormationCoreSlot(iItemHandler, 4, 79, 113, data));
            this.addSlot(new FormationCoreSlot(iItemHandler, 5, 41, 98, data));
            this.addSlot(new FormationCoreSlot(iItemHandler, 6, 26, 60, data));
            this.addSlot(new FormationCoreSlot(iItemHandler, 7, 41, 22, data));
            this.addSlot(new FormationCoreFuelSlot(iItemHandler, 8, 79, 60, data));
        });
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 135 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 193));
        }
    }
}
