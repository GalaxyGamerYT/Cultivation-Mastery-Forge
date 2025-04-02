package galaxygameryt.cultivation_mastery.util.helpers;

import galaxygameryt.cultivation_mastery.util.player_data.ServerPlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class HashMapHelper {
    public static HashMap<UUID, ServerPlayerData> deepClone(HashMap<UUID, ServerPlayerData> from) {
        HashMap<UUID, ServerPlayerData> new_hash_map = new HashMap<>();
        Set<Map.Entry<UUID, ServerPlayerData>> entries = from.entrySet();
        for (Map.Entry<UUID, ServerPlayerData> mapEntry : entries) {
            new_hash_map.put(mapEntry.getKey(), mapEntry.getValue());
        }
        return new_hash_map;
    }
}
