package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CultivationMastery.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.LOW_SPIRIT_STONE_ORE.get());
        blockWithItem(ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get());
        blockWithItem(ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get());
        blockWithItem(ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get());
        blockWithItem(ModBlocks.HIGH_SPIRIT_STONE_ORE.get());
        blockWithItem(ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get());

        blockWithItem(ModBlocks.SPIRITUAL_IRON_BLOCK.get());

        customModelBlockWithItem(ModBlocks.PADDED_CUSHION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/padded_cushion")));

        trainingPostBlock(ModBlocks.OAK_TRAINING_POST.get());
        trainingPostBlock(ModBlocks.JUNGLE_TRAINING_POST.get());
        trainingPostBlock(ModBlocks.MANGROVE_TRAINING_POST.get());
        trainingPostBlock(ModBlocks.CHERRY_TRAINING_POST.get());
        trainingPostBlock(ModBlocks.SPRUCE_TRAINING_POST.get());
        trainingPostBlock(ModBlocks.DARK_OAK_TRAINING_POST.get());
        trainingPostBlock(ModBlocks.BIRCH_TRAINING_POST.get());
        trainingPostBlock(ModBlocks.ACACIA_TRAINING_POST.get());

        // Spiritual Iron Training Post
        trainingPostBlock(ModBlocks.SPIRITUAL_IRON_TRAINING_POST.get());

        customModelBlockWithItem(ModBlocks.RUNE_INSCRIBING_TABLE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/rune_inscribing_table")));
    }

    private void customModelBlockWithItem(Block block, ModelFile model) {
        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }

    private void trainingPostBlock(Block block) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            boolean lower = state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;

            ModelFile model = new ModelFile.UncheckedModelFile(modLoc("training_posts/block/"+ForgeRegistries.BLOCKS.getKey(block).getPath()+"_"+(
                    lower ? "bottom" : "top")
                    ));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
        simpleBlockItem(block, new ModelFile.UncheckedModelFile(modLoc("training_posts/item/"+ForgeRegistries.BLOCKS.getKey(block).getPath())));
    }

    private void blockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    private void blockItem(Block block) {
        simpleBlockItem(block, new ModelFile(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "block/" +
                ForgeRegistries.BLOCKS.getKey(block).getPath())) {
            @Override
            protected boolean exists() {
                return true;
            }
        });
    }

    private void blockItem(Block block, String appendix) {
        simpleBlockItem(block, new ModelFile(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "block/" +
                ForgeRegistries.BLOCKS.getKey(block).getPath() + appendix)) {
            @Override
            protected boolean exists() {
                return true;
            }
        });
    }

    private void doubleBlockWithItem(Block block) {
        doubleBlock(block);
        blockItem(block);
    }

    private void doubleBlock(Block block) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            boolean lower = state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;

            ModelFile model = new ModelFile(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID,
                    "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath() + "_" + (
                            lower ? "lower" : "upper"
                    )
            )) {
                @Override
                protected boolean exists() {
                    return true;
                }
            };

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }

    private void doubleBlockWithPath(Block block, String path) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            boolean lower = state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;

            ModelFile model = new ModelFile(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID,
                    "block/" + path + "/" + ForgeRegistries.BLOCKS.getKey(block).getPath() + "_" + (
                            lower ? "lower" : "upper"
                    )
            )) {
                @Override
                protected boolean exists() {
                    return true;
                }
            };

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });

    }
}
