package galaxygameryt.cultivation_mastery.item;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.block.custom.ores.SpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.training_posts.TrainingPostBlock;
import galaxygameryt.cultivation_mastery.item.custom.SpiritStoneItem;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.ICreativeRuneStone;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CultivationMastery.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DEBUG_TAB = CREATIVE_MODE_TABS.register("debug_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.YIN_YANG.get()))
                    .title(Component.translatable("creativetab.cultivation_mastery.debug_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.YIN_YANG.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> CULTIVATION_MASTERY_TAB = CREATIVE_MODE_TABS.register("cultivation_mastery_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SPIRITUAL_MIRROR.get()))
                    .title(Component.translatable("creativetab.cultivation_mastery.cultivation_mastery_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.SPIRITUAL_MIRROR.get());
                        output.accept(ModItems.SPACE_RING.get());
//                        output.accept(ModItems.BACKPACK.get());
                        output.accept(ModItems.SPIRITUAL_IRON_INGOT.get());

                        // Items
                        for (RegistryObject<Item> itemEntry : ModItems.ITEMS.getEntries()) {
                            Item item = itemEntry.get();

                            if (item instanceof SpiritStoneItem) {
                                output.accept(item);
                            }
                        }

                        // Blocks
                        for (RegistryObject<Block> blockEntry : ModBlocks.BLOCKS.getEntries()) {
                            Block block = blockEntry.get();

                            if (block instanceof SpiritStoneOreBlock) {
                                output.accept(block.asItem());
                            }
                        }

                        output.accept(ModBlocks.SPIRITUAL_IRON_BLOCK.get().asItem());

                        output.accept(ModBlocks.PADDED_CUSHION.get().asItem());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> TRAINING_POST_TAB = CREATIVE_MODE_TABS.register("training_post_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.OAK_TRAINING_POST.get()))
                    .title(Component.translatable("creativetab.cultivation_mastery.training_post_tab"))
                    .displayItems((parameters, output) -> {

                        // Blocks
                        for (RegistryObject<Block> blockEntry : ModBlocks.BLOCKS.getEntries()) {
                            Block block = blockEntry.get();

                            if (block instanceof TrainingPostBlock) {
                                output.accept(block.asItem());
                            }
                        }
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> RUNE_STONE_TAB = CREATIVE_MODE_TABS.register("rune_stone_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BLANK_RUNE_STONE.get()))
                    .title(Component.translatable("creativetab.cultivation_mastery.rune_stone_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.BLANK_RUNE_STONE.get());
                        output.accept(ModItems.BASIC_RUNE_STONE.get());
                        output.accept(ModItems.LOW_RUNE_STONE.get());
                        output.accept(ModItems.MEDIUM_RUNE_STONE.get());
                        output.accept(ModItems.HIGH_RUNE_STONE.get());

                        output.acceptAll(ModItems.BASIC_RUNE_STONES.stream()
                                .map(RegistryObject::get)
                                .filter(item -> item instanceof ICreativeRuneStone)
                                .flatMap(item -> {
                                    List<ItemStack> stacks = new ArrayList<>();
                                    ((ICreativeRuneStone) item).fillCreativeTab(stacks);
                                    return stacks.stream();
                                }).collect(Collectors.toSet()));

                        output.acceptAll(ModItems.LOW_RUNE_STONES.stream()
                                .map(RegistryObject::get)
                                .filter(item -> item instanceof ICreativeRuneStone)
                                .flatMap(item -> {
                                    List<ItemStack> stacks = new ArrayList<>();
                                    ((ICreativeRuneStone) item).fillCreativeTab(stacks);
                                    return stacks.stream();
                                }).collect(Collectors.toSet()));

                        output.acceptAll(ModItems.MEDIUM_RUNE_STONES.stream()
                                .map(RegistryObject::get)
                                .filter(item -> item instanceof ICreativeRuneStone)
                                .flatMap(item -> {
                                    List<ItemStack> stacks = new ArrayList<>();
                                    ((ICreativeRuneStone) item).fillCreativeTab(stacks);
                                    return stacks.stream();
                                }).collect(Collectors.toSet()));

                        output.acceptAll(ModItems.HIGH_RUNE_STONES.stream()
                                .map(RegistryObject::get)
                                .filter(item -> item instanceof ICreativeRuneStone)
                                .flatMap(item -> {
                                    List<ItemStack> stacks = new ArrayList<>();
                                    ((ICreativeRuneStone) item).fillCreativeTab(stacks);
                                    return stacks.stream();
                                }).collect(Collectors.toSet()));
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        Logger.info("Registering Creative Mode Tabs");
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
