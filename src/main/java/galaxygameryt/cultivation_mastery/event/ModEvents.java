package galaxygameryt.cultivation_mastery.event;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.*;
import galaxygameryt.cultivation_mastery.util.capability.PlayerCapability;
import galaxygameryt.cultivation_mastery.util.capability.PlayerCapabilityProvider;
import galaxygameryt.cultivation_mastery.util.player_data.ServerPlayerData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
        ModMessages.sendToPlayer(new CultivationSyncS2CPacket(playerData.getCultivation()), player);
        // Body
        ModMessages.sendToPlayer(new BodyDataSyncS2CPacket(playerData.getBody()), player);
        // Realm
        ModMessages.sendToPlayer(new RealmDataSyncS2CPacket(playerData.getRealm()), player);
        // Max Qi
        ModMessages.sendToPlayer(new MaxQiDataSyncS2CPacket(playerData.getMaxQi()), player);
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
            UUID playerId = player.getUUID();

            CultivationMastery.SERVER_PLAYER_DATA_MAP.putIfAbsent(playerId, new ServerPlayerData(playerId));
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(playerId);

            playerData.incrementTickCounter();

            // The game runs at 20 t/s
            // Once every 5 seconds / 100 ticks
            if(playerData.getCultivation()) {
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

    private static void realmLogic(ServerPlayerData playerData, Player player) {
        if (playerData.getRealm() > 1) {
            realmMortalLogic(playerData, playerData.getRealm(), playerData.getBody());
        } else if (playerData.getRealm() > 2) {
            realmBodyTemperingLogic(playerData, playerData.getRealm(), playerData.getBody(), playerData.getBreakthrough());
        }
    }

    private static void realmBodyTemperingLogic(ServerPlayerData playerData, float realm, float body, boolean breakthrough) {
        if(body == 100 && breakthrough) {
            if(realm == CultivationMastery.REALMS[0].maxLevelFraction) {
                if(playerData.getQi() == playerData.getMaxQi()) {
                    playerData.setQi(0);
                    playerData.addRealm(0.1f);
                }
            } else {
                playerData.addRealm(0.1f);
                playerData.setBody(0);
            }
        }
    }

    private static void realmMortalLogic(ServerPlayerData playerData, float realm, float body) {
        if(realm == CultivationMastery.REALMS[0].maxLevelFraction) {
            playerData.setRealm(1);
        }
        if(body == 100) {
            playerData.addRealm(0.1f);
            playerData.setBody(0);
        }
    }
}
