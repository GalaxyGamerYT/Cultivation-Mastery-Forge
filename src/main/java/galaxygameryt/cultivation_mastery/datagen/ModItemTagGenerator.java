package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                               CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, CultivationMastery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // Forge Tags
        this.tag(Tags.Items.ORES)
                .addTags(
                        ModTags.Items.LOW_SPIRIT_STONE_ORES,
                        ModTags.Items.MEDIUM_SPIRIT_STONE_ORES,
                        ModTags.Items.HIGH_SPIRIT_STONE_ORES
                );
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
        this.tag(Tags.Items.ORE_RATES_SINGULAR)
                .addTags(
                        ModTags.Items.LOW_SPIRIT_STONE_ORES,
                        ModTags.Items.MEDIUM_SPIRIT_STONE_ORES,
                        ModTags.Items.HIGH_SPIRIT_STONE_ORES
                );

        // Mod Tags
        this.tag(ModTags.Items.LOW_SPIRIT_STONE_ORES)
                .add(
                        ModBlocks.LOW_SPIRIT_STONE_ORE.get().asItem(),
                        ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get().asItem()
                );
        this.tag(ModTags.Items.MEDIUM_SPIRIT_STONE_ORES)
                .add(
                        ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get().asItem(),
                        ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get().asItem()
                );
        this.tag(ModTags.Items.HIGH_SPIRIT_STONE_ORES)
                .add(
                        ModBlocks.HIGH_SPIRIT_STONE_ORE.get().asItem(),
                        ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get().asItem()
                );
    }
}
