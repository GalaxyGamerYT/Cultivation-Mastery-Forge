package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.RuneStoneItem;
import galaxygameryt.cultivation_mastery.recipe.builders.RuneInscribingRecipeBuilder;
import galaxygameryt.cultivation_mastery.util.Logger;
import galaxygameryt.cultivation_mastery.util.ModTags;
import galaxygameryt.cultivation_mastery.util.enums.RuneStoneAttributes;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

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
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        // Spiritual Stones
        packableRecipe(pWriter, RecipeCategory.MISC, ModItems.LOW_SPIRIT_STONE.get(), ModItems.MEDIUM_SPIRIT_STONE.get());
        packableRecipe(pWriter, RecipeCategory.MISC, ModItems.MEDIUM_SPIRIT_STONE.get(), ModItems.HIGH_SPIRIT_STONE.get());

        // Spiritual Iron
        packableRecipe(pWriter, RecipeCategory.MISC, ModItems.SPIRITUAL_IRON_INGOT.get(), ModBlocks.SPIRITUAL_IRON_BLOCK.get());
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

        buildOreRecipes(pWriter);
        buildTrainingPostRecipes(pWriter);
        buildRuneRecipes(pWriter);
    }

    private void buildOreRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, LOW_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.LOW_SPIRIT_STONE.get(), 0.25f, 200, "low_spirit_stone");
        oreBlasting(pWriter, LOW_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.LOW_SPIRIT_STONE.get(), 0.25f, 100, "low_spirit_stone");
        oreSmelting(pWriter, MEDIUM_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.MEDIUM_SPIRIT_STONE.get(), 0.25f, 200, "medium_spirit_stone");
        oreBlasting(pWriter, MEDIUM_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.MEDIUM_SPIRIT_STONE.get(), 0.25f, 100, "medium_spirit_stone");
        oreSmelting(pWriter, HIGH_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.HIGH_SPIRIT_STONE.get(), 0.25f, 200, "high_spirit_stone");
        oreBlasting(pWriter, HIGH_SPIRIT_STONE_SMELTABLES, RecipeCategory.MISC, ModItems.HIGH_SPIRIT_STONE.get(), 0.25f, 100, "high_spirit_stone");
    }

    private void buildTrainingPostRecipes(Consumer<FinishedRecipe> pWriter) {
        basicTrainingPostRecipe(pWriter, ModBlocks.OAK_TRAINING_POST.get(), Blocks.OAK_PLANKS, Blocks.OAK_LOG);
        basicTrainingPostRecipe(pWriter, ModBlocks.SPRUCE_TRAINING_POST.get(), Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LOG);
        basicTrainingPostRecipe(pWriter, ModBlocks.BIRCH_TRAINING_POST.get(), Blocks.BIRCH_PLANKS, Blocks.BIRCH_LOG);
        basicTrainingPostRecipe(pWriter, ModBlocks.CHERRY_TRAINING_POST.get(), Blocks.CHERRY_PLANKS, Blocks.CHERRY_LOG);
        basicTrainingPostRecipe(pWriter, ModBlocks.ACACIA_TRAINING_POST.get(), Blocks.ACACIA_PLANKS, Blocks.ACACIA_LOG);
        basicTrainingPostRecipe(pWriter, ModBlocks.DARK_OAK_TRAINING_POST.get(), Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_LOG);
        basicTrainingPostRecipe(pWriter, ModBlocks.MANGROVE_TRAINING_POST.get(), Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_LOG);
        basicTrainingPostRecipe(pWriter, ModBlocks.JUNGLE_TRAINING_POST.get(), Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_LOG);

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

    private void buildRuneRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLANK_RUNE_STONE.get(), 2)
                .requires(Items.CHISELED_STONE_BRICKS)
                .requires(Items.AMETHYST_SHARD)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, getItemName(ModItems.BLANK_RUNE_STONE.get())+"_single"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLANK_RUNE_STONE.get(), 8)
                .pattern(" A ")
                .pattern("ASA")
                .pattern(" A ")
                .define('A', Items.AMETHYST_SHARD)
                .define('S', Items.CHISELED_STONE_BRICKS)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, getItemName(ModItems.BLANK_RUNE_STONE.get())+"_multiple"));

        runeInscribingRecipe(pWriter, ModItems.BASIC_RUNE_STONE.get(), ModItems.BLANK_RUNE_STONE.get(), Items.REDSTONE);
        runeInscribingRecipe(pWriter, ModItems.LOW_RUNE_STONE.get(), ModItems.BLANK_RUNE_STONE.get(), ModItems.LOW_SPIRIT_STONE.get());
        runeInscribingRecipe(pWriter, ModItems.MEDIUM_RUNE_STONE.get(), ModItems.BLANK_RUNE_STONE.get(), ModItems.MEDIUM_SPIRIT_STONE.get());
        runeInscribingRecipe(pWriter, ModItems.HIGH_RUNE_STONE.get(), ModItems.BLANK_RUNE_STONE.get(), ModItems.HIGH_SPIRIT_STONE.get());
        runeInscribingRecipe(pWriter, ModItems.IMMORTAL_RUNE_STONE.get(), ModItems.BLANK_RUNE_STONE.get(), ModItems.IMMORTAL_SPIRIT_STONE.get());

        // BASIC RUNE STONES
        for (RuneStoneAttributes.Basic attr : RuneStoneAttributes.Basic.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.BASIC_RUNE_STONE.get(), attr.name());
            runeInscribingRecipe(pWriter,
                    stack.getItem(),
                    attr.name(),
                    ModItems.BLANK_RUNE_STONE.get(),
                    Items.REDSTONE);
        }

        // LOW RUNE STONES
        for (RuneStoneAttributes.Low attr : RuneStoneAttributes.Low.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.LOW_RUNE_STONE.get(), attr.name());
            runeInscribingRecipe(pWriter,
                    stack.getItem(),
                    attr.name(),
                    ModItems.BLANK_RUNE_STONE.get(),
                    ModItems.LOW_SPIRIT_STONE.get());
        }

        // MEDIUM RUNE STONES
        for (RuneStoneAttributes.Medium attr : RuneStoneAttributes.Medium.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.MEDIUM_RUNE_STONE.get(), attr.name());
            runeInscribingRecipe(pWriter,
                    stack.getItem(),
                    attr.name(),
                    ModItems.BLANK_RUNE_STONE.get(),
                    ModItems.MEDIUM_SPIRIT_STONE.get());
        }

        // HIGH RUNE STONES
        for (RuneStoneAttributes.High attr : RuneStoneAttributes.High.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.HIGH_RUNE_STONE.get(), attr.name());
            runeInscribingRecipe(pWriter,
                    stack.getItem(),
                    attr.name(),
                    ModItems.BLANK_RUNE_STONE.get(),
                    ModItems.HIGH_SPIRIT_STONE.get());
        }

        // IMMORTAL RUNE STONES
        for (RuneStoneAttributes.Immortal attr : RuneStoneAttributes.Immortal.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.IMMORTAL_RUNE_STONE.get(), attr.name());
            runeInscribingRecipe(pWriter,
                    stack.getItem(),
                    attr.name(),
                    ModItems.BLANK_RUNE_STONE.get(),
                    ModItems.IMMORTAL_SPIRIT_STONE.get());
        }

    }

    protected static void runeInscribingRecipe(Consumer<FinishedRecipe> writer, Item result, String attribute, ItemLike baseItem, ItemLike inscribingItem) {
        RuneInscribingRecipeBuilder.runeInscribing(RecipeCategory.MISC, result)
                .base(baseItem)
                .inscriber(inscribingItem)
                .attribute(attribute)
                .unlockedBy(getHasName(inscribingItem), has(inscribingItem))
                .save(writer);
    }

    protected static void runeInscribingRecipe(Consumer<FinishedRecipe> writer, Item result, ItemLike baseItem, ItemLike inscribingItem) {
        runeInscribingRecipe(writer, result, "", baseItem, inscribingItem);
    }

    protected static void basicTrainingPostRecipe(Consumer<FinishedRecipe> pWriter, ItemLike outputItem, ItemLike baseItem, ItemLike pillarItem) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, outputItem)
                .pattern(" P ")
                .pattern(" P ")
                .pattern("BBB")
                .define('B', baseItem)
                .define('P', pillarItem)
                .unlockedBy("has_logs", has(ItemTags.LOGS))
                .save(pWriter);
    }

    protected static void packableRecipe(Consumer<FinishedRecipe> pWriter, RecipeCategory category, ItemLike unpackedItem, ItemLike packedItem) {
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

    protected static void oreSmelting(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTIme, @NotNull String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, CultivationMastery.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
