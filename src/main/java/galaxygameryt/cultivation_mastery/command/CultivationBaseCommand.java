package galaxygameryt.cultivation_mastery.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
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
                .requires(CommandSourceStack::isPlayer)
                .then(Commands.literal("help").executes(CultivationBaseCommand::help))
                .then(Commands.literal("stats").executes(CultivationStatsCommand::getSelf)
                        .then(Commands.literal("reset").executes(CultivationStatsCommand::resetSelf)
                                .then(Commands.argument("player", EntityArgument.player()).executes(CultivationStatsCommand::resetOthers))
                        )
                        .then(Commands.literal("get").executes(CultivationStatsCommand::getSelf)
                                .then(Commands.argument("player", EntityArgument.player()).executes(CultivationStatsCommand::getOthers))
                        )
                        .then(Commands.literal("set")
                                .then(Commands.literal("body")
                                        .then(Commands.argument("bodyValue", FloatArgumentType.floatArg(0,99)).executes(CultivationStatsCommand::setSelfBody))
                                )
                                .then(Commands.literal("qi")
                                        .then(Commands.argument("qiValue", FloatArgumentType.floatArg(0)).executes(CultivationStatsCommand::setSelfQi))
                                )
                                .then(Commands.literal("realm")
                                        .then(Commands.argument("realmValue" , FloatArgumentType.floatArg(0,10)).executes(CultivationStatsCommand::setSelfRealm))
                                )
                                .then(Commands.literal("cultivation")
                                        .then(Commands.argument("cultivationValue", BoolArgumentType.bool()).executes(CultivationStatsCommand::setSelfCultivation))
                                )
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("body")
                                                .then(Commands.argument("bodyValue", FloatArgumentType.floatArg(0,99)).executes(CultivationStatsCommand::setOthersBody))
                                        )
                                        .then(Commands.literal("qi")
                                                .then(Commands.argument("qiValue", FloatArgumentType.floatArg(0)).executes(CultivationStatsCommand::setOthersQi))
                                        )
                                        .then(Commands.literal("realm")
                                                .then(Commands.argument("realmValue" , FloatArgumentType.floatArg(0,10)).executes(CultivationStatsCommand::setOthersRealm))
                                        )
                                        .then(Commands.literal("cultivation")
                                                .then(Commands.argument("cultivationValue", BoolArgumentType.bool()).executes(CultivationStatsCommand::setOthersCultivation))
                                        )
                                )
                        )
                )
        );
    }

    public static int help(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        player.sendSystemMessage(Component.literal(
                """
                        -----HELP-----
                        help - Shows command help.
                        stats - Get or set cultivation stats for a player or yourself.
                """
        ).withStyle(ChatFormatting.GOLD));
        return 1;
    }
}
