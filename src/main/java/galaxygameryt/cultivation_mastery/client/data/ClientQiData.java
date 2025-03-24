package galaxygameryt.cultivation_mastery.client.data;

public class ClientQiData {
    private static float playerQi;

    public static void set(float qi) {
        ClientQiData.playerQi = qi;
    }

    public static float getPlayerQi() {
        return playerQi;
    }
}
