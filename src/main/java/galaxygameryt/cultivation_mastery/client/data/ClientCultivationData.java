package galaxygameryt.cultivation_mastery.client.data;

public class ClientCultivationData {
    private static boolean playerCultivation;

    public static void set(boolean cultivation) {
        ClientCultivationData.playerCultivation = cultivation;
    }

    public static boolean getPlayerCultivation() {
        return playerCultivation;
    }
}
