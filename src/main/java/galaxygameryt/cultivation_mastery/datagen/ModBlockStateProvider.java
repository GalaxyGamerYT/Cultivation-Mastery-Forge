package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CultivationMastery.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.LOW_SPIRIT_STONE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE);
        blockWithItem(ModBlocks.MEDIUM_SPIRIT_STONE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE);
        blockWithItem(ModBlocks.HIGH_SPIRIT_STONE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
