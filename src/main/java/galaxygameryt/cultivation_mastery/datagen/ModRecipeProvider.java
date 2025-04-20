package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
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
        // Ores
        oreSmelting(pWriter, LOW_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.LOW_SPIRIT_STONE.get(), 0.25f, 200, "low_spirit_stone");
        oreBlasting(pWriter, LOW_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.LOW_SPIRIT_STONE.get(), 0.25f, 100, "low_spirit_stone");
        oreSmelting(pWriter, MEDIUM_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.MEDIUM_SPIRIT_STONE.get(), 0.25f, 200, "medium_spirit_stone");
        oreBlasting(pWriter, MEDIUM_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.MEDIUM_SPIRIT_STONE.get(), 0.25f, 100, "medium_spirit_stone");
        oreSmelting(pWriter, HIGH_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.HIGH_SPIRIT_STONE.get(), 0.25f, 200, "high_spirit_stone");
        oreBlasting(pWriter, HIGH_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.HIGH_SPIRIT_STONE.get(), 0.25f, 100, "high_spirit_stone");

        // Spiritual Stones
        packableRecipes(pWriter, RecipeCategory.MISC, ModItems.LOW_SPIRIT_STONE.get(), ModItems.MEDIUM_SPIRIT_STONE.get());
        packableRecipes(pWriter, RecipeCategory.MISC, ModItems.MEDIUM_SPIRIT_STONE.get(), ModItems.HIGH_SPIRIT_STONE.get());

        // Spiritual Iron
        packableRecipes(pWriter, RecipeCategory.MISC, ModItems.SPIRITUAL_IRON_INGOT.get(), ModBlocks.SPIRITUAL_IRON_BLOCK.get());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SPIRITUAL_IRON_INGOT.get(), 2)
                .requires(ModItems.LOW_SPIRIT_STONE.get())
                .requires(Items.IRON_INGOT, 2)
                .unlockedBy("has_spritual_stone", has(ModTags.Items.SPIRITUAL_STONES))
                .save(pWriter);

        // Spiritual Mirror
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPIRITUAL_MIRROR.get())
                .pattern("DSD")
                .pattern("SGS")
                .pattern("DSD")
                .define('D', Items.DIAMOND)
                .define('S', ModItems.LOW_SPIRIT_STONE.get())
                .define('G', Items.GLASS)
                .unlockedBy("has_spritual_stone", has(ModTags.Items.SPIRITUAL_STONES))
                .save(pWriter);

        // Space Ring
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPACE_RING.get())
                .pattern("SI ")
                .pattern("I I")
                .pattern(" I ")
                .define('I', Items.IRON_INGOT)
                .define('S', ModItems.MEDIUM_SPIRIT_STONE.get())
                .unlockedBy("has_spritual_stone", has(ModTags.Items.SPIRITUAL_STONES))
                .save(pWriter);

        // Training Posts
        // Basic Training Posts
        basicTrainingPostRecipes(pWriter, ModBlocks.OAK_TRAINING_POST.get(), Blocks.OAK_PLANKS, Blocks.OAK_LOG);
        basicTrainingPostRecipes(pWriter, ModBlocks.SPRUCE_TRAINING_POST.get(), Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LOG);
        basicTrainingPostRecipes(pWriter, ModBlocks.BIRCH_TRAINING_POST.get(), Blocks.BIRCH_PLANKS, Blocks.BIRCH_LOG);
        basicTrainingPostRecipes(pWriter, ModBlocks.CHERRY_TRAINING_POST.get(), Blocks.CHERRY_PLANKS, Blocks.CHERRY_LOG);
        basicTrainingPostRecipes(pWriter, ModBlocks.ACACIA_TRAINING_POST.get(), Blocks.ACACIA_PLANKS, Blocks.ACACIA_LOG);
        basicTrainingPostRecipes(pWriter, ModBlocks.DARK_OAK_TRAINING_POST.get(), Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_LOG);
        basicTrainingPostRecipes(pWriter, ModBlocks.MANGROVE_TRAINING_POST.get(), Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_LOG);
        basicTrainingPostRecipes(pWriter, ModBlocks.JUNGLE_TRAINING_POST.get(), Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_LOG);

        // Spiritual Iron Training Post
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModBlocks.SPIRITUAL_IRON_TRAINING_POST.get())
                .pattern(" B ")
                .pattern(" B ")
                .pattern("WTW")
                .define('B', ModBlocks.SPIRITUAL_IRON_BLOCK.get())
                .define('W', Blocks.OAK_PLANKS)
                .define('T', ModTags.Items.WOOD_TRAINING_POSTS)
                .unlockedBy(getHasName(ModBlocks.SPIRITUAL_IRON_BLOCK.get()), has(ModBlocks.SPIRITUAL_IRON_BLOCK.get()))
                .save(pWriter);


    }

    protected static void basicTrainingPostRecipes(Consumer<FinishedRecipe> pWriter, ItemLike outputItem, ItemLike baseItem, ItemLike pillarItem) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, outputItem)
                .pattern(" P ")
                .pattern(" P ")
                .pattern("BBB")
                .define('B', baseItem)
                .define('P', pillarItem)
                .unlockedBy("has_logs", has(ItemTags.LOGS))
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
