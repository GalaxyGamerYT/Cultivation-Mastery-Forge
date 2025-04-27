package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.block.custom.ores.HighSpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.ores.LowSpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.ores.MediumSpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.ores.SpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.training_posts.TrainingPostBlock;
import galaxygameryt.cultivation_mastery.block.custom.training_posts.WoodenTrainingPostBlock;
import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CultivationMastery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        for (RegistryObject<Block> blockEntry : ModBlocks.BLOCKS.getEntries()) {
            Block block = blockEntry.get();

            if (block instanceof TrainingPostBlock) {
                tag(ModTags.Blocks.TRAINING_POSTS).add(block);
            }
            if (block instanceof WoodenTrainingPostBlock) {
                tag(ModTags.Blocks.WOOD_TRAINING_POSTS).add(block);
            }
            if (block instanceof SpiritStoneOreBlock) {
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
                tag(Tags.Blocks.ORE_RATES_SINGULAR).add(block);
                tag(Tags.Blocks.ORES).add(block);
            }
            if (block instanceof LowSpiritStoneOreBlock) {
                tag(BlockTags.NEEDS_IRON_TOOL).add(block);
                tag(ModTags.Blocks.LOW_SPIRIT_STONE_ORES).add(block);
            }
            if (block instanceof MediumSpiritStoneOreBlock) {
                tag(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
                tag(ModTags.Blocks.MEDIUM_SPIRIT_STONE_ORES).add(block);
            }
            if (block instanceof HighSpiritStoneOreBlock) {
                tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(block);
                tag(ModTags.Blocks.HIGH_SPIRIT_STONE_ORES).add(block);
            }
        }

        // Forge Tags
        this.tag(Tags.Blocks.ORES_IN_GROUND_STONE)
                .add(
                        ModBlocks.LOW_SPIRIT_STONE_ORE.get(),
                        ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get(),
                        ModBlocks.HIGH_SPIRIT_STONE_ORE.get()
                );
        this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
                .add(
                        ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get(),
                        ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get(),
                        ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get()
                );
    }
}
