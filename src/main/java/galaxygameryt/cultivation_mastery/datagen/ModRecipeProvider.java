package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> LOW_SPIRIT_STONE_SMELTABLES = List.of(ModBlocks.LOW_SPIRIT_STONE_ORE.get(),
            ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get());
    private static final List<ItemLike> MEDIUM_SPIRIT_STONE_SMELTABLES = List.of(ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get(),
            ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get());
    private static final List<ItemLike> HIGH_SPIRIT_STONE_SMELTABLES = List.of(ModBlocks.HIGH_SPIRIT_STONE_ORE.get(),
            ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, LOW_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.LOW_SPIRIT_STONE.get(), 0.25f, 200, "low_spirit_stone");
        oreBlasting(pWriter, LOW_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.LOW_SPIRIT_STONE.get(), 0.25f, 100, "low_spirit_stone");
        oreSmelting(pWriter, MEDIUM_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.MEDIUM_SPIRIT_STONE.get(), 0.25f, 200, "medium_spirit_stone");
        oreBlasting(pWriter, MEDIUM_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.MEDIUM_SPIRIT_STONE.get(), 0.25f, 100, "medium_spirit_stone");
        oreSmelting(pWriter, HIGH_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.HIGH_SPIRIT_STONE.get(), 0.25f, 200, "high_spirit_stone");
        oreBlasting(pWriter, HIGH_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.HIGH_SPIRIT_STONE.get(), 0.25f, 100, "high_spirit_stone");

        packableRecipes(pWriter, RecipeCategory.MISC, ModItems.LOW_SPIRIT_STONE.get(), ModItems.MEDIUM_SPIRIT_STONE.get());
        packableRecipes(pWriter, RecipeCategory.MISC, ModItems.MEDIUM_SPIRIT_STONE.get(), ModItems.HIGH_SPIRIT_STONE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPIRITUAL_MIRROR.get())
                .pattern("DSD")
                .pattern("SGS")
                .pattern("DSD")
                .define('D', Items.DIAMOND.asItem())
                .define('S', ModItems.LOW_SPIRIT_STONE.get())
                .define('G', Items.GLASS.asItem())
                .unlockedBy(getHasName(ModItems.LOW_SPIRIT_STONE.get()), has(ModItems.LOW_SPIRIT_STONE.get()))
                .save(pWriter);

    }

    protected static void packableRecipes(Consumer<FinishedRecipe> pWriter, RecipeCategory category, ItemLike unpackedItem, ItemLike packedItem) {
        ShapedRecipeBuilder.shaped(category, packedItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', unpackedItem)
                .unlockedBy(getHasName(unpackedItem), has(unpackedItem))
                .save(pWriter, CultivationMastery.MOD_ID + ":" + getConversionRecipeName(packedItem, unpackedItem));
        ShapelessRecipeBuilder.shapeless(category, unpackedItem, 9)
                .requires(packedItem)
                .unlockedBy(getHasName(packedItem), has(packedItem))
                .save(pWriter, CultivationMastery.MOD_ID + ":" + getConversionRecipeName(unpackedItem, packedItem));
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, CultivationMastery.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
