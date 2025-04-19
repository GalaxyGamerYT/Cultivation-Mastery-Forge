package galaxygameryt.cultivation_mastery.util.data.player;

import galaxygameryt.cultivation_mastery.Realms;
import galaxygameryt.cultivation_mastery.realms.base.MajorRealm;

public class ClientPlayerData extends PlayerData {
    public ClientPlayerData() {

    }

    public String getRealmDisplay() {
        int majorRealmValue = (int) Math.floor(realm);
        int minorRealmValue = (int) Math.floor((realm*10)-(majorRealmValue*10));

        MajorRealm majorRealm = Realms.REALMS[majorRealmValue];

        return majorRealm.displayRealm(minorRealmValue);
    }
}
