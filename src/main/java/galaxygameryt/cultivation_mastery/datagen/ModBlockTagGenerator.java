package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CultivationMastery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        // Minecraft Tags
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .addTags(
                        ModTags.Blocks.LOW_SPIRIT_STONE_ORES
                );

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .addTags(
                        ModTags.Blocks.MEDIUM_SPIRIT_STONE_ORES
                );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTags(
                        ModTags.Blocks.LOW_SPIRIT_STONE_ORES,
                        ModTags.Blocks.MEDIUM_SPIRIT_STONE_ORES,
                        ModTags.Blocks.HIGH_SPIRIT_STONE_ORES
                );

        // Forge Tags
        this.tag(Tags.Blocks.ORES)
                .addTags(
                        ModTags.Blocks.LOW_SPIRIT_STONE_ORES,
                        ModTags.Blocks.MEDIUM_SPIRIT_STONE_ORES,
                        ModTags.Blocks.HIGH_SPIRIT_STONE_ORES
                );

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .addTags(
                        ModTags.Blocks.HIGH_SPIRIT_STONE_ORES
                );

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
        this.tag(Tags.Blocks.ORE_RATES_SINGULAR)
                .addTags(
                        ModTags.Blocks.LOW_SPIRIT_STONE_ORES,
                        ModTags.Blocks.MEDIUM_SPIRIT_STONE_ORES,
                        ModTags.Blocks.HIGH_SPIRIT_STONE_ORES
                );

        // Mod Tags
        this.tag(ModTags.Blocks.LOW_SPIRIT_STONE_ORES)
                .add(
                        ModBlocks.LOW_SPIRIT_STONE_ORE.get(),
                        ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get()
                );
        this.tag(ModTags.Blocks.MEDIUM_SPIRIT_STONE_ORES)
                .add(
                        ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get(),
                        ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get()
                );
        this.tag(ModTags.Blocks.HIGH_SPIRIT_STONE_ORES)
                .add(
                        ModBlocks.HIGH_SPIRIT_STONE_ORE.get(),
                        ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get()
                );
        this.tag(ModTags.Blocks.WOOD_TRAINING_POSTS)
                .add(
                        ModBlocks.JUNGLE_TRAINING_POST.get(),
                        ModBlocks.MANGROVE_TRAINING_POST.get(),
                        ModBlocks.ACACIA_TRAINING_POST.get(),
                        ModBlocks.CHERRY_TRAINING_POST.get(),
                        ModBlocks.BIRCH_TRAINING_POST.get(),
                        ModBlocks.OAK_TRAINING_POST.get(),
                        ModBlocks.DARK_OAK_TRAINING_POST.get(),
                        ModBlocks.SPRUCE_TRAINING_POST.get()
                );
    }
}
