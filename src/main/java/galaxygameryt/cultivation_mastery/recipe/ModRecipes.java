package galaxygameryt.cultivation_mastery.recipe;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.recipe.custom.RuneInscribingRecipe;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static class Serializers {
        public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
                DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CultivationMastery.MOD_ID);

        public static final RegistryObject<RecipeSerializer<RuneInscribingRecipe>> RUNE_INSCRIBING_TABLE_SERIALIZER =
                SERIALIZERS.register("rune_inscribing_table", () -> RuneInscribingRecipe.Serializer.INSTANCE);
    }

    public static class Types {
        public static final DeferredRegister<RecipeType<?>> TYPES =
                DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, CultivationMastery.MOD_ID);

        public static final RegistryObject<RecipeType<RuneInscribingRecipe>> RUNE_INSCRIBING_TABLE_TYPE =
                TYPES.register("rune_inscribing_table", () -> RuneInscribingRecipe.Type.INSTANCE);
    }

    public static void register(IEventBus event) {
        Logger.info("Registering Recipes");

        Serializers.SERIALIZERS.register(event);
        Types.TYPES.register(event);
    }
}
