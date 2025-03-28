package galaxygameryt.cultivation_mastery.client.data;

public class ClientRealmData {
    private static float playerRealm;

    public static void set(float realm) {
        ClientRealmData.playerRealm = realm;
    }

    public static float getPlayerRealm() {
        return playerRealm;
    }
}
