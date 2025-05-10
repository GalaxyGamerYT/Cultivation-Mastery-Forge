package galaxygameryt.cultivation_mastery.compat.jei;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuTypes;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.rune_inscribing_table.RuneInscribingTableMenu;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.rune_inscribing_table.RuneInscribingTableScreen;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.RuneStoneItem;
import galaxygameryt.cultivation_mastery.recipe.custom.RuneInscribingRecipe;
import galaxygameryt.cultivation_mastery.util.Logger;
import galaxygameryt.cultivation_mastery.util.enums.RuneStoneAttributes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
        registration.addRecipeCategories(
                new RuneInscribingCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        if (Minecraft.getInstance().level == null) {
            Logger.warn("JEI: noe level loaded. Skipping recipe registration");
            return;
        }

        // Ingredients
        IIngredientManager ingredientManager = registration.getIngredientManager();

        // Ignore Items
        List<ItemStack> ignoredItems = List.of(
                new ItemStack(ModItems.YIN_YANG.get()),
                new ItemStack(ModItems.ARRAY.get())
        );
        ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, ignoredItems);

        // Add Items
        List<ItemStack> addItems = new ArrayList<>();
        for (RuneStoneAttributes.Basic attr: RuneStoneAttributes.Basic.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.BASIC_RUNE_STONE.get(), attr.name());
            addItems.add(stack);
        }
        for (RuneStoneAttributes.Low attr: RuneStoneAttributes.Low.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.BASIC_RUNE_STONE.get(), attr.name());
            addItems.add(stack);
        }
        for (RuneStoneAttributes.Medium attr: RuneStoneAttributes.Medium.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.BASIC_RUNE_STONE.get(), attr.name());
            addItems.add(stack);
        }
        for (RuneStoneAttributes.High attr: RuneStoneAttributes.High.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.BASIC_RUNE_STONE.get(), attr.name());
            addItems.add(stack);
        }
        for (RuneStoneAttributes.Immortal attr: RuneStoneAttributes.Immortal.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.BASIC_RUNE_STONE.get(), attr.name());
            addItems.add(stack);
        }
        ingredientManager.addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, addItems);

        // Recipes
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<RuneInscribingRecipe> runeInscribingRecipes = recipeManager.getAllRecipesFor(RuneInscribingRecipe.Type.INSTANCE);
        registration.addRecipes(RUNE_INSCRIBING_RECIPE_TYPE, runeInscribingRecipes);
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(RuneInscribingTableScreen.class, 21, 36, 12, 12,
                RUNE_INSCRIBING_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.RUNE_INSCRIBING_TABLE.get()), RUNE_INSCRIBING_RECIPE_TYPE);
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

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ModItems.BASIC_RUNE_STONE.get(),
                (stack, context) -> {
                    if (!RuneStoneItem.getAttribute(stack).isBlank()) {
                        String attr = RuneStoneItem.getAttribute(stack);

                        try {
                            RuneStoneAttributes.Basic.valueOf(attr);
                            return attr;
                        } catch (IllegalArgumentException e) {
                            return IIngredientSubtypeInterpreter.NONE;
                        }
                    }
                    return IIngredientSubtypeInterpreter.NONE;
                });
        registration.registerSubtypeInterpreter(ModItems.LOW_RUNE_STONE.get(),
                (stack, context) -> {
                    if (!RuneStoneItem.getAttribute(stack).isBlank()) {
                        String attr = RuneStoneItem.getAttribute(stack);

                        try {
                            RuneStoneAttributes.Low.valueOf(attr);
                            return attr;
                        } catch (IllegalArgumentException e) {
                            return IIngredientSubtypeInterpreter.NONE;
                        }
                    }
                    return IIngredientSubtypeInterpreter.NONE;
                });
        registration.registerSubtypeInterpreter(ModItems.MEDIUM_RUNE_STONE.get(),
                (stack, context) -> {
                    if (!RuneStoneItem.getAttribute(stack).isBlank()) {
                        String attr = RuneStoneItem.getAttribute(stack);

                        try {
                            RuneStoneAttributes.Medium.valueOf(attr);
                            return attr;
                        } catch (IllegalArgumentException e) {
                            return IIngredientSubtypeInterpreter.NONE;
                        }
                    }
                    return IIngredientSubtypeInterpreter.NONE;
                });
        registration.registerSubtypeInterpreter(ModItems.HIGH_RUNE_STONE.get(),
                (stack, context) -> {
                    if (!RuneStoneItem.getAttribute(stack).isBlank()) {
                        String attr = RuneStoneItem.getAttribute(stack);

                        try {
                            RuneStoneAttributes.High.valueOf(attr);
                            return attr;
                        } catch (IllegalArgumentException e) {
                            return IIngredientSubtypeInterpreter.NONE;
                        }
                    }
                    return IIngredientSubtypeInterpreter.NONE;
                });
        registration.registerSubtypeInterpreter(ModItems.IMMORTAL_RUNE_STONE.get(),
                (stack, context) -> {
                    if (!RuneStoneItem.getAttribute(stack).isBlank()) {
                        String attr = RuneStoneItem.getAttribute(stack);

                        try {
                            RuneStoneAttributes.Immortal.valueOf(attr);
                            return attr;
                        } catch (IllegalArgumentException e) {
                            return IIngredientSubtypeInterpreter.NONE;
                        }
                    }
                    return IIngredientSubtypeInterpreter.NONE;
                });
    }
}
