package galaxygameryt.cultivation_mastery.command;

import com.mojang.brigadier.CommandDispatcher;
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
                .then(Commands.literal("stats")
                        .then(Commands.literal("get").executes(CultivationStatsCommand::getSelf)
                                .then(Commands.argument("player", EntityArgument.player()).executes(CultivationStatsCommand::getOthers))
                        )
//                        .then(Commands.literal("set")
//                                .then(Commands.literal("body").executes(CultivationStatsCommand::setSelfBody)
//                                        .then(Commands.argument("body", FloatArgumentType.floatArg(0,99))
//                                                .executes())
//                                )
//                        )
                )
        );
    }

    public static int help(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();

        player.sendSystemMessage(Component.literal(
                "-----HELP-----\n" +
                "help - Shows command help.\n" +
                "info - Gets the information for a player or yourself."
        ).withStyle(ChatFormatting.GOLD));
        return 1;
    }
}
