package galaxygameryt.cultivation_mastery.networking.packet.S2C;

import galaxygameryt.cultivation_mastery.client.data.ClientBodyData;
import galaxygameryt.cultivation_mastery.client.data.ClientRealmData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RealmDataSyncS2CPacket {
    private final float realm;

    public RealmDataSyncS2CPacket(float body) {
        this.realm = body;
    }

    public RealmDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.realm = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(realm);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientRealmData.set(realm);
        });
        return true;
    }

}
