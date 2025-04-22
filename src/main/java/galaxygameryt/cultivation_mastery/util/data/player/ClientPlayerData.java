package galaxygameryt.cultivation_mastery.util.data.player;

import galaxygameryt.cultivation_mastery.realms.Realms;
import galaxygameryt.cultivation_mastery.realms.base.MajorRealm;

import java.util.UUID;

public class ClientPlayerData extends PlayerData {
    // Specialised
    private boolean joined;

    public ClientPlayerData() {
        this(false);
    }

    public ClientPlayerData(boolean joined) {
        this.joined = joined;
    }

    // Specialised
    // Joined
    public boolean getJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    // Display
    public String getRealmDisplay() {
        int majorRealmValue = (int) Math.floor(realm);
        int minorRealmValue = (int) Math.floor((realm*10)-(majorRealmValue*10));

        MajorRealm majorRealm = Realms.REALMS[majorRealmValue];

        return majorRealm.displayRealm(minorRealmValue);
    }
}
