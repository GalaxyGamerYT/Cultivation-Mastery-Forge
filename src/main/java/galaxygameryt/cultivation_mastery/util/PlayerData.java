package galaxygameryt.cultivation_mastery.util;

import java.util.UUID;

public class PlayerData {
    private UUID playerUUID;
    private int tickCounter = 0;
    private int max_qi = 100;
    private float qi_increase = 0.1f;
    private boolean cultivation = false;
    private boolean meditating = false;
    private float qi = 0;
    private float body = 0;

    public PlayerData(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getTickCounter() {
        return tickCounter;
    }

    public void setTickCounter(int tickCounter) {
        this.tickCounter = tickCounter;
    }

    public void incrementTickCounter() {
        this.tickCounter++;
    }

    public int getMax_qi() {
        return max_qi;
    }

    public void setMax_qi(int max_qi) {
        this.max_qi = max_qi;
    }

    public float getQi_increase() {
        return qi_increase;
    }

    public void setQi_increase(float qi_increase) {
        this.qi_increase = qi_increase;
    }

    public boolean getCultivation() {
        return cultivation;
    }

    public void setCultivation(boolean cultivation) {
        this.cultivation = cultivation;
    }

    public boolean getMeditating() {
        return meditating;
    }

    public void setMeditating(boolean meditating) {
        this.meditating = meditating;
    }

    public float getQi() {
        return qi;
    }

    public void setQi(float qi) {
        this.qi = qi;
    }

    public float getBody() {
        return body;
    }

    public void setBody(float body) {
        this.body = body;
    }


}
