package galaxygameryt.cultivation_mastery.capabilites.realm;

import net.minecraft.nbt.CompoundTag;

public class PlayerRealm {
    private float realm = 0f;

    public float getRealm() {
        return realm;
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
