package galaxygameryt.cultivation_mastery.client.gui.screens.custom;

import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuTypes;
import galaxygameryt.cultivation_mastery.client.gui.screens.slots.ProtectedSlot;
import galaxygameryt.cultivation_mastery.item.custom.ContainerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ContainerMenu extends AbstractContainerMenu {
    public final SimpleContainer inventory;
    public final ItemStack itemStack;
    public final int inventorySize;
    public final int blockedSlot;

    public ContainerMenu(int id, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(id, inventory, new SimpleContainer(27), ItemStack.EMPTY, 27, 0);
    }

    public ContainerMenu(int id, Inventory playerInventory, SimpleContainer inventory, ItemStack stack, int size, int blockedSlot) {
        super(ModMenuTypes.CONTAINER_MENU.get(), id);
        this.inventory = inventory;
        this.itemStack = stack;
        this.inventorySize = size;
        this.blockedSlot = blockedSlot;

        // Add inventory slots (3 rows of 9 slots)
        for (int row = 0; row < inventorySize/9; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        // Add player inventory slots
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Add player hotbar slots
        for (int col = 0; col < 9; col++) {
            this.addSlot(new ProtectedSlot(playerInventory, col, 8 + col * 18, 142, blockedSlot));
        }
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            ContainerItem.saveInventory(itemStack, inventory); // Save when closing
        }
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot clickedSlot = this.slots.get(index);

        if (!clickedSlot.hasItem() || index == blockedSlot) {
            return ItemStack.EMPTY;
        }

        ItemStack originalStack = clickedSlot.getItem();
        ItemStack copyStack = originalStack.copy();

        int playerSlotEnd = this.slots.size();

        if (index < inventorySize) {
            // Moving from container to player inventory
            if (!this.moveItemStackTo(originalStack, inventorySize, playerSlotEnd, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Moving from player inventory to container
            if (!this.moveItemStackTo(originalStack, 0, inventorySize, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (originalStack.isEmpty()) {
            clickedSlot.set(ItemStack.EMPTY);
        } else {
            clickedSlot.setChanged();
        }

        return copyStack;
    }

    public static class Provider implements MenuProvider {
        private final SimpleContainer inventory;
        private final ItemStack stack;
        private final Component title;
        private final int size;
        private final int blockedSlot;

        public Provider(SimpleContainer inventory, ItemStack stack, Component title, int size, int blockedSlot) {
            this.inventory = inventory;
            this.stack = stack;
            this.title = title;
            this.size = size;
            this.blockedSlot = blockedSlot;
        }

        @Override
        public ContainerMenu createMenu(int id, @NotNull Inventory playerInventory, @NotNull Player player) {
            return new ContainerMenu(id, playerInventory, inventory, stack, size, blockedSlot);
        }

        @Override
        public @NotNull Component getDisplayName() {
            return title;
        }
    }
}