package galaxygameryt.cultivation_mastery.item.custom.rune_stones;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ICreativeRuneStone {
    int getLevelColor();

    void fillCreativeTab(List<ItemStack> items);
}
