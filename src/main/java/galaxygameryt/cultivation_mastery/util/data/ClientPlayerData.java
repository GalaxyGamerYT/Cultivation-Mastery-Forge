package galaxygameryt.cultivation_mastery.util.data;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.objects.MajorRealmObjectType;
import galaxygameryt.cultivation_mastery.util.objects.MinorRealmObjectType;

import java.util.UUID;

public class ClientPlayerData extends PlayerData{
    public ClientPlayerData(UUID playerUUID) {
        super(playerUUID);
    }

    public String getRealmDisplay() {
        int majorRealmValue = (int) Math.floor(realm);
        int minorRealmValue = (int) Math.floor((realm*10)-(majorRealmValue*10));

        MajorRealmObjectType majorRealm = CultivationMastery.REALMS[majorRealmValue];
        MinorRealmObjectType minorRealm = majorRealm.minorRealms[minorRealmValue];

        return String.format("%s %s %s", minorRealm.prefix, majorRealm.name, minorRealm.suffix);
    }
}
