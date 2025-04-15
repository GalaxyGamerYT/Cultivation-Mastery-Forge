package galaxygameryt.cultivation_mastery.data.player;

import java.util.UUID;

public class ServerPlayerData extends PlayerData{
    // Specialised
    public UUID playerUUID;
    private int tickCounter = 0;

    // Boolean Data
    private boolean breakthrough = false;

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

    // Tick Counter
    public int getTickCounter() {
        return tickCounter;
    }

    public void setTickCounter(int tickCounter) {
        this.tickCounter = tickCounter;
    }

    public void incrementTickCounter() {
        this.tickCounter++;
    }

    // Boolean Data
    // Breakthrough
    public boolean getBreakthrough() {
        return breakthrough;
    }

    public void setBreakthrough(boolean breakthrough) {
        this.breakthrough = breakthrough;
    }
}
