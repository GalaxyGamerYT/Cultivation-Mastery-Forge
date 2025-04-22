package galaxygameryt.cultivation_mastery.util.data.capability;

import net.minecraft.nbt.CompoundTag;

public class PlayerCapability {
    // Boolean Data
    private boolean cultivation = false;
    private boolean breakthrough = false;

    // Int Data
    private int maxQi = 100;

    // Float Data
    private float qiIncrease = 0.1f;
    private float qi = 0;
    private float body = 0;
    private float realm = 0;

    // Data Getters and Setters
    // Cultivation
    public boolean getCultivation() {
        return cultivation;
    }

    public void setCultivation(boolean cultivation) {
        this.cultivation = cultivation;
    }

    // Breakthrough
    public boolean getBreakthrough() {
        return breakthrough;
    }

    public void setBreakthrough(boolean breakthrough) {
        this.breakthrough = breakthrough;
    }

    // Max Qi
    public int getMaxQi() {
        return maxQi;
    }

    public void setMaxQi(int maxQi) {
        this.maxQi = maxQi;
    }

    // Qi Increase
    public float getQiIncrease() {
        return qiIncrease;
    }

    public void setQiIncrease(float qiIncrease) {
        this.qiIncrease = qiIncrease;
    }

    // Qi
    public float getQi() {
        return qi;
    }

    public void setQi(float qi) {
        this.qi = qi;
    }

    // Body
    public float getBody() {
        return body;
    }

    public void setBody(float body) {
        this.body = body;
    }

    // Realm
    public float getRealm() {
        return realm;
    }

    public void setRealm(float realm) {
        this.realm = realm;
    }

    // IMPORTANT!!!!
    public void copyFrom(PlayerCapability source) {
        this.cultivation = source.cultivation;
        this.breakthrough = source.breakthrough;
        this.maxQi = source.maxQi;
        this.qiIncrease = source.qiIncrease;
        this.qi = source.qi;
        this.body = source.body;
        this.realm = source.realm;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("cultivation", cultivation);
        nbt.putBoolean("breakthrough", breakthrough);
        nbt.putInt("maxQi", maxQi);
        nbt.putFloat("qiIncrease", qiIncrease);
        nbt.putFloat("qi", qi);
        nbt.putFloat("body", body);
        nbt.putFloat("realm", realm);
    }

    public void loadNBTData(CompoundTag nbt) {
        cultivation = nbt.getBoolean("cultivation");
        breakthrough = nbt.getBoolean("breakthrough");
        maxQi = nbt.getInt("maxQi");
        qiIncrease = nbt.getFloat("qiIncrease");
        qi = nbt.getFloat("qi");
        body = nbt.getFloat("body");
        realm = nbt.getFloat("realm");
    }
}
