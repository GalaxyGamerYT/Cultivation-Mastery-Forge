package galaxygameryt.cultivation_mastery.item.custom.rune_stones;

import galaxygameryt.cultivation_mastery.util.enums.RuneStoneAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HighRuneStoneItem extends RuneStoneItem {
    public HighRuneStoneItem(Properties properties) {
        this(properties, "");
    }

    public HighRuneStoneItem(Properties properties, String attribute) {
        super(properties, attribute);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        tooltipComponents.add(Component.translatable("item.cultivation_mastery.high_rune_stone.tooltip"));
    }

    @Override
    protected String getLevelPrefix() {
        return "High";
    }

    @Override
    public int getLevelColor() {
        return 0x0058c4;
    }

    @Override
    public void fillCreativeTabItems(List<ItemStack> items, int index) {
        ItemStack stack = new ItemStack(this);
        setAttribute(stack, RuneStoneAttributes.High.values()[index].name());
        items.add(stack);
    }
}
