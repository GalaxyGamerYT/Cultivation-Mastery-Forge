package galaxygameryt.cultivation_mastery.capabilites.max_qi;

import net.minecraft.nbt.CompoundTag;

public class PlayerMaxQi {
    private int max_qi = 100;

    public int getMaxQi() {
        return max_qi;
    }

    public void setMaxQi(int num) {
        this.max_qi = num;
    }

    public void copyFrom(PlayerMaxQi source) {
        this.max_qi = source.max_qi;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("max_qi", max_qi);
    }

    public void loadNBTData(CompoundTag nbt) {
        max_qi = nbt.getInt("max_qi");
    }
}
