package galaxygameryt.cultivation_mastery.event;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.client.gui.overlays.CultivationHudOverlay;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.BreakthroughC2SPacket;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.MeditatingC2SPacket;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.ContainerScreen;
import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuTypes;
import galaxygameryt.cultivation_mastery.util.KeyBinding;
import galaxygameryt.cultivation_mastery.util.data.player.ClientPlayerData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Player player = Minecraft.getInstance().player;
            ClientLevel level = Minecraft.getInstance().level;
            if (player != null && level != null) {
                if (KeyBinding.MEDITATE_KEY.consumeClick()) {
                    boolean meditating = CultivationMastery.CLIENT_PLAYER_DATA.getMeditating();
                    CultivationMastery.CLIENT_PLAYER_DATA.setMeditating(!meditating);
                    ModMessages.sendToServer(new MeditatingC2SPacket(!meditating, player.getUUID()));
                    if (meditating) {
                        player.sendSystemMessage(Component.translatable("message.cultivation_mastery.meditate").withStyle(ChatFormatting.GOLD));
                        level.playSound(player, player.getOnPos(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS,
                                0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                    } else {
                        player.sendSystemMessage(Component.translatable("message.cultivation_mastery.not_meditate").withStyle(ChatFormatting.GOLD));
                        level.playSound(player, player.getOnPos(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS,
                                0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                    }
                }
                if (KeyBinding.BREAKTHROUGH_KEY.consumeClick()) {
                    ModMessages.sendToServer(new BreakthroughC2SPacket(true, player.getUUID()));
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                Minecraft instance = Minecraft.getInstance();
                ClientPlayerData playerData = CultivationMastery.CLIENT_PLAYER_DATA;

                playerData.incrementTickCounter();

                if (playerData.getTickCounter() >= 1200) {
                    playerData.setTickCounter(0);
                }
            }
        }
    }

}
