package galaxygameryt.cultivation_mastery.networking.packet.S2C.sync;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnvQiMultiDataSyncS2CPacket {
    private final int envQiMulti;

    public EnvQiMultiDataSyncS2CPacket(int envQiMulti) {
        this.envQiMulti = envQiMulti;
    }

    public EnvQiMultiDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.envQiMulti = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(envQiMulti);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!

            if(envQiMulti != CultivationMastery.CLIENT_PLAYER_DATA.getEnvQiMulti()) {
                CultivationMastery.CLIENT_PLAYER_DATA.setEnvQiMulti(envQiMulti);
            }
        });
        return true;
    }

}
