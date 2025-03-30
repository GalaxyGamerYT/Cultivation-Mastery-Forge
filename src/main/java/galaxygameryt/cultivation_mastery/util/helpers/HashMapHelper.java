package galaxygameryt.cultivation_mastery.util.helpers;

import galaxygameryt.cultivation_mastery.util.data.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class HashMapHelper {
    public static HashMap<UUID, PlayerData> deepClone(HashMap<UUID, PlayerData> from) {
        HashMap<UUID, PlayerData> new_hash_map = new HashMap<>();
        Set<Map.Entry<UUID, PlayerData>> entries = from.entrySet();
        for (Map.Entry<UUID, PlayerData> mapEntry : entries) {
            new_hash_map.put(mapEntry.getKey(), mapEntry.getValue());
        }
        return new_hash_map;
    }
}
