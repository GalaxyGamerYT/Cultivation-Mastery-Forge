package galaxygameryt.cultivation_mastery.capabilites.body;

import net.minecraft.nbt.CompoundTag;

public class PlayerBody {
    private float body = 0f;
    private static final int max_body = 10;
    private static final int min_body = 0;

    public float getBody() {
        return body;
    }

    public void addBody(float add) {
        setBody(Math.min(body + add, max_body));
    }

    public void subBody(float sub) {
        setBody(Math.max(body - sub, min_body));
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
