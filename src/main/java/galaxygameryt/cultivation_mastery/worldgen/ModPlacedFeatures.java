package galaxygameryt.cultivation_mastery.worldgen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> LOW_SPIRIT_STONE_ORE_PLACED_KEY = registerKey("low_spirit_stone_ore_placed");
    public static final ResourceKey<PlacedFeature> MEDIUM_SPIRIT_STONE_ORE_PLACED_KEY = registerKey("medium_spirit_stone_ore_placed");
    public static final ResourceKey<PlacedFeature> HIGH_SPIRIT_STONE_ORE_PLACED_KEY = registerKey("high_spirit_stone_ore_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, LOW_SPIRIT_STONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.LOW_SPIRIT_STONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, MEDIUM_SPIRIT_STONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MEDIUM_SPIRIT_STONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(10,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, HIGH_SPIRIT_STONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.HIGH_SPIRIT_STONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, name));
    }


    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
