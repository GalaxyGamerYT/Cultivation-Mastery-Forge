package galaxygameryt.cultivation_mastery.util.player_data;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.objects.MajorRealmObjectType;
import galaxygameryt.cultivation_mastery.util.objects.MinorRealmObjectType;
import net.minecraft.client.Minecraft;

import java.util.UUID;

public class ClientPlayerData extends PlayerData{
    public ClientPlayerData() {

    }

    public String getRealmDisplay() {
        int majorRealmValue = (int) Math.floor(realm);
        int minorRealmValue = (int) Math.floor((realm*10)-(majorRealmValue*10));

        MajorRealmObjectType majorRealm = CultivationMastery.REALMS[majorRealmValue];
        MinorRealmObjectType minorRealm = majorRealm.minorRealms[minorRealmValue];

        return String.format("%s %s %s", minorRealm.prefix, majorRealm.name, minorRealm.suffix);
    }
}
