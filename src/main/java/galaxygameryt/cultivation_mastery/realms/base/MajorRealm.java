package galaxygameryt.cultivation_mastery.realms.base;

import galaxygameryt.cultivation_mastery.util.helpers.IntHelper;

import java.awt.*;

public abstract class MajorRealm {
    public String name = "";
    public int levels = 9;
    public float max = 0;
    public Color aura = null;

    public MajorRealm() {}

    public MajorRealm(String name, int levels, float max, int aura) {
        this.name = name;
        this.levels = levels;
        this.max = max;
        this.aura = new Color(aura);
    }

    public String displayRealm(int minor) {
        String output = name;
        if (levels > 1 && minor < levels) {
            output += String.format(" %s", IntHelper.toNumerals(minor+1));
        }
        return output;
    }
}
