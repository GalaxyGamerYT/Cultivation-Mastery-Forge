package galaxygameryt.cultivation_mastery.item;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

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

                        output.accept(ModItems.LOW_SPIRIT_STONE.get());
                        output.accept(ModItems.MEDIUM_SPIRIT_STONE.get());
                        output.accept(ModItems.HIGH_SPIRIT_STONE.get());

                        output.accept(ModBlocks.LOW_SPIRIT_STONE_ORE.get().asItem());
                        output.accept(ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get().asItem());
                        output.accept(ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get().asItem());
                        output.accept(ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get().asItem());
                        output.accept(ModBlocks.HIGH_SPIRIT_STONE_ORE.get().asItem());
                        output.accept(ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get().asItem());

                        output.accept(ModBlocks.SPIRITUAL_IRON_BLOCK.get().asItem());

                        output.accept(ModBlocks.PADDED_CUSHION.get().asItem());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> TRAINING_POST_TAB = CREATIVE_MODE_TABS.register("training_post_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.OAK_TRAINING_POST.get()))
                    .title(Component.translatable("creativetab.cultivation_mastery.training_post_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.OAK_TRAINING_POST.get().asItem());
                        output.accept(ModBlocks.SPRUCE_TRAINING_POST.get().asItem());
                        output.accept(ModBlocks.ACACIA_TRAINING_POST.get().asItem());
                        output.accept(ModBlocks.CHERRY_TRAINING_POST.get().asItem());
                        output.accept(ModBlocks.MANGROVE_TRAINING_POST.get().asItem());
                        output.accept(ModBlocks.DARK_OAK_TRAINING_POST.get().asItem());
                        output.accept(ModBlocks.JUNGLE_TRAINING_POST.get().asItem());
                        output.accept(ModBlocks.BIRCH_TRAINING_POST.get().asItem());

                        output.accept(ModBlocks.SPIRITUAL_IRON_TRAINING_POST.get().asItem());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        Logger.info("Registering Creative Mode Tabs");
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
