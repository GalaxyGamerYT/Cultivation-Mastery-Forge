package galaxygameryt.cultivation_mastery.event;

import galaxygameryt.cultivation_mastery.CultivationMastery;
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
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.CultivationSyncS2CPacket;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.QiDataSyncS2CPacket;
import galaxygameryt.cultivation_mastery.util.PlayerData;
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

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID)
public class ModEvents {
    private static final HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

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
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            // ON THE SERVER!
            if(event.getEntity() instanceof ServerPlayer player) {
                syncS2C(player);
            }
        }
    }

    private static void syncS2C(ServerPlayer player) {
        // Qi
        player.getCapability(PlayerQiProvider.PLAYER_QI).ifPresent(qi -> {
            ModMessages.sendToPlayer(new QiDataSyncS2CPacket(qi.getQi()), player);
        });
        // Cultivation
        player.getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).ifPresent(cultivation -> {
            ModMessages.sendToPlayer(new CultivationSyncS2CPacket(cultivation.getCultivation()), player);
        });
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        // Registers the player's capabilities data
        event.register(PlayerCultivation.class);
        event.register(PlayerQi.class);
        event.register(PlayerMaxQi.class);
        event.register(PlayerQiIncrease.class);
        event.register(PlayerMeditating.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            // Ticks on the server
            Player player = event.player;

            UUID playerId = player.getUUID();
            playerDataMap.putIfAbsent(playerId, new PlayerData(playerId));

            PlayerData playerData = playerDataMap.get(playerId);
            playerData.incrementTickCounter();

            syncS2C((ServerPlayer) player);

            // The game runs at 20 t/s
            // Once every 5 seconds / 100 ticks
            if(playerData.getTickCounter() >= 100) {
                playerData.setTickCounter(0);

                // Gets the cultivation boolean of the player
                player.getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).ifPresent(cultivation -> {
                    playerData.setCultivation(cultivation.getCultivation());
                });
                if(playerData.getCultivation()) {

                    // Gets the max qi of the player
                    player.getCapability(PlayerMaxQiProvider.PLAYER_MAX_QI).ifPresent(max_qi -> {
                        playerData.setMax_qi(max_qi.getMaxQi());
                    });

                    // Gets the qi increase of the player
                    player.getCapability(PlayerQiIncreaseProvider.PLAYER_QI_INCREASE).ifPresent(qi_increase -> {
                        playerData.setQi_increase(qi_increase.getQi_increase());
                    });

                    // Gets the meditating boolean of the player
                    player.getCapability(PlayerMeditatingProvider.PLAYER_MEDITATING).ifPresent(meditating -> {
                        playerData.setMeditating(meditating.getMeditating());
                    });

                    // Adds to the player's qi
                    player.getCapability(PlayerQiProvider.PLAYER_QI).ifPresent(qi -> {
                        if(playerData.getMeditating()) {
                            qi.addQi(playerData.getQi_increase(), playerData.getMax_qi());
                            playerData.setQi(qi.getQi());
                        }
                    });
                }
                playerDataMap.put(playerId, playerData);
            }
        }
    }
}
