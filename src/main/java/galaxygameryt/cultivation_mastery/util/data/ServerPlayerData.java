package galaxygameryt.cultivation_mastery.util.data;

import java.time.LocalDateTime;
import java.util.UUID;

public class ServerPlayerData extends PlayerData{
    // Specialised
    private int tickCounter = 0;

    // Boolean Data
    private boolean breakthrough = false;

    public ServerPlayerData(UUID playerUUID) {
        super(playerUUID);
    }

    // Specialised
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
