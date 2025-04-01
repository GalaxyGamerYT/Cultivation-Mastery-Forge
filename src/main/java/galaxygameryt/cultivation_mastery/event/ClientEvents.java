package galaxygameryt.cultivation_mastery.event;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.client.CultivationHudOverlay;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.BreakthroughC2SPacket;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.MeditatingC2SPacket;
import galaxygameryt.cultivation_mastery.screen.custom.BackpackScreen;
import galaxygameryt.cultivation_mastery.screen.ModMenuTypes;
import galaxygameryt.cultivation_mastery.util.KeyBinding;
import galaxygameryt.cultivation_mastery.util.data.ClientPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.UUID;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                ClientPlayerData clientPlayerData = CultivationMastery.CLIENT_PLAYER_DATA_MAP.get(player.getUUID());
                if (KeyBinding.MEDITATE_KEY.consumeClick()) {
                    boolean meditating = clientPlayerData.getMeditating();
                    ModMessages.sendToServer(new MeditatingC2SPacket(!meditating));
                }
                if (KeyBinding.BREAKTHROUGH_KEY.consumeClick()) {
                    ModMessages.sendToServer(new BreakthroughC2SPacket());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
            if(event.getLevel().isClientSide) {
                if(event.getEntity() instanceof Player player) {
                    // Entity is a player!
                    if (player.getUUID() == Minecraft.getInstance().player.getUUID()) {
                        UUID playerId = player.getUUID();
                        CultivationMastery.CLIENT_PLAYER_DATA_MAP.putIfAbsent(playerId, new ClientPlayerData(playerId));
                    }
                }
                }
        }
    }

    @Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.BACKPACK_MENU.get(), BackpackScreen::new);
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.MEDITATE_KEY);
            event.register(KeyBinding.BREAKTHROUGH_KEY);
//            event.register(KeyBinding.CULTIVATION_GUI_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("cultivation", CultivationHudOverlay.HUD_CULTIVATION);
        }
    }
}
