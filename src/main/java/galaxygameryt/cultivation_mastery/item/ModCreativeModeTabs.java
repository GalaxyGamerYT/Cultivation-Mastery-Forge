package galaxygameryt.cultivation_mastery.item;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.block.custom.ores.SpiritStoneOreBlock;
import galaxygameryt.cultivation_mastery.block.custom.training_posts.TrainingPostBlock;
import galaxygameryt.cultivation_mastery.item.custom.SpiritStoneItem;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.*;
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
                        output.accept(ModBlocks.RUNE_INSCRIBING_TABLE.get().asItem());
                        output.accept(ModBlocks.FORMATION_CORE.get().asItem());
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
                        output.accept(ModItems.IMMORTAL_RUNE_STONE.get());

                        populateRuneStones(output);
                    })
                    .build());

    public static void populateRuneStones(CreativeModeTab.Output output) {
        List<ItemStack> runeStoneStacks = new ArrayList<>();

        // BASIC RUNE STONES
        for (RegistryObject<Item> itemObj : ModItems.BASIC_RUNE_STONES) {
            int index = ModItems.BASIC_RUNE_STONES.indexOf(itemObj);
            BasicRuneStoneItem item = (BasicRuneStoneItem) itemObj.get();
            item.fillCreativeTabItems(runeStoneStacks, index);
        }

        // LOW RUNE STONES
        for (RegistryObject<Item> itemObj : ModItems.LOW_RUNE_STONES) {
            int index = ModItems.LOW_RUNE_STONES.indexOf(itemObj);
            LowRuneStoneItem item = (LowRuneStoneItem) itemObj.get();
            item.fillCreativeTabItems(runeStoneStacks, index);
        }

        // MEDIUM RUNE STONES
        for (RegistryObject<Item> itemObj : ModItems.MEDIUM_RUNE_STONES) {
            int index = ModItems.MEDIUM_RUNE_STONES.indexOf(itemObj);
            MediumRuneStoneItem item = (MediumRuneStoneItem) itemObj.get();
            item.fillCreativeTabItems(runeStoneStacks, index);
        }

        // HIGH RUNE STONES
        for (RegistryObject<Item> itemObj : ModItems.HIGH_RUNE_STONES) {
            int index = ModItems.HIGH_RUNE_STONES.indexOf(itemObj);
            HighRuneStoneItem item = (HighRuneStoneItem) itemObj.get();
            item.fillCreativeTabItems(runeStoneStacks, index);
        }

        // IMMORTAL RUNE STONES
        for (RegistryObject<Item> itemObj : ModItems.IMMORTAL_RUNE_STONES) {
            int index = ModItems.IMMORTAL_RUNE_STONES.indexOf(itemObj);
            ImmortalRuneStoneItem item = (ImmortalRuneStoneItem) itemObj.get();
            item.fillCreativeTabItems(runeStoneStacks, index);
        }

        output.acceptAll(runeStoneStacks);
    }

    public static void register(IEventBus eventBus) {
        Logger.info("Registering Creative Mode Tabs");
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
