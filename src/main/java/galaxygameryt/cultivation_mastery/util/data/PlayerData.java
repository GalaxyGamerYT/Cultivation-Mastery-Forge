package galaxygameryt.cultivation_mastery.util.data;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlayerData {
    // Specialised
    private UUID playerUUID;
    private LocalDateTime lastUpdated = LocalDateTime.now();
    private int tickCounter = 0;

    // Boolean Data
    private boolean cultivation = false;
    private boolean meditating = false;

    // Data
    private int maxQi = 100;
    private float qiIncrease = 0.1f;
    private float qi = 0;
    private float body = 0;
    private float realm = 0;

    public PlayerData(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    // Specialised
    // UUID
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    // Local Date Time
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void updateLastUpdated() {
        setLastUpdated(LocalDateTime.now());
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
    // Cultivation
    public boolean getCultivation() {
        return cultivation;
    }

    public void setCultivation(boolean cultivation) {
        this.cultivation = cultivation;
    }

    // Meditating
    public boolean getMeditating() {
        return meditating;
    }

    public void setMeditating(boolean meditating) {
        this.meditating = meditating;
    }

    // Data
    // Max Qi
    public int getMaxQi() {
        return maxQi;
    }

    public void setMaxQi(int maxQi) {
        this.maxQi = maxQi;
    }

    public void addMaxQi(int add) {
        setMaxQi(maxQi + add);
    }

    public void subMaxQi(int sub) {
        setMaxQi(Math.max(maxQi - sub, 1));
    }

    // Qi Increase
    public float getQiIncrease() {
        return qiIncrease;
    }

    public void setQiIncrease(float qiIncrease) {
        this.qiIncrease = qiIncrease;
    }

    public void addQiIncrease(float add) {
        setQiIncrease(qiIncrease + add);
    }

    public void subQiIncrease(float sub) {
        setQiIncrease(Math.max(qiIncrease - sub, 0));
    }

    // Qi
    public float getQi() {
        return qi;
    }

    public void setQi(float qi) {
        this.qi = qi;
    }

    public void addQi(float add) {
        setQi(Math.min(qi + add, this.maxQi));
    }

    public void increase_qi() {
        addQi(getQiIncrease());
    }

    public void subQi(float sub) {
        setQi(Math.max(qi - sub, 0));
    }

    // Body
    public float getBody() {
        return body;
    }

    public void setBody(float body) {
        this.body = body;
    }

    public void addBody(float add) {
        setBody(Math.min(body + add, 10));
    }

    public void subBody(float sub) {
        setBody(Math.max(body - sub, 0));
    }

    // Realm
    public float getRealm() {
        return realm;
    }

    public void setRealm(float realm) {
        this.realm = realm;
    }

    public void addRealm(float add) {
        setRealm(Math.min(realm + add, 10));
    }

    public void subRealm(float sub) {
        setRealm(Math.max(realm - sub, 0));
    }
}
