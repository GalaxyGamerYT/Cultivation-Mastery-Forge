package galaxygameryt.cultivation_mastery.util.objects;

import galaxygameryt.cultivation_mastery.util.helpers.MathHelper;

public class MajorRealmObjectType {
    public String name;
    public int levels;
    public float maxLevelFraction;
    public MinorRealmObjectType[] minorRealms;

    public MajorRealmObjectType(String name, MinorRealmObjectType[] minorRealms) {
        this.name = name;
        this.levels = minorRealms.length;
        this.maxLevelFraction = MathHelper.roundFloat((float) minorRealms.length/10, 1);
        this.minorRealms = minorRealms;
    }
}
