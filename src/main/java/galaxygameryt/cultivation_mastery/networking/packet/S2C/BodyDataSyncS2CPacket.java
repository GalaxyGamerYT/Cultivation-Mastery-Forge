package galaxygameryt.cultivation_mastery.networking.packet.S2C;

import galaxygameryt.cultivation_mastery.client.data.ClientBodyData;
import galaxygameryt.cultivation_mastery.client.data.ClientQiData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

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
            ClientBodyData.set(body);
        });
        return true;
    }

}
