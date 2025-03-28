package galaxygameryt.cultivation_mastery.capabilites.realm;

import net.minecraft.nbt.CompoundTag;

public class PlayerRealm {
    private float realm = 0f;
    private static final int max_realm = 10;
    private static final int min_body = 0;

    public float getRealm() {
        return realm;
    }

    public void addRealm(float add) {
        setRealm(Math.min(realm + add, max_realm));
    }

    public void subRealm(float sub) {
        setRealm(Math.max(realm - sub, min_body));
    }

    public void setRealm(float num) {
        this.realm = num;
    }

    public void copyFrom(PlayerRealm source) {
        this.realm = source.realm;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("realm", realm);
    }

    public void loadNBTData(CompoundTag nbt) {
        realm = nbt.getFloat("realm");
    }
}
