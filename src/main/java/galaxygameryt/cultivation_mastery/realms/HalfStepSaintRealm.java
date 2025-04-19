package galaxygameryt.cultivation_mastery.realms;

import galaxygameryt.cultivation_mastery.realms.base.MajorRealm;

public class HalfStepSaintRealm extends MajorRealm {
    public HalfStepSaintRealm() {
        this.name = "Half-Step Saint";
        this.levels = 1;
        this.max = 7;
    }

    @Override
    public String displayRealm(int minor) {
        return name;
    }
}
