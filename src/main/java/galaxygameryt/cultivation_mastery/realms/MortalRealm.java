package galaxygameryt.cultivation_mastery.realms;

import galaxygameryt.cultivation_mastery.realms.base.MajorRealm;

public class MortalRealm extends MajorRealm {
    public MortalRealm() {
        this.name = "Mortal";
        this.levels = 1;
        this.max = 0;
    }

    @Override
    public String displayRealm(int minor) {
        return name;
    }
}
