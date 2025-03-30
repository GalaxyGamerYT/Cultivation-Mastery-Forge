package galaxygameryt.cultivation_mastery.capabilites.qi;

import net.minecraft.nbt.CompoundTag;

public class PlayerQi {
    private float qi = 0;

    public float getQi() {
        return qi;
    }

    public void setQi(float num) {
        this.qi = num;
    }

    public void copyFrom(PlayerQi source) {
        this.qi = source.qi;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("qi", qi);
    }

    public void loadNBTData(CompoundTag nbt) {
        qi = nbt.getFloat("qi");
    }

}
