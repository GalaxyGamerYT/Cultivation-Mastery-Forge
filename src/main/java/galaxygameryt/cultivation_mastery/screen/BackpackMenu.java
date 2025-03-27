package galaxygameryt.cultivation_mastery.screen;

import galaxygameryt.cultivation_mastery.item.custom.BackpackItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BackpackMenu extends AbstractContainerMenu {
    private final SimpleContainer inventory;
    private final ItemStack backpackStack;

    public BackpackMenu(int id, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(id, inventory, new SimpleContainer(27), ItemStack.EMPTY);
    }

    public BackpackMenu(int id, Inventory playerInventory, SimpleContainer inventory, ItemStack stack) {
        super(ModMenuTypes.BACKPACK_MENU.get(), id);
        this.inventory = inventory;
        this.backpackStack = stack;

        // Add inventory slots (3 rows of 9 slots)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        // Add player inventory slots
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 86 + row * 18));
            }
        }

        // Add player hotbar slots
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 144));
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            BackpackItem.saveInventory(backpackStack, inventory); // Save when closing
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    public static class Provider implements MenuProvider {
        private final SimpleContainer inventory;
        private final ItemStack stack;
        private final Component title;

        public Provider(SimpleContainer inventory, ItemStack stack, Component title) {
            this.inventory = inventory;
            this.stack = stack;
            this.title = title;
        }

        @Override
        public BackpackMenu createMenu(int id, Inventory playerInventory, Player player) {
            return new BackpackMenu(id, playerInventory, inventory, stack);
        }

        @Override
        public Component getDisplayName() {
            return title;
        }
    }
}