package galaxygameryt.cultivation_mastery.worldgen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_LOW_SPIRIT_STONE_ORE = registerKey("add_low_spirit_stone_ore");
    public static final ResourceKey<BiomeModifier> ADD_MEDIUM_SPIRIT_STONE_ORE = registerKey("add_medium_spirit_stone_ore");
    public static final ResourceKey<BiomeModifier> ADD_HIGH_SPIRIT_STONE_ORE = registerKey("add_high_spirit_stone_ore");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_LOW_SPIRIT_STONE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LOW_SPIRIT_STONE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_MEDIUM_SPIRIT_STONE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.MEDIUM_SPIRIT_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MEDIUM_SPIRIT_STONE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_HIGH_SPIRIT_STONE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.HIGH_SPIRIT_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.HIGH_SPIRIT_STONE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, name));
    }
}
