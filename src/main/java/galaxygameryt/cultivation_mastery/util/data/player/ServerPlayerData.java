package galaxygameryt.cultivation_mastery.util.data.player;

import java.util.UUID;

public class ServerPlayerData extends PlayerData {
    // Specialised
    public UUID playerUUID;

    public ServerPlayerData(UUID playerUUID) {

    }

    // Specialised
    // UUID
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }
}
