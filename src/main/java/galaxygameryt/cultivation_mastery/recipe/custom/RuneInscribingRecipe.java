package galaxygameryt.cultivation_mastery.recipe.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.*;
import galaxygameryt.cultivation_mastery.util.Logger;
import me.lucko.spark.lib.adventure.text.TextComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RuneInscribingRecipe implements Recipe<Container> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack result;
    private final ResourceLocation id;

    private final String attribute;

    private final int baseItemIndex = 0;
    private final int inscribingItemIndex = 1;

    public RuneInscribingRecipe(NonNullList<Ingredient> inputItems, ItemStack result, ResourceLocation id, String attribute) {
        this.inputItems = inputItems;
        this.result = result;
        this.id = id;
        this.attribute = attribute;
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        ItemStack baseItem = container.getItem(baseItemIndex);
        ItemStack inscribingItem = container.getItem(inscribingItemIndex);
        return this.inputItems.get(baseItemIndex).test(baseItem) && this.inputItems.get(inscribingItemIndex).test(inscribingItem);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess registryAccess) {
        return getResultItem(RegistryAccess.EMPTY);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        ItemStack stack = result.copy();
        if (stack.getItem() instanceof RuneStoneItem runeItem) {
            RuneStoneItem.setAttribute(stack, attribute);
        }
        return stack;
    }

    public NonNullList<Ingredient> getInputItems() {
        return inputItems;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return getInputItems();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<RuneInscribingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "rune_inscribing";
    }

    public static class Serializer implements RecipeSerializer<RuneInscribingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "rune_inscribing");

        @Override
        public @NotNull RuneInscribingRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject serializedRecipe) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(serializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));

            String attribute = GsonHelper.getAsString(serializedRecipe, "attribute");

            return new RuneInscribingRecipe(inputs, result, recipeId, attribute);
        }

        @Override
        public @Nullable RuneInscribingRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
            int size = buffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);

            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();

            String attribute = buffer.readUtf();

            return new RuneInscribingRecipe(inputs, output, recipeId, attribute);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RuneInscribingRecipe recipe) {
            buffer.writeInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.getInputItems()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItemStack(recipe.result, false);
            buffer.writeUtf(recipe.attribute);
        }
    }
}
