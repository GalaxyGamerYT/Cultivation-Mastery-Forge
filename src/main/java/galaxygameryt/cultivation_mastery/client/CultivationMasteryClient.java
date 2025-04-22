package galaxygameryt.cultivation_mastery.client;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.client.gui.overlays.CultivationHudOverlay;
import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuTypes;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.ContainerScreen;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.SpiritualMirrorScreen;
import galaxygameryt.cultivation_mastery.client.gui.toasts.BreakthroughToast;
import galaxygameryt.cultivation_mastery.util.KeyBinding;
import galaxygameryt.cultivation_mastery.util.enums.Screens;
import galaxygameryt.cultivation_mastery.util.enums.Toasts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CultivationMasteryClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        MenuScreens.register(ModMenuTypes.CONTAINER_MENU.get(), ContainerScreen::new);
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.MEDITATE_KEY);
        event.register(KeyBinding.BREAKTHROUGH_KEY);
//            event.register(KeyBinding.CULTIVATION_GUI_KEY);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
//            event.registerAboveAll("cultivation", CultivationHudOverlayOld.HUD_CULTIVATION);
        event.registerAboveAll("cultivation", CultivationHudOverlay.instance);
    }

    public static void openScreen(Screens screen) {
        Minecraft instance = Minecraft.getInstance();

        switch (screen) {
            case NULL:
                instance.setScreen(null);
            case SPIRITUAL_MIRROR:
                instance.setScreen(new SpiritualMirrorScreen());
        }
    }

    public static void addToast(Toasts toast) {
        Minecraft instance = Minecraft.getInstance();
        ToastComponent toastComponent = instance.getToasts();

        switch (toast) {
            case BREAKTROUGH:
                toastComponent.addToast(new BreakthroughToast());
                instance.player.sendSystemMessage(Component.literal("Sending Breakthrough Toast!"));
        }
    }
}

