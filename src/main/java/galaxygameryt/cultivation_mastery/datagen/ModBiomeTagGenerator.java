package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagGenerator extends BiomeTagsProvider {
    public ModBiomeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, CultivationMastery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Biomes.MEDIUM_SPIRIT_BIOMES)
                .add(
                        Biomes.BAMBOO_JUNGLE,
                        Biomes.BIRCH_FOREST,
                        Biomes.CHERRY_GROVE,
                        Biomes.CRIMSON_FOREST,
                        Biomes.DARK_FOREST,
                        Biomes.FLOWER_FOREST,
                        Biomes.FOREST,
                        Biomes.JUNGLE,
                        Biomes.GROVE,
                        Biomes.LUSH_CAVES,
                        Biomes.MANGROVE_SWAMP,
                        Biomes.MEADOW,
                        Biomes.MUSHROOM_FIELDS,
                        Biomes.OLD_GROWTH_BIRCH_FOREST,
                        Biomes.OLD_GROWTH_PINE_TAIGA,
                        Biomes.OLD_GROWTH_SPRUCE_TAIGA,
                        Biomes.SAVANNA,
                        Biomes.SAVANNA_PLATEAU,
                        Biomes.SNOWY_SLOPES,
                        Biomes.SNOWY_TAIGA,
                        Biomes.SOUL_SAND_VALLEY,
                        Biomes.SPARSE_JUNGLE,
                        Biomes.SUNFLOWER_PLAINS,
                        Biomes.SWAMP,
                        Biomes.TAIGA,
                        Biomes.WARPED_FOREST,
                        Biomes.WINDSWEPT_FOREST,
                        Biomes.WINDSWEPT_SAVANNA
                );

        this.tag(ModTags.Biomes.HIGH_SPIRIT_BIOMES)
                .add(
                        Biomes.DEEP_COLD_OCEAN,
                        Biomes.DEEP_DARK,
                        Biomes.DEEP_OCEAN,
                        Biomes.DEEP_FROZEN_OCEAN,
                        Biomes.DEEP_LUKEWARM_OCEAN,
                        Biomes.END_BARRENS,
                        Biomes.END_HIGHLANDS,
                        Biomes.END_MIDLANDS,
                        Biomes.SMALL_END_ISLANDS,
                        Biomes.THE_END,
                        Biomes.FROZEN_OCEAN,
                        Biomes.FROZEN_PEAKS,
                        Biomes.FROZEN_RIVER,
                        Biomes.ICE_SPIKES,
                        Biomes.JAGGED_PEAKS,
                        Biomes.STONY_PEAKS
                );
    }
}
