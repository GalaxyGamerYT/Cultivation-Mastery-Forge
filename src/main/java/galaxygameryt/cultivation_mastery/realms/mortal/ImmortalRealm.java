package galaxygameryt.cultivation_mastery.realms.mortal;

import galaxygameryt.cultivation_mastery.realms.base.MajorRealm;

public class ImmortalRealm extends MajorRealm {
    public ImmortalRealm() {
        this.name = "Mortal";
        this.levels = 1;
        this.max = 100;
    }

    @Override
    public String displayRealm(int minor) {
        return name;
    }
}
