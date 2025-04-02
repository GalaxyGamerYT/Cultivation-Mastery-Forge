package galaxygameryt.cultivation_mastery.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.capabilites.body.PlayerBodyProvider;
import galaxygameryt.cultivation_mastery.capabilites.cultivation.PlayerCultivationProvider;
import galaxygameryt.cultivation_mastery.capabilites.max_qi.PlayerMaxQiProvider;
import galaxygameryt.cultivation_mastery.capabilites.meditating.PlayerMeditatingProvider;
import galaxygameryt.cultivation_mastery.capabilites.qi.PlayerQiProvider;
import galaxygameryt.cultivation_mastery.capabilites.realm.PlayerRealmProvider;
import galaxygameryt.cultivation_mastery.util.player_data.ServerPlayerData;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class CultivationStatsCommand {
    public static int getSelf(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        getInfo(player);
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

//    Body
    public static void setBody(CommandContext<CommandSourceStack> context, Player player) {
        float bodyValue = FloatArgumentType.getFloat(context, "bodyValue");
        player.getCapability(PlayerBodyProvider.PLAYER_BODY).ifPresent(body -> {
            body.setBody(bodyValue);
        });
    }

    public static int setSelfBody(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        setBody(context, player);
        return 1;
    }

    public static int setOthersBody(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        setBody(context, player);
        return 1;
    }

//    Qi
    public static void setQi(CommandContext<CommandSourceStack> context, Player player) {
        float qiValue = FloatArgumentType.getFloat(context, "qiValue");
        player.getCapability(PlayerQiProvider.PLAYER_QI).ifPresent(qi -> {
            qi.setQi(qiValue);
        });
    }

    public static int setSelfQi(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        setQi(context, player);
        return 1;
    }

    public static int setOthersQi(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        setQi(context, player);
        return 1;
    }

//    Realm
    public static void setRealm(CommandContext<CommandSourceStack> context, Player player) {
        float realmValue = FloatArgumentType.getFloat(context, "realmValue");
        player.getCapability(PlayerRealmProvider.PLAYER_REALM).ifPresent(realm -> {
            realm.setRealm(realmValue);
        });
    }

    public static int setSelfRealm(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        setRealm(context, player);
        return 1;
    }

    public static int setOthersRealm(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        setRealm(context, player);
        return 1;
    }

//    Cultivation
    public static void setCultivation(CommandContext<CommandSourceStack> context, Player player) {
        boolean cultivationValue = BoolArgumentType.getBool(context, "cultivationValue");
        player.getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).ifPresent(cultivation -> {
            cultivation.setCultivation(cultivationValue);
        });
    }

    public static int setSelfCultivation(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        setCultivation(context, player);
        return 1;
    }

    public static int setOthersCultivation(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        setCultivation(context, player);
        return 1;
    }

    public static void reset(Player player) {
        if(player != null) {
            player.getCapability(PlayerQiProvider.PLAYER_QI).ifPresent(capability -> {
                capability.setQi(0);
            });
            player.getCapability(PlayerRealmProvider.PLAYER_REALM).ifPresent(capability -> {
                capability.setRealm(0);
            });
            player.getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).ifPresent(capability -> {
                capability.setCultivation(false);
            });
            player.getCapability(PlayerBodyProvider.PLAYER_BODY).ifPresent(capability -> {
                capability.setBody(0);
            });
            player.getCapability(PlayerMaxQiProvider.PLAYER_MAX_QI).ifPresent(capability -> {
                capability.setMaxQi(100);
            });
            player.getCapability(PlayerMeditatingProvider.PLAYER_MEDITATING).ifPresent(capability -> {
                capability.setMeditating(false);
            });
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());
            playerData.setBreakthrough(false);
            CultivationMastery.SERVER_PLAYER_DATA_MAP.put(player.getUUID(), playerData);
        }
    }

    public static int resetSelf(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        reset(player);
        if(player != null) {
            player.sendSystemMessage(Component.literal("Reset Cultivation Stats"));
            CultivationMastery.LOGGER.info(String.format("Reset Cultivation Stats for %s", player.getDisplayName()));
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
