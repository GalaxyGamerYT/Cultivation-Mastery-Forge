package galaxygameryt.cultivation_mastery.block;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.custom.FormationCoreBlock;
import galaxygameryt.cultivation_mastery.block.custom.PaddedCushionBlock;
import galaxygameryt.cultivation_mastery.block.custom.RuneInscribingTableBlock;
import galaxygameryt.cultivation_mastery.block.custom.ores.HighSpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.ores.LowSpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.ores.MediumSpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.training_posts.TrainingPostBlock;
import galaxygameryt.cultivation_mastery.block.custom.training_posts.WoodenTrainingPostBlock;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.util.Logger;
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

    public static final RegistryObject<Block> PADDED_CUSHION = registerBlock("padded_cushion",
            () -> new PaddedCushionBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_SLAB)));

    public static final RegistryObject<Block> LOW_SPIRIT_STONE_ORE = registerBlock("low_spirit_stone_ore",
            () -> new LowSpiritStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(2,5)));
    public static final RegistryObject<Block> DEEPSLATE_LOW_SPIRIT_STONE_ORE = registerBlock("deepslate_low_spirit_stone_ore",
            () -> new LowSpiritStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4f).requiresCorrectToolForDrops(), UniformInt.of(2,5)));
    public static final RegistryObject<Block> MEDIUM_SPIRIT_STONE_ORE = registerBlock("medium_spirit_stone_ore",
            () -> new MediumSpiritStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2.5f).requiresCorrectToolForDrops(), UniformInt.of(4,7)));
    public static final RegistryObject<Block> DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE = registerBlock("deepslate_medium_spirit_stone_ore",
            () -> new MediumSpiritStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4.5f).requiresCorrectToolForDrops(), UniformInt.of(4,7)));
    public static final RegistryObject<Block> HIGH_SPIRIT_STONE_ORE = registerBlock("high_spirit_stone_ore",
            () -> new HighSpiritStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(6,9)));
    public static final RegistryObject<Block> DEEPSLATE_HIGH_SPIRIT_STONE_ORE = registerBlock("deepslate_high_spirit_stone_ore",
            () -> new HighSpiritStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(6,9)));

    public static final RegistryObject<Block> ACACIA_TRAINING_POST = registerWoodenTrainingPostBlock("acacia_training_post",
            Blocks.ACACIA_PLANKS);
    public static final RegistryObject<Block> BIRCH_TRAINING_POST = registerWoodenTrainingPostBlock("birch_training_post",
            Blocks.BIRCH_PLANKS);
    public static final RegistryObject<Block> CHERRY_TRAINING_POST = registerWoodenTrainingPostBlock("cherry_training_post",
            Blocks.CHERRY_PLANKS);
    public static final RegistryObject<Block> DARK_OAK_TRAINING_POST = registerWoodenTrainingPostBlock("dark_oak_training_post",
            Blocks.DARK_OAK_PLANKS);
    public static final RegistryObject<Block> JUNGLE_TRAINING_POST = registerWoodenTrainingPostBlock("jungle_training_post",
            Blocks.JUNGLE_PLANKS);
    public static final RegistryObject<Block> MANGROVE_TRAINING_POST = registerWoodenTrainingPostBlock("mangrove_training_post",
            Blocks.MANGROVE_PLANKS);
    public static final RegistryObject<Block> OAK_TRAINING_POST = registerWoodenTrainingPostBlock("oak_training_post",
            Blocks.OAK_PLANKS);
    public static final RegistryObject<Block> SPRUCE_TRAINING_POST = registerWoodenTrainingPostBlock("spruce_training_post",
            Blocks.SPRUCE_PLANKS);

    public static final RegistryObject<Block> SPIRITUAL_IRON_TRAINING_POST = registerBlock("spiritual_iron_training_post",
            () -> new TrainingPostBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(-2.0F, 6.0F), 0.25f, 0.1f));

    public static final RegistryObject<Block> RUNE_INSCRIBING_TABLE = registerBlock("rune_inscribing_table",
            () -> new RuneInscribingTableBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));



    private static RegistryObject<Block> registerWoodenTrainingPostBlock(String name, Block propertiesCopyFromBlock) {
        return registerBlock(name, () -> new WoodenTrainingPostBlock(BlockBehaviour.Properties.copy(propertiesCopyFromBlock)
                .strength(-2.0f, 3.0f), 0.1f, 0.01f));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        Logger.info("Registering Blocks");
        BLOCKS.register(eventBus);
    }
}
