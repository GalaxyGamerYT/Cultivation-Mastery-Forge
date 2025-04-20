package galaxygameryt.cultivation_mastery.block;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.custom.TrainingPostBlock;
import galaxygameryt.cultivation_mastery.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CultivationMastery.MOD_ID);

    public static final RegistryObject<Block> SPIRITUAL_IRON_BLOCK = registerBlock("spiritual_iron_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(7.5F, 8.5F)));

    public static final RegistryObject<Block> LOW_SPIRIT_STONE_ORE = registerBlock("low_spirit_stone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(2,5)));
    public static final RegistryObject<Block> DEEPSLATE_LOW_SPIRIT_STONE_ORE = registerBlock("deepslate_low_spirit_stone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4f).requiresCorrectToolForDrops(), UniformInt.of(2,5)));
    public static final RegistryObject<Block> MEDIUM_SPIRIT_STONE_ORE = registerBlock("medium_spirit_stone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2.5f).requiresCorrectToolForDrops(), UniformInt.of(4,7)));
    public static final RegistryObject<Block> DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE = registerBlock("deepslate_medium_spirit_stone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4.5f).requiresCorrectToolForDrops(), UniformInt.of(4,7)));
    public static final RegistryObject<Block> HIGH_SPIRIT_STONE_ORE = registerBlock("high_spirit_stone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(6,9)));
    public static final RegistryObject<Block> DEEPSLATE_HIGH_SPIRIT_STONE_ORE = registerBlock("deepslate_high_spirit_stone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(6,9)));

    public static final RegistryObject<Block> ACACIA_TRAINING_POST = registerBasicTrainingPostBlock("acacia_training_post",
            Blocks.ACACIA_PLANKS);
    public static final RegistryObject<Block> BIRCH_TRAINING_POST = registerBasicTrainingPostBlock("birch_training_post",
            Blocks.BIRCH_PLANKS);
    public static final RegistryObject<Block> CHERRY_TRAINING_POST = registerBasicTrainingPostBlock("cherry_training_post",
            Blocks.CHERRY_PLANKS);
    public static final RegistryObject<Block> DARK_OAK_TRAINING_POST = registerBasicTrainingPostBlock("dark_oak_training_post",
            Blocks.DARK_OAK_PLANKS);
    public static final RegistryObject<Block> JUNGLE_TRAINING_POST = registerBasicTrainingPostBlock("jungle_training_post",
            Blocks.JUNGLE_PLANKS);
    public static final RegistryObject<Block> MANGROVE_TRAINING_POST = registerBasicTrainingPostBlock("mangrove_training_post",
            Blocks.MANGROVE_PLANKS);
    public static final RegistryObject<Block> OAK_TRAINING_POST = registerBasicTrainingPostBlock("oak_training_post",
            Blocks.OAK_PLANKS);
    public static final RegistryObject<Block> SPRUCE_TRAINING_POST = registerBasicTrainingPostBlock("spruce_training_post",
            Blocks.SPRUCE_PLANKS);

    public static final RegistryObject<Block> SPIRITUAL_IRON_TRAINING_POST = registerBlock("spiritual_iron_training_post",
            () -> new TrainingPostBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(-2.0F, 6.0F), 0.25f, 0.1f));



    private static RegistryObject<Block> registerBasicTrainingPostBlock(String name, Block propertiesCopyFromBlock) {
        return registerBlock(name, () -> new TrainingPostBlock(BlockBehaviour.Properties.copy(propertiesCopyFromBlock)
                .strength(-2.0f, 3.0f), 0.1f, 0.01f));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
