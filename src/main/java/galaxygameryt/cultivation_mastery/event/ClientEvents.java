package galaxygameryt.cultivation_mastery.event;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.entity.ModEntities;
import galaxygameryt.cultivation_mastery.entity.custom.SittingEntity;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.BreakthroughKeyC2SPacket;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.MeditatingC2SPacket;
import galaxygameryt.cultivation_mastery.util.KeyBinding;
import galaxygameryt.cultivation_mastery.util.data.player.ClientPlayerData;
import galaxygameryt.cultivation_mastery.util.handlers.PlayerMeditationHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
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

                PlayerMeditationHandler.tick(player);

                if (playerData.getTickCounter("general") >= 20) {
                    playerData.resetTickCounter("general");
                }

                playerData.incrementAllTickCounters();
            }
        }
    }

    public static void addGlowingQiEnergy(Player player, ClientLevel level) {
        float progress = PlayerMeditationHandler.meditateProgress.get(player.getUUID());
        if (progress > 0.5f) {
            RandomSource random = player.getRandom();
            double strength = progress;
            double offsetY = 1.3;

            double spread = 0.15 * strength;
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                    player.getX() + (random.nextDouble() - 0.5) * spread,
                    player.getY() + offsetY,
                    player.getZ() + (random.nextDouble() - 0.5) * spread,
                    0, 0.01 + strength * 0.02, 0);
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
