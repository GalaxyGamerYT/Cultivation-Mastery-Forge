package galaxygameryt.cultivation_mastery.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.capability.PlayerCapabilityProvider;
import galaxygameryt.cultivation_mastery.util.player_data.ServerPlayerData;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class CultivationStatsCommand {
    private static final String PLAYER_ERROR_MESSAGE = "You must specify a player for this command!";

//    Get Commands
    public static int getSelf(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        if (player != null) {
            getInfo(player);
        } else {
            CultivationMastery.LOGGER.info(PLAYER_ERROR_MESSAGE);
        }
        return 1;
    }

    public static int getOthers(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");

        getInfo(player);
        return 1;
    }

    private static void getInfo(Player player) {
        ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());
        player.sendSystemMessage(Component.literal(String.format(
                "-----%s-----\nCultivation: %b\nRealm: %.1f\nBody: %.5f\nQi: %.5f",
                player.getDisplayName().getString(),
                playerData.getCultivation(),
                playerData.getRealm(),
                playerData.getBody(),
                playerData.getQi()
        )).withStyle(ChatFormatting.DARK_AQUA));
    }

//    Set Commands
    // Body
    public static void setBody(CommandContext<CommandSourceStack> context, Player player) {
        float bodyValue = FloatArgumentType.getFloat(context, "bodyValue");
        player.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(capability -> {
            capability.setBody(bodyValue);
        });
        ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());
        playerData.setBody(bodyValue);
        CultivationMastery.SERVER_PLAYER_DATA_MAP.put(player.getUUID(), playerData);
    }

    public static int setSelfBody(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        if (player != null) {
            setBody(context, player);
        } else {
            CultivationMastery.LOGGER.info(PLAYER_ERROR_MESSAGE);
        }
        return 1;
    }

    public static int setOthersBody(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        setBody(context, player);
        return 1;
    }

    // Qi
    public static void setQi(CommandContext<CommandSourceStack> context, Player player) {
        float qiValue = FloatArgumentType.getFloat(context, "qiValue");
        player.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(capability -> {
            capability.setQi(qiValue);
        });
        ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());
        playerData.setQi(qiValue);
        CultivationMastery.SERVER_PLAYER_DATA_MAP.put(player.getUUID(), playerData);
    }

    public static int setSelfQi(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        if (player != null) {
            setQi(context, player);
        } else {
            CultivationMastery.LOGGER.info(PLAYER_ERROR_MESSAGE);
        }
        return 1;
    }

    public static int setOthersQi(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        setQi(context, player);
        return 1;
    }

    // Realm
    public static void setRealm(CommandContext<CommandSourceStack> context, Player player) {
        float realmValue = FloatArgumentType.getFloat(context, "realmValue");
        player.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(capability -> {
            capability.setRealm(realmValue);
        });
        ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());
        playerData.setRealm(realmValue);
        CultivationMastery.SERVER_PLAYER_DATA_MAP.put(player.getUUID(), playerData);
    }

    public static int setSelfRealm(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        if (player != null) {
            setRealm(context, player);
        } else {
            CultivationMastery.LOGGER.info(PLAYER_ERROR_MESSAGE);
        }
        return 1;
    }

    public static int setOthersRealm(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        setRealm(context, player);
        return 1;
    }

    // Cultivation
    public static void setCultivation(CommandContext<CommandSourceStack> context, Player player) {
        boolean cultivationValue = BoolArgumentType.getBool(context, "cultivationValue");
        player.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(capability -> {
            capability.setCultivation(cultivationValue);
        });
        ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());
        playerData.setCultivation(cultivationValue);
        CultivationMastery.SERVER_PLAYER_DATA_MAP.put(player.getUUID(), playerData);
    }

    public static int setSelfCultivation(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        if (player != null) {
            setCultivation(context, player);
        } else {
            CultivationMastery.LOGGER.info(PLAYER_ERROR_MESSAGE);
        }
        return 1;
    }

    public static int setOthersCultivation(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        setCultivation(context, player);
        return 1;
    }

//    Reset Commands
    public static void reset(Player player) {
        if(player != null) {
            player.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(capability -> {
                capability.setQi(0);
                capability.setRealm(0);
                capability.setCultivation(false);
                capability.setBody(0);
                capability.setMaxQi(100);
            });
            ServerPlayerData oldPlayerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());

            ServerPlayerData newPlayerData = new ServerPlayerData(player.getUUID());
            newPlayerData.setPlayerUUID(player.getUUID());
            newPlayerData.setTickCounter(oldPlayerData.getTickCounter());

            CultivationMastery.SERVER_PLAYER_DATA_MAP.put(player.getUUID(), newPlayerData);
        }
    }

    public static int resetSelf(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        reset(player);
        if(player != null) {
            player.sendSystemMessage(Component.literal("Reseting Cultivation Stats"));
            CultivationMastery.LOGGER.info(String.format("Reseting Cultivation Stats for %s", player.getDisplayName()));
        } else {
            CultivationMastery.LOGGER.info(PLAYER_ERROR_MESSAGE);
        }
        return 1;
    }

    public static int resetOthers(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        Player sender = context.getSource().getPlayer();
        reset(player);
        CultivationMastery.LOGGER.info(String.format("Reset Cultivation Stats for %s", player.getDisplayName()));
        if(sender != null) {
            if (player.getUUID() == sender.getUUID()) {
                sender.sendSystemMessage(Component.literal(String.format("Reset Cultivation Stats for %s", player.getDisplayName())));
            }
        }
        return 1;
    }
}
