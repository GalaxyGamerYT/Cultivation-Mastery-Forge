package galaxygameryt.cultivation_mastery.compat.jei;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuTypes;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.rune_inscribing_table.RuneInscribingTableMenu;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.rune_inscribing_table.RuneInscribingTableScreen;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.recipe.custom.RuneInscribingRecipe;
import galaxygameryt.cultivation_mastery.util.Logger;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

@JeiPlugin
public class JEICultivationMasteryPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_UID = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "jei_plugin");

    public static final RecipeType<RuneInscribingRecipe> RUNE_INSCRIBING_RECIPE_TYPE =
            RecipeType.create(CultivationMastery.MOD_ID, "rune_inscribing", RuneInscribingRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        Logger.info("BOB!!!! - Categories registering");
        registration.addRecipeCategories(
                new RuneInscribingCategory(registration.getJeiHelpers().getGuiHelper())
        );
        Logger.info("BOB!!!! - Categories registered");
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        Logger.info("BOB!!!! - Recipes registering");

        if (Minecraft.getInstance().level == null) {
            Logger.warn("JEI: noe level loaded. Skipping recipe registration");
            return;
        }

        List<ItemStack> ignoredItems = List.of(
                new ItemStack(ModItems.YIN_YANG.get())
        );

        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, ignoredItems);

        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<RuneInscribingRecipe> runeInscribingRecipes = recipeManager.getAllRecipesFor(RuneInscribingRecipe.Type.INSTANCE);

        Logger.info("BOB!!!! - Found " + runeInscribingRecipes.size() + " rune inscribing recipes");
        registration.addRecipes(RUNE_INSCRIBING_RECIPE_TYPE, runeInscribingRecipes);
        Logger.info("BOB!!!! - Recipes registered");
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(RuneInscribingTableScreen.class, 21, 36, 12, 12,
                RUNE_INSCRIBING_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        Logger.info("BOB!!!! - Recipe Catalysts registering");
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.RUNE_INSCRIBING_TABLE.get()), RUNE_INSCRIBING_RECIPE_TYPE);
        Logger.info("BOB!!!! - Recipe Catalysts registered");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                RuneInscribingTableMenu.class,
                ModMenuTypes.RUNE_INSCRIBING_TABLE_MENU.get(),
                RUNE_INSCRIBING_RECIPE_TYPE,
                RuneInscribingTableMenu.BASE_ITEM_SLOT_INDEX,
                RuneInscribingTableMenu.INSCRIBING_ITEM_SLOT_INDEX+1,
                RuneInscribingTableMenu.INV_SLOT_START,
                36
        );
    }
}
