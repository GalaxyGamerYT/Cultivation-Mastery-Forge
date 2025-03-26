package galaxygameryt.cultivation_mastery.item;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
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

    public static final RegistryObject<CreativeModeTab> CULTIVATION_MASTERY_TAB = CREATIVE_MODE_TABS.register("cultivation_mastery_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SPIRITUAL_MIRROR.get()))
                    .title(Component.translatable("creativetab.cultivation_mastery_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.SPIRITUAL_MIRROR.get());

                        pOutput.accept(ModItems.LOW_SPIRIT_STONE.get());
                        pOutput.accept(ModItems.MEDIUM_SPIRIT_STONE.get());
                        pOutput.accept(ModItems.HIGH_SPIRIT_STONE.get());

                        pOutput.accept(ModBlocks.LOW_SPIRIT_STONE_ORE.get().asItem());
                        pOutput.accept(ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get().asItem());
                        pOutput.accept(ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get().asItem());
                        pOutput.accept(ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get().asItem());
                        pOutput.accept(ModBlocks.HIGH_SPIRIT_STONE_ORE.get().asItem());
                        pOutput.accept(ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get().asItem());

                        pOutput.accept(ModBlocks.OAK_TRAINING_POST.get().asItem());
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
