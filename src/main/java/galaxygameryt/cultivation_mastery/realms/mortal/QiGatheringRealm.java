package galaxygameryt.cultivation_mastery.realms.mortal;

import galaxygameryt.cultivation_mastery.realms.base.MajorRealm;

public class QiGatheringRealm extends MajorRealm {
    private static final int[] maxQi = {100, 150, 250, 400, 600, 750, 1000, 1150};

    public QiGatheringRealm() {
        this.name = "Qi Gathering";
        this.max = 2.8f;
    }

    public static int getMaxQi(int index) {
        if (index >= 0 && index < maxQi.length) {
            return maxQi[index];
        }
        return maxQi[maxQi.length-1];
    }
}
