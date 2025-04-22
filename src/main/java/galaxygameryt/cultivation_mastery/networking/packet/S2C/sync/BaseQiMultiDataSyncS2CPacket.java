package galaxygameryt.cultivation_mastery.networking.packet.S2C.sync;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BaseQiMultiDataSyncS2CPacket {
    private final int baseQiMulti;

    public BaseQiMultiDataSyncS2CPacket(int baseQiMulti) {
        this.baseQiMulti = baseQiMulti;
    }

    public BaseQiMultiDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.baseQiMulti = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(baseQiMulti);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!

            if(baseQiMulti != CultivationMastery.CLIENT_PLAYER_DATA.getBaseQiMulti()) {
                CultivationMastery.CLIENT_PLAYER_DATA.setBaseQiMulti(baseQiMulti);
            }
        });
        return true;
    }

}
