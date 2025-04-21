package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        this.tag(ModTags.Items.SPIRITUAL_STONES)
                .add(
                        ModItems.LOW_SPIRIT_STONE.get(),
                        ModItems.MEDIUM_SPIRIT_STONE.get(),
                        ModItems.HIGH_SPIRIT_STONE.get()
                );
        this.tag(ModTags.Items.WOOD_TRAINING_POSTS)
                .add(
                        ModBlocks.JUNGLE_TRAINING_POST.get().asItem(),
                        ModBlocks.MANGROVE_TRAINING_POST.get().asItem(),
                        ModBlocks.ACACIA_TRAINING_POST.get().asItem(),
                        ModBlocks.CHERRY_TRAINING_POST.get().asItem(),
                        ModBlocks.BIRCH_TRAINING_POST.get().asItem(),
                        ModBlocks.OAK_TRAINING_POST.get().asItem(),
                        ModBlocks.DARK_OAK_TRAINING_POST.get().asItem(),
                        ModBlocks.SPRUCE_TRAINING_POST.get().asItem()
                );
    }
}
