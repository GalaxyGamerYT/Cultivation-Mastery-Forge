package galaxygameryt.cultivation_mastery.util.data.player;

import galaxygameryt.cultivation_mastery.realms.Realms;
import galaxygameryt.cultivation_mastery.util.helpers.MathHelper;

public class PlayerData {
    // Constants
    protected final int MAX_BODY = 100;
    protected final int MAX_REALM = Realms.REALMS.length-1;

    // Boolean Data
    protected boolean cultivation = false;
    protected boolean meditating = false;
    protected boolean breakthrough = false;

    // Data
    protected int maxQi = 50;
    protected float qiIncrease = 0.1f;
    protected float qi = 0;
    protected float body = 0;
    protected float realm = 0;

    protected int envQiMulti = 1;
    protected int baseQiMulti = 1;

    public PlayerData() {}

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
        addQi((qiIncrease * baseQiMulti) * envQiMulti);
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
        this.realm = Math.min(realm, MAX_REALM);
    }

    public void addRealm(float add) {
        setRealm(Math.min(MathHelper.roundFloat(realm + add, 2), MAX_REALM));
    }

    public void subRealm(float sub) {
        setRealm(Math.max(MathHelper.roundFloat(realm - sub,2), 0));
    }

    // Environment Multiplier
    public int getEnvQiMulti() {
        return envQiMulti;
    }

    public void setEnvQiMulti(int envQiMulti) {
        this.envQiMulti = envQiMulti;
    }

    public void addQiEnvMulti(int add) {
        setEnvQiMulti(envQiMulti + add);
    }

    public void subQiEnvMulti(int sub) {
        setEnvQiMulti(Math.max(envQiMulti - sub, 1));
    }

    // Base Qi Multiplier
    public int getBaseQiMulti() {
        return baseQiMulti;
    }

    public void setBaseQiMulti(int baseQiMulti) {
        this.baseQiMulti = baseQiMulti;
    }

    public void addBaseQiMulti(int add) {
        setBaseQiMulti(baseQiMulti + add);
    }

    public void subBaseQiMulti(int sub) {
        setBaseQiMulti(Math.max(baseQiMulti - sub, 1));
    }

    // Extra Data
    public int getMajorRealm() {
        return (int) Math.floor(realm);
    }

}
