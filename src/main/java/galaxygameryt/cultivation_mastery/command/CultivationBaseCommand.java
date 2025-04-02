package galaxygameryt.cultivation_mastery.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class CultivationBaseCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("cult")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
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
                                        .then(Commands.argument("bodyValue", FloatArgumentType.floatArg(0,99))
                                                .requires(CommandSourceStack::isPlayer)
                                                .executes(CultivationStatsCommand::setSelfBody))
                                )
                                .then(Commands.literal("qi")
                                        .requires(CommandSourceStack::isPlayer)
                                        .then(Commands.argument("qiValue", FloatArgumentType.floatArg(0))
                                                .requires(CommandSourceStack::isPlayer)
                                                .executes(CultivationStatsCommand::setSelfQi))
                                )
                                .then(Commands.literal("realm")
                                        .requires(CommandSourceStack::isPlayer)
                                        .then(Commands.argument("realmValue" , FloatArgumentType.floatArg(0,10))
                                                .requires(CommandSourceStack::isPlayer)
                                                .executes(CultivationStatsCommand::setSelfRealm))
                                )
                                .then(Commands.literal("cultivation")
                                        .requires(CommandSourceStack::isPlayer)
                                        .then(Commands.argument("cultivationValue", BoolArgumentType.bool())
                                                .requires(CommandSourceStack::isPlayer)
                                                .executes(CultivationStatsCommand::setSelfCultivation))
                                )
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("body")
                                                .then(Commands.argument("bodyValue", FloatArgumentType.floatArg(0,99))
                                                        .executes(CultivationStatsCommand::setOthersBody))
                                        )
                                        .then(Commands.literal("qi")
                                                .then(Commands.argument("qiValue", FloatArgumentType.floatArg(0))
                                                        .executes(CultivationStatsCommand::setOthersQi))
                                        )
                                        .then(Commands.literal("realm")
                                                .then(Commands.argument("realmValue" , FloatArgumentType.floatArg(0,10))
                                                        .executes(CultivationStatsCommand::setOthersRealm))
                                        )
                                        .then(Commands.literal("cultivation")
                                                .then(Commands.argument("cultivationValue", BoolArgumentType.bool())
                                                        .executes(CultivationStatsCommand::setOthersCultivation))
                                        )
                                )
                        )
                )
        );
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
