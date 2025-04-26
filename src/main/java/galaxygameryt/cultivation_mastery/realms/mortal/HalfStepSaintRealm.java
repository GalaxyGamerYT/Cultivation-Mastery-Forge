package galaxygameryt.cultivation_mastery.realms.mortal;

import galaxygameryt.cultivation_mastery.realms.base.MajorRealm;

import java.awt.*;

public class HalfStepSaintRealm extends MajorRealm {
    public HalfStepSaintRealm() {
        this.name = "Half-Step Saint";
        this.levels = 1;
        this.max = 7;
        this.aura = new Color(0xf8e41e);
    }

    @Override
    public String displayRealm(int minor) {
        return name;
    }
}
