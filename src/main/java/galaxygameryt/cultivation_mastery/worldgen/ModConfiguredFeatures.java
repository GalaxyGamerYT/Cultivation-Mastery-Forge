package galaxygameryt.cultivation_mastery.worldgen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> LOW_SPIRIT_STONE_ORE_KEY = registerKey("low_spirit_stone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_SPIRIT_STONE_ORE_KEY = registerKey("medium_spirit_stone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGH_SPIRIT_STONE_ORE_KEY = registerKey("high_spirit_stone_ore");
// 12, 10, 8
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endStoneReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> lowSpiritStoneOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.LOW_SPIRIT_STONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get().defaultBlockState())
        );
        register(context, LOW_SPIRIT_STONE_ORE_KEY, Feature.ORE, new OreConfiguration(lowSpiritStoneOres, 12));

        List<OreConfiguration.TargetBlockState> mediumSpiritStoneOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(netherrackReplaceables, ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get().defaultBlockState())
        );
        register(context, MEDIUM_SPIRIT_STONE_ORE_KEY, Feature.ORE, new OreConfiguration(mediumSpiritStoneOres, 10));

        List<OreConfiguration.TargetBlockState> highSpiritStoneOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.HIGH_SPIRIT_STONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(endStoneReplaceables, ModBlocks.HIGH_SPIRIT_STONE_ORE.get().defaultBlockState())
        );
        register(context, HIGH_SPIRIT_STONE_ORE_KEY, Feature.ORE, new OreConfiguration(highSpiritStoneOres, 12));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
