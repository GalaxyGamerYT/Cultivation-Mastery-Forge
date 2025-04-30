package galaxygameryt.cultivation_mastery.compat.jei;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.rune_inscribing_table.RuneInscribingTableScreen;
import galaxygameryt.cultivation_mastery.recipe.custom.RuneInscribingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class JEICultivationMasteryPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_UID = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "jei_plugin");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new RuneInscribingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<RuneInscribingRecipe> runeInscribingRecipes = recipeManager.getAllRecipesFor(RuneInscribingRecipe.Type.INSTANCE);
        registration.addRecipes(RuneInscribingRecipe.Type.JEI_TYPE, runeInscribingRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(RuneInscribingTableScreen.class, 60, 30, 20, 30,
                RuneInscribingRecipe.Type.JEI_TYPE);
    }
}
