package galaxygameryt.cultivation_mastery.compat.jei;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.RuneStoneItem;
import galaxygameryt.cultivation_mastery.recipe.custom.RuneInscribingRecipe;
import galaxygameryt.cultivation_mastery.util.Logger;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RuneInscribingCategory implements IRecipeCategory<RuneInscribingRecipe>  {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID,
            "textures/gui/containers/rune_inscribing_table");

    private final IDrawable background;
    private final IDrawable icon;

    public RuneInscribingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.RUNE_INSCRIBING_TABLE.get()));
    }

    @Override
    public @NotNull RecipeType<RuneInscribingRecipe> getRecipeType() {
        return RuneInscribingRecipe.Type.JEI_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.cultivation_mastery.rune_inscribing_table");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull RuneInscribingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 10).addIngredients(recipe.getIngredients().get(0));

        ItemStack outputStack = new ItemStack(recipe.getResultItem(RegistryAccess.EMPTY).getItem());
        if (outputStack.getItem() instanceof RuneStoneItem runeStone) {
            RuneStoneItem.setAttribute(outputStack, runeStone.getAttribute());
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 10).addItemStack(outputStack);
    }
}
