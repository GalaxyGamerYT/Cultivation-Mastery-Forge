package galaxygameryt.cultivation_mastery.client.gui.screens;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.container.ContainerMenu;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.formation_core.FormationCoreMenu;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.rune_inscribing_table.RuneInscribingTableMenu;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, CultivationMastery.MOD_ID);

    public static final RegistryObject<MenuType<ContainerMenu>> CONTAINER_MENU = registerFactoryMenuType("container_menu", ContainerMenu::new);

    public static final RegistryObject<MenuType<RuneInscribingTableMenu>> RUNE_INSCRIBING_TABLE_MENU = registerFactoryMenuType("rune_inscribing_table", RuneInscribingTableMenu::new);

    public static final RegistryObject<MenuType<FormationCoreMenu>> FORMATION_CORE_MENU = registerFactoryMenuType("formation_core", FormationCoreMenu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, MenuType.MenuSupplier<T> supplier) {
        return MENUS.register(name, () -> new MenuType(supplier, FeatureFlags.DEFAULT_FLAGS));
    }

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerFactoryMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        Logger.info("Registering Menu Types");
        MENUS.register(eventBus);
    }
}
