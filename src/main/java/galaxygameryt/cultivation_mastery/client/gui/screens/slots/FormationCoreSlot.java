package galaxygameryt.cultivation_mastery.client.gui.screens.slots;

import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FormationCoreSlot extends SlotItemHandler {
    private final ContainerData data;


    public FormationCoreSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, ContainerData data) {
        super(itemHandler, index, xPosition, yPosition);
        this.data = data;
    }

    public int getData() {
        return this.data.get(0);
    }

    public void setData(int value) {
        this.data.set(0, value);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.is(ModTags.Items.USABLE_RUNE_STONES);
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (!this.getItem().isEmpty()) {
            ItemStack stack = getItemHandler().getStackInSlot(8);
            if (stack.isEmpty()) {
                setData(0);
            } else {
                setData(1);
            }
        } else {
            for (int i = 0; i < 7; i++) {
                ItemStack stack = getItemHandler().getStackInSlot(i);
                if (!stack.isEmpty()) {
                    setData(1);
                    break;
                } else {
                    setData(0);
                }
            }
        }
    }
}
