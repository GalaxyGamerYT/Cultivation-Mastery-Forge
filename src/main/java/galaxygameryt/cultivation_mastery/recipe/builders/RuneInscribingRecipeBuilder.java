package galaxygameryt.cultivation_mastery.recipe.builders;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import galaxygameryt.cultivation_mastery.recipe.ModRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.CraftingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class RuneInscribingRecipeBuilder extends CraftingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private Ingredient baseItem = Ingredient.EMPTY;
    private Ingredient inscribingItem = Ingredient.EMPTY;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private String group;

    public RuneInscribingRecipeBuilder(RecipeCategory category, Item result, int count) {
        this.category = category;
        this.result = result;
        this.count = count;
    }

    public static RuneInscribingRecipeBuilder runeInscribing(RecipeCategory category, Item result) {
        return new RuneInscribingRecipeBuilder(category, result, 1);
    }

    public static RuneInscribingRecipeBuilder runeInscribing(RecipeCategory category, Item result, int count) {
        return new RuneInscribingRecipeBuilder(category, result, count);
    }

    public RuneInscribingRecipeBuilder inscriber(TagKey<Item> tag) {
        return this.inscriber(Ingredient.of(tag));
    }

    public RuneInscribingRecipeBuilder inscriber(ItemLike item) {
        return this.inscriber(Ingredient.of(item));
    }

    public RuneInscribingRecipeBuilder inscriber(Ingredient ingredient) {
        this.inscribingItem = ingredient;
        return this;
    }

    public RuneInscribingRecipeBuilder base(TagKey<Item> tag) {
        return this.base(Ingredient.of(tag));
    }

    public RuneInscribingRecipeBuilder base(ItemLike item) {
        return this.base(Ingredient.of(item));
    }

    public RuneInscribingRecipeBuilder base(Ingredient ingredient) {
        this.baseItem = ingredient;
        return this;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String criterionName, @NotNull CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return this.result;
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer, @NotNull ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT)
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new RuneInscribingRecipeBuilder.Result(recipeId, this.result, this.count,
                this.group == null ? "" : this.group, determineBookCategory(this.category), this.baseItem, this.inscribingItem,
                this.advancement, recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result extends CraftingRecipeBuilder.CraftingResult {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final String group;
        private final Ingredient baseItem;
        private final Ingredient inscribingItem;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        protected Result(ResourceLocation id, Item result, int count, String group, CraftingBookCategory category, Ingredient baseItem, Ingredient inscribingItem, Advancement.Builder advancement, ResourceLocation advancementId) {
            super(category);
            this.id = id;
            this.result = result;
            this.count = count;
            this.group = group;
            this.baseItem = baseItem;
            this.inscribingItem = inscribingItem;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
            super.serializeRecipeData(json);
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            JsonArray jsonArray = new JsonArray();

            for (Ingredient ingredient : List.of(baseItem, inscribingItem)) {
                jsonArray.add(ingredient.toJson());
            }

            json.add("ingredients", jsonArray);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) {
                jsonObject.addProperty("count", this.count);
            }

            json.add("result", jsonObject);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return ModRecipes.Serializers.RUNE_INSCRIBING_TABLE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
