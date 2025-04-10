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

//        This Try Catch statement is a temporary fix, If a solution is found please remove.
        MinorRealmObjectType minorRealm = new MinorRealmObjectType();
        try {
            minorRealm = majorRealm.minorRealms[minorRealmValue];

        } catch (Exception e) {
            CultivationMastery.LOGGER.debug(String.valueOf(e));
        }

        return String.format("%s %s %s", minorRealm.prefix, majorRealm.name, minorRealm.suffix);
    }
}
