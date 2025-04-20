package galaxygameryt.cultivation_mastery.event;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.realms.Realms;
import galaxygameryt.cultivation_mastery.effect.ModEffects;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.*;
import galaxygameryt.cultivation_mastery.util.ModTags;
import galaxygameryt.cultivation_mastery.util.data.capability.PlayerCapability;
import galaxygameryt.cultivation_mastery.util.data.capability.PlayerCapabilityProvider;
import galaxygameryt.cultivation_mastery.util.data.player.ServerPlayerData;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            // Attaches the capabilities to the player
            if(!event.getObject().getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "capabilities"), new PlayerCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            // Keeps the data on death
            Player player = event.getOriginal();

            player.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(oldStore -> {
                player.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                // ON THE SERVER!
                capabilities2PlayerData(player);
                syncS2C(player);
            }
        }
    }

    private static void syncS2C(ServerPlayer player) {

        ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());
        // Qi
        ModMessages.sendToPlayer(new QiDataSyncS2CPacket(playerData.getQi()), player);
        // Cultivation
        ModMessages.sendToPlayer(new CultivationDataSyncS2CPacket(playerData.getCultivation()), player);
        // Body
        ModMessages.sendToPlayer(new BodyDataSyncS2CPacket(playerData.getBody()), player);
        // Realm
        ModMessages.sendToPlayer(new RealmDataSyncS2CPacket(playerData.getRealm()), player);
        // Max Qi
        ModMessages.sendToPlayer(new MaxQiDataSyncS2CPacket(playerData.getMaxQi()), player);
        // Base Qi Multi
        ModMessages.sendToPlayer(new BaseQiMultiDataSyncS2CPacket(playerData.getBaseQiMulti()), player);
        // Env Qi Multi
        ModMessages.sendToPlayer(new EnvQiMultiDataSyncS2CPacket(playerData.getEnvQiMulti()), player);
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        // Registers the player's capabilities data
        event.register(PlayerCapability.class);
    }

    public static ServerPlayerData capabilities2PlayerData(Player player) {
        UUID playerId = player.getUUID();
        CultivationMastery.SERVER_PLAYER_DATA_MAP.putIfAbsent(playerId, new ServerPlayerData(playerId));
        ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(playerId);

        player.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(capability -> {
                playerData.setCultivation(capability.getCultivation());
                playerData.setMaxQi(capability.getMaxQi());
                playerData.setQiIncrease(capability.getQiIncrease());
                playerData.setQi(capability.getQi());
                playerData.setBody(capability.getBody());
                playerData.setRealm(capability.getRealm());
        });

        CultivationMastery.SERVER_PLAYER_DATA_MAP.put(player.getUUID(), playerData);
        return playerData;
    }

    public static void playerData2Capabilities(Player player) {
        UUID playerId = player.getUUID();
        CultivationMastery.SERVER_PLAYER_DATA_MAP.putIfAbsent(playerId, new ServerPlayerData(playerId));
        ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(playerId);

        player.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(capability -> {
            if (playerData.getCultivation() != capability.getCultivation()) {
                capability.setCultivation(playerData.getCultivation());
            }
            if (playerData.getMaxQi() != capability.getMaxQi()) {
                capability.setMaxQi(playerData.getMaxQi());
            }
            if (playerData.getQiIncrease() != capability.getQiIncrease()) {
                capability.setQiIncrease(playerData.getQiIncrease());
            }
            if (playerData.getQi() != capability.getQi()) {
                capability.setQi(playerData.getQi());
            }
            if (playerData.getBody() != capability.getBody()) {
                capability.setBody(playerData.getBody());
            }
            if (playerData.getRealm() != capability.getRealm()) {
                capability.setRealm(playerData.getRealm());
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            // Ticks on the server
            Player player = event.player;
            Level level = player.level();
            UUID playerId = player.getUUID();

            CultivationMastery.SERVER_PLAYER_DATA_MAP.putIfAbsent(playerId, new ServerPlayerData(playerId));
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(playerId);

            playerData.incrementTickCounter();

            // The game runs at 20 t/s
            // Once every 5 seconds / 100 ticks
            if(playerData.getCultivation()) {
                envQiAbsorptionBiomeLogic(player, level);
                // Cultivation Realms Logic
                realmLogic(playerData, player);

                if(playerData.getMeditating() && playerData.getRealm() >= 2 && playerData.getTickCounter() >= 100) {
                    playerData.setTickCounter(0);

                    // Adds to the player's qi
                    playerData.increase_qi();
                }
            }

            if (playerData.getTickCounter() >= 20) {
                playerData.setBreakthrough(false);
            }

            playerData2Capabilities(player);
            syncS2C((ServerPlayer) player);
        }
    }

    private static void envQiAbsorptionBiomeLogic(Player player, Level level) {
        Holder<Biome> biomeHolder = level.getBiome(player.blockPosition());
        MobEffectInstance existing = player.getEffect(ModEffects.ENVIRONMENT_QI_ABSORPTION.get());
        int duration = 600;
        int duration_limit = 550;

        if (biomeHolder.is(ModTags.Biomes.MEDIUM_SPIRIT_BIOMES)) {
            if (existing == null || existing.getDuration() < duration_limit) {
                MobEffectInstance instance = new MobEffectInstance(ModEffects.ENVIRONMENT_QI_ABSORPTION.get(), duration, 0, false, false, true);
                if (existing != null) {
                    player.removeEffect(existing.getEffect());
                }
                player.addEffect(instance);
            }
        } else if (biomeHolder.is(ModTags.Biomes.HIGH_SPIRIT_BIOMES)) {
            if (existing == null || existing.getDuration() < duration_limit) {
                MobEffectInstance instance = new MobEffectInstance(ModEffects.ENVIRONMENT_QI_ABSORPTION.get(), duration, 1, false, false, true);
                if (existing != null) {
                    player.removeEffect(existing.getEffect());
                }
                player.addEffect(instance);
            }
        }
    }

    private static void breakthroughLogic(ServerPlayerData playerData, Player player) {
        float realm = playerData.getRealm();
        int majorRealmValue = (int) Math.floor(realm);
        if(realm > Realms.REALMS[majorRealmValue].max) {
            if (realm >= 1) {
                MobEffectInstance instance = new MobEffectInstance(ModEffects.BREAKTHROUGH.get(), 1200, 0, false, true, true);
                player.addEffect(instance);
            }
            playerData.setRealm(majorRealmValue+1);
        } else {
            playerData.setRealm(realm);
        }
    }

    private static void realmLogic(ServerPlayerData playerData, Player player) {
        if (playerData.getRealm() < 1) {
            realmMortalLogic(playerData, player);
        } else if (playerData.getRealm() < 2) {
            realmBodyTemperingLogic(playerData, player);
        }
        breakthroughLogic(playerData, player);
    }

    private static void realmBodyTemperingLogic(ServerPlayerData playerData, Player player) {
        float body = playerData.getBody();
        boolean breakthrough = playerData.getBreakthrough();

        if (breakthrough && body == 100) {
            if (playerData.getRealm() < Realms.REALMS[1].max) {
                // 1-9
                playerData.addRealm(0.1f);
                playerData.setBody(0);
            } else {
                // 9-max
                if (playerData.getQi() >= playerData.getMaxQi()) {
                    playerData.setQi(0);
                    playerData.setMaxQi(100);
                    playerData.addRealm(0.1f);
                }
            }
        }
    }

    private static void realmMortalLogic(ServerPlayerData playerData, Player player) {
        if(playerData.getBody() == 100) {
            playerData.addRealm(0.1f);
            playerData.setBody(0);
        }
    }
}
