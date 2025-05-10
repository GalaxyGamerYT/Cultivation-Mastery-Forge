package galaxygameryt.cultivation_mastery.item.custom.rune_stones;

import galaxygameryt.cultivation_mastery.util.enums.RuneStoneAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MediumRuneStoneItem extends RuneStoneItem {
    public MediumRuneStoneItem(Properties properties) {
        this(properties, "");
    }

    public MediumRuneStoneItem(Properties properties, String attribute) {
        super(properties, attribute);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        tooltipComponents.add(Component.translatable("item.cultivation_mastery.medium_rune_stone.tooltip"));
    }

    @Override
    protected String getLevelPrefix() {
        return "Medium";
    }

    @Override
    public int getLevelColor() {
        return 0xfecd28;
    }
}
