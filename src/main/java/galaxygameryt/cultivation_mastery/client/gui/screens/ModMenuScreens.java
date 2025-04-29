package galaxygameryt.cultivation_mastery.client.gui.screens;

import galaxygameryt.cultivation_mastery.client.gui.screens.custom.container.ContainerScreen;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModMenuScreens {
    public static void register(FMLClientSetupEvent event) {
        Logger.info("Registering Menu Screens");

        MenuScreens.register(ModMenuTypes.CONTAINER_MENU.get(), ContainerScreen::new);
    }
}
