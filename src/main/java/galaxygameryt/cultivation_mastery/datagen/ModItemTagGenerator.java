package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.block.custom.ores.HighSpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.ores.LowSpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.ores.MediumSpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.ores.SpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.training_posts.TrainingPostBlock;
import galaxygameryt.cultivation_mastery.block.custom.training_posts.WoodenTrainingPostBlock;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.item.custom.SpiritStoneItem;
import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                               CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, CultivationMastery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        // Items
        for (RegistryObject<Item> itemEntry : ModItems.ITEMS.getEntries()) {
            Item item = itemEntry.get();

            if (item instanceof SpiritStoneItem) {
                tag(ModTags.Items.SPIRITUAL_STONES).add(item);
            }
        }

        // Blocks
        for (RegistryObject<Block> blockEntry : ModBlocks.BLOCKS.getEntries()) {
            Block block = blockEntry.get();

            if (block instanceof SpiritStoneOreBlock) {
                tag(Tags.Items.ORES).add(block.asItem());
                tag(Tags.Items.ORE_RATES_SINGULAR).add(block.asItem());
            }
            if (block instanceof LowSpiritStoneOreBlock) {
                tag(ModTags.Items.LOW_SPIRIT_STONE_ORES).add(block.asItem());
            }
            if (block instanceof MediumSpiritStoneOreBlock) {
                tag(ModTags.Items.MEDIUM_SPIRIT_STONE_ORES).add(block.asItem());
            }
            if (block instanceof HighSpiritStoneOreBlock) {
                tag(ModTags.Items.HIGH_SPIRIT_STONE_ORES).add(block.asItem());
            }
            if (block instanceof WoodenTrainingPostBlock) {
                tag(ModTags.Items.WOOD_TRAINING_POSTS).add(block.asItem());
            }
            if (block instanceof TrainingPostBlock) {
                tag(ModTags.Items.TRAINING_POSTS).add(block.asItem());
            }
        }

        this.tag(Tags.Items.ORES_IN_GROUND_STONE)
                .add(
                        ModBlocks.LOW_SPIRIT_STONE_ORE.get().asItem(),
                        ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get().asItem(),
                        ModBlocks.HIGH_SPIRIT_STONE_ORE.get().asItem()
                );
        this.tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE)
                .add(
                        ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get().asItem(),
                        ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get().asItem(),
                        ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get().asItem()
                );
    }
}
