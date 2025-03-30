package galaxygameryt.cultivation_mastery.event;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.capabilites.body.PlayerBody;
import galaxygameryt.cultivation_mastery.capabilites.body.PlayerBodyProvider;
import galaxygameryt.cultivation_mastery.capabilites.cultivation.PlayerCultivation;
import galaxygameryt.cultivation_mastery.capabilites.cultivation.PlayerCultivationProvider;
import galaxygameryt.cultivation_mastery.capabilites.max_qi.PlayerMaxQi;
import galaxygameryt.cultivation_mastery.capabilites.max_qi.PlayerMaxQiProvider;
import galaxygameryt.cultivation_mastery.capabilites.meditating.PlayerMeditating;
import galaxygameryt.cultivation_mastery.capabilites.meditating.PlayerMeditatingProvider;
import galaxygameryt.cultivation_mastery.capabilites.qi.PlayerQi;
import galaxygameryt.cultivation_mastery.capabilites.qi.PlayerQiProvider;
import galaxygameryt.cultivation_mastery.capabilites.qi_increase.PlayerQiIncrease;
import galaxygameryt.cultivation_mastery.capabilites.qi_increase.PlayerQiIncreaseProvider;
import galaxygameryt.cultivation_mastery.capabilites.realm.PlayerRealm;
import galaxygameryt.cultivation_mastery.capabilites.realm.PlayerRealmProvider;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.*;
import galaxygameryt.cultivation_mastery.util.data.ClientPlayerData;
import galaxygameryt.cultivation_mastery.util.data.PlayerData;
import net.minecraft.client.Minecraft;
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

            // Cultivation
            if(!event.getObject().getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "cultivation"), new PlayerCultivationProvider());
            }
            // Qi
            if(!event.getObject().getCapability(PlayerQiProvider.PLAYER_QI).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "qi"), new PlayerQiProvider());
            }
            // Max Qi
            if(!event.getObject().getCapability(PlayerMaxQiProvider.PLAYER_MAX_QI).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "max_qi"), new PlayerMaxQiProvider());
            }
            // Qi Increase
            if(!event.getObject().getCapability(PlayerQiIncreaseProvider.PLAYER_QI_INCREASE).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "qi_increase"), new PlayerQiIncreaseProvider());
            }
            // Meditating
            if(!event.getObject().getCapability(PlayerMeditatingProvider.PLAYER_MEDITATING).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "meditating"), new PlayerMeditatingProvider());
            }
            // Body
            if(!event.getObject().getCapability(PlayerBodyProvider.PLAYER_BODY).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "body"), new PlayerBodyProvider());
            }
            // Realm
            if(!event.getObject().getCapability(PlayerRealmProvider.PLAYER_REALM).isPresent()) {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "realm"), new PlayerRealmProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            // Keeps the data on death
            Player player = event.getOriginal();

            // Cultivation
            player.getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).ifPresent(oldStore -> {
                player.getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            // Max Qi
            player.getCapability(PlayerMaxQiProvider.PLAYER_MAX_QI).ifPresent(oldStore -> {
                player.getCapability(PlayerMaxQiProvider.PLAYER_MAX_QI).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            // Qi Increase
            player.getCapability(PlayerQiIncreaseProvider.PLAYER_QI_INCREASE).ifPresent(oldStore -> {
                player.getCapability(PlayerQiIncreaseProvider.PLAYER_QI_INCREASE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            // Body
            player.getCapability(PlayerBodyProvider.PLAYER_BODY).ifPresent(oldStore -> {
                player.getCapability(PlayerBodyProvider.PLAYER_BODY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            // Realm
            player.getCapability(PlayerRealmProvider.PLAYER_REALM).ifPresent(oldStore -> {
                player.getCapability(PlayerRealmProvider.PLAYER_REALM).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof  Player player) {
            // Entity is a player!
            if (event.getLevel().isClientSide()) {
                if (player.getUUID() == Minecraft.getInstance().player.getUUID()) {
                    UUID playerId = player.getUUID();
                    CultivationMastery.CLIENT_PLAYER_DATA_MAP.putIfAbsent(playerId, new ClientPlayerData(playerId));
                }
            }

            if (!event.getLevel().isClientSide()) {
                // ON THE SERVER!
                capabilities2PlayerData(player);
                syncS2C((ServerPlayer) player);
            }
        }
    }

    private static void syncS2C(ServerPlayer player) {

        PlayerData playerData = CultivationMastery.PLAYER_DATA_MAP.get(player.getUUID());
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
        event.register(PlayerCultivation.class);
        event.register(PlayerQi.class);
        event.register(PlayerMaxQi.class);
        event.register(PlayerQiIncrease.class);
        event.register(PlayerMeditating.class);
        event.register(PlayerBody.class);
        event.register(PlayerRealm.class);
    }

    public static PlayerData capabilities2PlayerData(Player player) {
        UUID playerId = player.getUUID();
        CultivationMastery.PLAYER_DATA_MAP.putIfAbsent(playerId, new PlayerData(playerId));
        PlayerData playerData = CultivationMastery.PLAYER_DATA_MAP.get(playerId);

        // Gets the cultivation boolean of the player
        player.getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).ifPresent(cultivation -> {
            playerData.setCultivation(cultivation.getCultivation());
        });
        // Gets the max qi of the player
        player.getCapability(PlayerMaxQiProvider.PLAYER_MAX_QI).ifPresent(max_qi -> {
            playerData.setMaxQi(max_qi.getMaxQi());
        });
        // Gets the qi increase of the player
        player.getCapability(PlayerQiIncreaseProvider.PLAYER_QI_INCREASE).ifPresent(qi_increase -> {
            playerData.setQiIncrease(qi_increase.getQi_increase());
        });
        // Gets the meditating boolean of the player
        player.getCapability(PlayerMeditatingProvider.PLAYER_MEDITATING).ifPresent(meditating -> {
            playerData.setMeditating(meditating.getMeditating());
        });
        // Gets the body cultivation of the player
        player.getCapability(PlayerBodyProvider.PLAYER_BODY).ifPresent(body -> {
            playerData.setBody(body.getBody());
        });
        // Gets the Qi value of the player
        player.getCapability(PlayerQiProvider.PLAYER_QI).ifPresent(qi -> {
            playerData.setQi(qi.getQi());
        });
        // Gets the Realm value of the player
        player.getCapability(PlayerRealmProvider.PLAYER_REALM).ifPresent(realm -> {
            playerData.setRealm(realm.getRealm());
        });

        CultivationMastery.PLAYER_DATA_MAP.put(player.getUUID(), playerData);
        return playerData;
    }

    public static void playerData2Capabilities(Player player) {
        UUID playerId = player.getUUID();
        CultivationMastery.PLAYER_DATA_MAP.putIfAbsent(playerId, new PlayerData(playerId));
        PlayerData playerData = CultivationMastery.PLAYER_DATA_MAP.get(playerId);

        // Gets the Realm value of the player
        player.getCapability(PlayerRealmProvider.PLAYER_REALM).ifPresent(realm -> {
            realm.setRealm(playerData.getRealm());
        });

        // Gets the Qi value of the player
        if(playerData.getRealm() >= 7) {
            player.getCapability(PlayerQiProvider.PLAYER_QI).ifPresent(qi -> {
                qi.setQi(playerData.getQi());
            });
        }

        // Gets the Body value of the player
        if(playerData.getRealm() < 1) {
            player.getCapability(PlayerBodyProvider.PLAYER_BODY).ifPresent(body -> {
                body.setBody(playerData.getBody());
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            // Ticks on the server
            Player player = event.player;
            UUID playerId = player.getUUID();

            PlayerData playerData = capabilities2PlayerData(player);

            playerData.incrementTickCounter();

            // The game runs at 20 t/s
            // Once every 5 seconds / 100 ticks
            if(playerData.getCultivation()) {
                // Cultivation Realms Logic
                realmBodyTemperingLogic(playerData, player);

                if(playerData.getMeditating() && playerData.getRealm() >= 1 && playerData.getTickCounter() >= 100) {
                    playerData.setTickCounter(0);

                    // Adds to the player's qi
                    playerData.increase_qi();
                }
            }

            playerData2Capabilities(player);
            syncS2C((ServerPlayer) player);
        }
    }

    private static void realmBodyTemperingLogic(PlayerData playerData, Player player) {
        float realm = playerData.getRealm();
        float body = playerData.getBody();

        if(realm < 1 && body >= 100) {
            playerData.addRealm(0.1f);
            if(realm == 0.9f) {
                playerData.setBody(0);
            }
        }
    }
}
