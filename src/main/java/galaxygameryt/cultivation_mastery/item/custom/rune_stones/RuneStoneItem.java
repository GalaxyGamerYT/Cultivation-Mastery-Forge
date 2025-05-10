package galaxygameryt.cultivation_mastery.item.custom.rune_stones;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RuneStoneItem extends Item implements ICreativeRuneStone {
    private static final String ATTRIBUTE_KEY = "attribute";
    private String attribute = null;

    public RuneStoneItem(Properties properties) {
        this(properties, "");
    }

    public RuneStoneItem(Properties properties, String attribute) {
        super(properties);
        this.attribute = sanitize(attribute);
    }

    public void setAttribute(String attribute) {
        RuneStoneItem.setAttribute(new ItemStack(this), sanitize(attribute));
    }

    public String getAttribute() {
        return attribute;
    }

    public static void setAttribute(ItemStack stack, String attribute) {
        stack.getOrCreateTag().putString(ATTRIBUTE_KEY, sanitize(attribute));
    }

    private static String sanitize(String input) {
        return input.replaceAll("[^\\x20-\\x7E]", "");
    }

    public static String getAttribute(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains(ATTRIBUTE_KEY)) {
            return stack.getTag().getString(ATTRIBUTE_KEY);
        }
        return "";
    }

    public static ItemStack createWithAttribute(RuneStoneItem runeStone, String attribute) {
        ItemStack stack = new ItemStack(runeStone);
        RuneStoneItem.setAttribute(stack, attribute);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        tooltipComponents.add(Component.translatable("item.cultivation_mastery.rune_stone.tooltip"));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        String attributeName = getAttribute(stack);

        if (!hasAttribute(stack)) {
            return Component.translatable(this.getDescriptionId(stack)).withStyle(Style.EMPTY.withColor(getLevelColor()));
        }

        String prefix = getLevelPrefix();
        String readableAttribute = formatAttributeName(attributeName);

        MutableComponent name = Component.translatable("item.cultivation_mastery.rune_stone.named", prefix, readableAttribute);

        return name.setStyle(Style.EMPTY.withColor(getLevelColor()));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return hasAttribute(stack);
    }

    public boolean hasAttribute(ItemStack stack) {
        return !getAttribute(stack).isEmpty();
    }

    protected String getLevelPrefix() {
        return "";
    }

    @Override
    public int getLevelColor() {
        return 0xFFFFFF;
    }

    private String formatAttributeName(String rawName) {
        String[] parts = rawName.toLowerCase().split("_");
        return Arrays.stream(parts)
                .map(part -> part.substring(0, 1).toUpperCase() + part.substring(1))
                .collect(Collectors.joining(" "));
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return hasAttribute(stack) ? 1 : 64;
    }
}
