package galaxygameryt.cultivation_mastery.util.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerData {
    // Constants
    public static final int MAX_BODY = 100;
    public static final int MAX_REALM = 11;

    // Specialised
    public UUID playerUUID;

    // Boolean Data
    public boolean cultivation = false;
    public boolean meditating = false;
    public boolean breakthrough = false;

    // Data
    public int maxQi = 100;
    public float qiIncrease = 0.1f;
    public float qi = 0;
    public float body = 0;
    public float realm = 0;

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

    // Breakthrough
    public boolean getBreakthrough() {
        return breakthrough;
    }

    public void setBreakthrough(boolean breakthrough) {
        this.breakthrough = breakthrough;
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
        setQi(Math.min(qi + add, maxQi));
    }

    public void increase_qi() {
        addQi(qiIncrease);
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
        setBody(Math.min(body + add, MAX_BODY));
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
        setRealm(Math.min(realm + add, MAX_REALM));
    }

    public void subRealm(float sub) {
        setRealm(Math.max(realm - sub, 0));
    }
}
