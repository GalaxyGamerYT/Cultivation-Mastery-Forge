package galaxygameryt.cultivation_mastery.util.handlers;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.player.ClientPlayerData;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class PlayerMeditationHandler {
    public static final HashMap<UUID, Float> meditateProgress = new HashMap<>();

    public static void tick(Player player) {
        UUID id = player.getUUID();

        boolean meditating = CultivationMastery.CLIENT_PLAYER_DATA.getMeditating();

        float progress = meditateProgress.getOrDefault(id, 0f);
        float target = meditating ? 1f : 0f;
        float speed = 0.05f;

        if (progress < target) progress = Math.min(target, progress + speed);
        else if (progress > target) progress = Math.max(target, progress - speed);

        meditateProgress.put(id, progress);

    }
}
