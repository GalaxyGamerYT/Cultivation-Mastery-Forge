package galaxygameryt.cultivation_mastery.event;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.client.CultivationHudOverlay;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.CultivationC2SPacket;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.MeditatingC2SPacket;
import galaxygameryt.cultivation_mastery.screen.custom.CustomScreen;
import galaxygameryt.cultivation_mastery.screen.custom.BackpackScreen;
import galaxygameryt.cultivation_mastery.screen.ModMenuTypes;
import galaxygameryt.cultivation_mastery.util.KeyBinding;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.MEDITATE_KEY.consumeClick()) {
//                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Meditating!"));
                ModMessages.sendToServer(new MeditatingC2SPacket());
            }
            if (KeyBinding.CULTIVATION_GUI_KEY.consumeClick()) {
                ModMessages.sendToServer(new CultivationC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.CUSTOM_MENU.get(), CustomScreen::new);
            MenuScreens.register(ModMenuTypes.BACKPACK_MENU.get(), BackpackScreen::new);
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.MEDITATE_KEY);
            event.register(KeyBinding.CULTIVATION_GUI_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("cultivation", CultivationHudOverlay.HUD_CULTIVATION);
        }
    }
}
