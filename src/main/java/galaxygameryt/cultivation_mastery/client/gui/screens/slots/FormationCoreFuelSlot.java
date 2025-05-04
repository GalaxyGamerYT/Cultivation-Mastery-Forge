package galaxygameryt.cultivation_mastery.client.gui.screens.slots;

import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FormationCoreFuelSlot extends SlotItemHandler {
    private final ContainerData data;

    public FormationCoreFuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, ContainerData data) {
        super(itemHandler, index, xPosition, yPosition);
        this.data = data;
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (!this.getItem().isEmpty()) {
            for (int i = 0; i < 7; i++) {
                ItemStack stack = getItemHandler().getStackInSlot(i);
                if (!stack.isEmpty()) {
                    data.set(0, 1);
                    break;
                } else {
                    data.set(0, 0);
                }
            }
        } else {
            data.set(0, 0);
        }
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.is(ModTags.Items.SPIRITUAL_STONES);
    }
}
