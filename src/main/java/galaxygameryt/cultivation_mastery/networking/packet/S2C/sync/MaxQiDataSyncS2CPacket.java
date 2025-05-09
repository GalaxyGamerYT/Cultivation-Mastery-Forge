package galaxygameryt.cultivation_mastery.networking.packet.S2C.sync;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MaxQiDataSyncS2CPacket {
    private final int max_qi;

    public MaxQiDataSyncS2CPacket(int max_qi) {
        this.max_qi = max_qi;
    }

    public MaxQiDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.max_qi = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(max_qi);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!

            if(max_qi != CultivationMastery.CLIENT_PLAYER_DATA.getMaxQi()) {
                CultivationMastery.CLIENT_PLAYER_DATA.setMaxQi(max_qi);
            }
        });
        return true;
    }

}
