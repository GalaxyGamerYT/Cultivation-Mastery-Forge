package galaxygameryt.cultivation_mastery.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.BreakthroughS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class CultivationBaseCommand {
    private static final String PLAYER_ERROR_MESSAGE = "You must specify a player for this command!";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("cult")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.literal("toast")
                        .requires(CommandSourceStack::isPlayer)
                        .executes(CultivationBaseCommand::addToastSelf)
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(CultivationBaseCommand::addToastOthers)))
                .then(Commands.literal("help")
                        .requires(CommandSourceStack::isPlayer)
                        .executes(CultivationBaseCommand::help))
                .then(Commands.literal("stats")
                        .requires(CommandSourceStack::isPlayer)
                        .executes(CultivationStatsCommand::getSelf)
                        .then(Commands.literal("reset")
                                .requires(CommandSourceStack::isPlayer)
                                .executes(CultivationStatsCommand::resetSelf)
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(CultivationStatsCommand::resetOthers))
                        )
                        .then(Commands.literal("get")
                                .requires(CommandSourceStack::isPlayer)
                                .executes(CultivationStatsCommand::getSelf)
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(CultivationStatsCommand::getOthers))
                        )
                        .then(Commands.literal("set")
                                .then(Commands.literal("body")
                                        .requires(CommandSourceStack::isPlayer)
                                        .then(Commands.argument("bodyValue", FloatArgumentType.floatArg(0,100))
                                                .requires(CommandSourceStack::isPlayer)
                                                .executes(CultivationStatsCommand::setSelfBody)
                                                .then(Commands.argument("player", EntityArgument.player())
                                                        .executes(CultivationStatsCommand::setOthersBody)
                                                )
                                        )
                                )
                                .then(Commands.literal("qi")
                                        .requires(CommandSourceStack::isPlayer)
                                        .then(Commands.argument("qiValue", FloatArgumentType.floatArg(0))
                                                .requires(CommandSourceStack::isPlayer)
                                                .executes(CultivationStatsCommand::setSelfQi)
                                                .then(Commands.argument("player", EntityArgument.player())
                                                        .executes(CultivationStatsCommand::setOthersQi)
                                                )
                                        )
                                )
                                .then(Commands.literal("realm")
                                        .requires(CommandSourceStack::isPlayer)
                                        .then(Commands.argument("realmValue" , FloatArgumentType.floatArg(0,10))
                                                .requires(CommandSourceStack::isPlayer)
                                                .executes(CultivationStatsCommand::setSelfRealm)
                                                .then(Commands.argument("player", EntityArgument.player())
                                                        .executes(CultivationStatsCommand::setOthersRealm)
                                                )
                                        )
                                )
                                .then(Commands.literal("cultivation")
                                        .requires(CommandSourceStack::isPlayer)
                                        .then(Commands.argument("cultivationValue", BoolArgumentType.bool())
                                                .requires(CommandSourceStack::isPlayer)
                                                .executes(CultivationStatsCommand::setSelfCultivation)
                                                .then(Commands.argument("player", EntityArgument.player())
                                                        .executes(CultivationStatsCommand::setOthersCultivation)
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static int addToastSelf(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        if (player != null) {
            addToast(player);
        } else {
            CultivationMastery.LOGGER.info(PLAYER_ERROR_MESSAGE);
        }
        return 1;
    }

    private static int addToastOthers(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        addToast(player);
        return 1;
    }

    private static void addToast(Player player) {
        ModMessages.sendToPlayer(new BreakthroughS2CPacket(), (ServerPlayer) player);
    }

    public static int help(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        String HELP_TEXT_PLAYER = """
                -----HELP-----
                help - Shows command help.
                stats <player:optional> - Get or set cultivation stats for a player or yourself.
                reset <player:optional> - Resets cultivation stats for a player or yourself
        """;

        String HELP_TEXT_SERVER = """
                -----HELP-----
                help - Shows command help.
                stats <player> - Get or set cultivation stats for a player.
                reset <player> - Resets cultivation stats for a player.
        """;

        if (player != null) {
            player.sendSystemMessage(Component.literal(HELP_TEXT_PLAYER).withStyle(ChatFormatting.GOLD));
        } else {
            CultivationMastery.LOGGER.info(HELP_TEXT_SERVER);
        }
        return 1;
    }
}
