package galaxygameryt.cultivation_mastery.realms.mortal;

import galaxygameryt.cultivation_mastery.realms.base.MajorRealm;

public class ReturnToOriginRealm extends MajorRealm {
    public ReturnToOriginRealm() {
        this.name = "Return To Origin";
        this.levels = 3;
        this.max = 8.3f;
    }

    @Override
    public String displayRealm(int minor) {
        String output = "";
        output += String.valueOf(minor);
        switch (minor) {
            case 1:
                output += "st";
            case 2:
                output += "nd";
            case 3:
                output += "rd";
        }
        output += " Order ";
        output += name;
        return output;
    }
}
