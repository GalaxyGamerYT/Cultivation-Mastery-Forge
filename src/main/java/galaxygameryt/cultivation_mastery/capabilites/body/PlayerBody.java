package galaxygameryt.cultivation_mastery.capabilites.body;

import net.minecraft.nbt.CompoundTag;

public class PlayerBody {
    private float body = 0f;

    public float getBody() {
        return body;
    }

    public void setBody(float num) {
        this.body = num;
    }

    public void copyFrom(PlayerBody source) {
        this.body = source.body;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("body", body);
    }

    public void loadNBTData(CompoundTag nbt) {
        body = nbt.getFloat("body");
    }
}
