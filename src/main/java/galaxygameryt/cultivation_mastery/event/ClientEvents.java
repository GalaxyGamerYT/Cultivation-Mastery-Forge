package galaxygameryt.cultivation_mastery.event;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.entity.custom.MeditationEntity;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.BreakthroughKeyC2SPacket;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.MeditatingC2SPacket;
import galaxygameryt.cultivation_mastery.particles.ModParticles;
import galaxygameryt.cultivation_mastery.util.KeyBinding;
import galaxygameryt.cultivation_mastery.util.data.player.ClientPlayerData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        ClientLevel level = Minecraft.getInstance().level;
        if (player != null && level != null) {
            if (KeyBinding.MEDITATE_KEY.consumeClick()) {
                meditateKeyLogic(player, level);
            }
            if (KeyBinding.BREAKTHROUGH_KEY.consumeClick()) {
                ModMessages.sendToServer(new BreakthroughKeyC2SPacket(true, player.getUUID()));
            }
        }
    }

    public static void meditateKeyLogic(Player player, ClientLevel level) {
        boolean meditating = CultivationMastery.CLIENT_PLAYER_DATA.getMeditating();
        CultivationMastery.CLIENT_PLAYER_DATA.setMeditating(!meditating);
        ModMessages.sendToServer(new MeditatingC2SPacket(!meditating, player.getUUID()));

        SoundEvent sound = SoundEvents.BEACON_ACTIVATE;
        Component msg = Component.translatable("message.cultivation_mastery.meditate").withStyle(ChatFormatting.GOLD);

        if (!meditating) {
            sound = SoundEvents.BEACON_DEACTIVATE;
            msg = Component.translatable("message.cultivation_mastery.not_meditate").withStyle(ChatFormatting.GOLD);
        }

        player.sendSystemMessage(msg);
        level.playSound(player, player.getOnPos(), sound, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft instance = Minecraft.getInstance();
            Player player = instance.player;
            if (player != null) {
                ClientPlayerData playerData = CultivationMastery.CLIENT_PLAYER_DATA;

                playerData.createTickCounter("general");

//                if (player.getVehicle() instanceof MeditationEntity) {
//                    generateDantianSwirl(player);
//                }

                if (playerData.getTickCounter("general") >= 20) {
                    playerData.resetTickCounter("general");
                }
                if (playerData.tickCounterExists("formation_core_gui_array")) {
                    if (playerData.getTickCounter("formation_core_gui_array") >= 30) {
                        playerData.resetTickCounter("formation_core_gui_array");
                    }
                }

                playerData.incrementAllTickCounters();
            }
        }
    }

    private static void generateDantianSwirl(Player player) {
        double offsetDistance = 0.4;
        double heightOffset = -0.5;

        float yaw = player.getYHeadRot();

        double yawRad = Math.toRadians(yaw);
        double xOffset = -Math.sin(yawRad) * offsetDistance;
        double zOffset = Math.cos(yawRad) * offsetDistance;

        double x = player.getX() + xOffset;
        double y = player.getY() + player.getEyeHeight() + heightOffset;
        double z = player.getZ() + zOffset;

        player.level().addParticle(ModParticles.QI_PARTICLE.get(), x, y, z, 0, 0, 0);
    }

    private static void generateQiSwirl(Player player, ClientLevel level) {
        double dantianY = player.getY() + 0.9; // Around belly area
        double radius = 0.8;
        int swirlPoints = 16;

        for (int i = 0; i < swirlPoints; i++) {
            double angle = (System.currentTimeMillis() / 100.0 + i * (360.0 / swirlPoints)) % 360;
            double radians = Math.toRadians(angle);

            double offsetX = radius * Math.cos(radians);
            double offsetZ = radius * Math.sin(radians);

            double x = player.getX() + offsetX;
            double y = dantianY;
            double z = player.getZ() + offsetZ;

            player.level().addParticle(ModParticles.QI_PARTICLE.get(), x, y, z, 0, 0.01, 0);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player && CultivationMastery.CLIENT_PLAYER_DATA.getJoined()) {
            player.sendSystemMessage(Component.translatable("message.cultivation_mastery.welcome").withStyle(ChatFormatting.GOLD));
            CultivationMastery.CLIENT_PLAYER_DATA.setJoined(true);
        }
    }

}
