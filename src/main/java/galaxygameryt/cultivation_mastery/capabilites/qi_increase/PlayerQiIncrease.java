package galaxygameryt.cultivation_mastery.capabilites.qi_increase;

import net.minecraft.nbt.CompoundTag;

public class PlayerQiIncrease {
    private float qi_increase = 0.1f;

    public float getQi_increase() {
        return qi_increase;
    }

    public void addQiIncrease(float add) {
        setQi_increase(qi_increase + add);
    }

    public void subQiIncrease(float sub) {
        setQi_increase(Math.max(qi_increase - sub, 0));
    }

    public void setQi_increase(float num) {
        this.qi_increase = num;
    }

    public void copyFrom(PlayerQiIncrease source) {
        this.qi_increase = source.qi_increase;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("qi_increase", qi_increase);
    }

    public void loadNBTData(CompoundTag nbt) {
        qi_increase = nbt.getFloat("qi_increase");
    }
}
