package galaxygameryt.cultivation_mastery.recipe.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.RuneStoneItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RuneInscribingRecipe implements Recipe<Container> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack result;
    private final ResourceLocation id;

    public RuneInscribingRecipe(NonNullList<Ingredient> inputItems, ItemStack result, ResourceLocation id) {
        this.inputItems = inputItems;
        this.result = result;
        this.id = id;
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        ItemStack ingredient = container.getItem(0);
        ItemStack rune = container.getItem(1);
        return this.inputItems.get(0).test(ingredient) && this.inputItems.get(1).test(rune);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess registryAccess) {
        return getResultItem(registryAccess);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        ItemStack stack = result.copy();
        if (stack.getItem() instanceof RuneStoneItem runeItem) {
            RuneStoneItem.setAttribute(stack, runeItem.getAttribute());
        }
        return stack;
    }

    public NonNullList<Ingredient> getInputItems() {
        return inputItems;
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
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(serializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new RuneInscribingRecipe(inputs, result, recipeId);
        }

        @Override
        public @Nullable RuneInscribingRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();
            return new RuneInscribingRecipe(inputs, output, recipeId);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RuneInscribingRecipe recipe) {
            buffer.writeInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }
}
