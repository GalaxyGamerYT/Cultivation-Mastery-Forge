package galaxygameryt.cultivation_mastery.capabilites.cultivation;

import net.minecraft.nbt.CompoundTag;

public class PlayerCultivation {
    private boolean cultivation = false;

    public boolean getCultivation() {
        return cultivation;
    }

    public void setCultivation(boolean state) {
        this.cultivation = state;
    }

    public void copyFrom(PlayerCultivation source) {
        this.cultivation = source.cultivation;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("cultivation", cultivation);
    }

    public void loadNBTData(CompoundTag nbt) {
        cultivation = nbt.getBoolean("cultivation");
    }
}
