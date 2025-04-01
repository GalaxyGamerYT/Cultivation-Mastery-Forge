package galaxygameryt.cultivation_mastery.util.objects;

import galaxygameryt.cultivation_mastery.util.helpers.MathHelper;

public class MajorRealmObjectType {
    public String name;
    public int levels;
    public float maxLevelFraction;
    public MinorRealmObjectType[] minorRealms;

    public MajorRealmObjectType(String name, int levels, MinorRealmObjectType[] minorRealms) {
        this.name = name;
        this.levels = levels;
        this.maxLevelFraction = MathHelper.roundFloat((float) levels/10, 1);
        this.minorRealms = minorRealms;
    }
}
