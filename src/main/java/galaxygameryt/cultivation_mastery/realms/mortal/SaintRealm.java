package galaxygameryt.cultivation_mastery.realms.mortal;

import galaxygameryt.cultivation_mastery.realms.base.MajorRealm;

import java.awt.*;

public class SaintRealm extends MajorRealm {
    public SaintRealm() {
        this.name = "Saint";
        this.levels = 3;
        this.max = 8.3f;
        this.aura = new Color(0x9600ff);
    }

    @Override
    public String displayRealm(int minor) {
        String output = "";
        output += String.valueOf(minor+1);
        switch (minor+1) {
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
