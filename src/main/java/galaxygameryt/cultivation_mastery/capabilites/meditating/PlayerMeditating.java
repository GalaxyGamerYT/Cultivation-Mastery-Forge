package galaxygameryt.cultivation_mastery.capabilites.meditating;

import net.minecraft.nbt.CompoundTag;

public class PlayerMeditating {
    private boolean meditating = false;

    public boolean getMeditating() {
        return meditating;
    }

    public void setMeditating(boolean state) {
        this.meditating = state;
    }

    public void copyFrom(PlayerMeditating source) {
        this.meditating = source.meditating;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("meditating", meditating);
    }

    public void loadNBTData(CompoundTag nbt) {
        meditating = nbt.getBoolean("meditating");
    }
}
