package galaxygameryt.cultivation_mastery.client.data;

public class ClientBodyData {
    private static float playerBody;

    public static void set(float body) {
        ClientBodyData.playerBody = body;
    }

    public static float getPlayerBody() {
        return playerBody;
    }
}
