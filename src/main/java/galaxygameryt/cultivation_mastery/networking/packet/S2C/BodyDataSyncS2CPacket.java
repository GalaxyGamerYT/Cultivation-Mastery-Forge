package galaxygameryt.cultivation_mastery.networking.packet.S2C;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class BodyDataSyncS2CPacket {
    private final float body;

    public BodyDataSyncS2CPacket(float body) {
        this.body = body;
    }

    public BodyDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.body = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(body);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            Player player = context.getSender();
            UUID playerId = player.getUUID();

            ClientPlayerData clientPlayerData = CultivationMastery.CLIENT_PLAYER_DATA_MAP.get(playerId);
            if(body != clientPlayerData.getBody()) {
                clientPlayerData.setBody(body);
                CultivationMastery.CLIENT_PLAYER_DATA_MAP.put(playerId, clientPlayerData);
            }
        });
        return true;
    }

}
