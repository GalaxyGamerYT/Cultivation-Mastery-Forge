package galaxygameryt.cultivation_mastery.client.gui.screens.slots;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ProtectedSlot extends Slot {
    private final int blockedSlot;

    public ProtectedSlot(Container container, int slot, int x, int y, int blockedSlot) {
        super(container, slot, x, y);
        this.blockedSlot = blockedSlot;
    }

    @Override
    public boolean mayPickup(Player player) {
        return this.getSlotIndex() != blockedSlot && super.mayPickup(player);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.getSlotIndex() != blockedSlot && super.mayPlace(stack);
    }
}
