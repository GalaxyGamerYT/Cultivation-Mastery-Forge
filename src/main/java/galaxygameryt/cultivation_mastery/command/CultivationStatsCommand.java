package galaxygameryt.cultivation_mastery.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.PlayerData;
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
        PlayerData playerData = CultivationMastery.PLAYER_DATA_MAP.get(player.getUUID());
        player.sendSystemMessage(Component.literal(String.format(
                "-----%s-----\nCultivation: %b\nRealm: %.1f\nBody: %.5f\nQi: %.5f",
                player.getDisplayName().getString(),
                playerData.getCultivation(),
                playerData.getRealm(),
                playerData.getBody(),
                playerData.getQi()
        )).withStyle(ChatFormatting.DARK_AQUA));
    }

    public static int setSelf(CommandContext<CommandSourceStack> context) {
        return 1;
    }

    public static int setSelfBody(CommandContext<CommandSourceStack> context) {
        return 1;
    }
}
