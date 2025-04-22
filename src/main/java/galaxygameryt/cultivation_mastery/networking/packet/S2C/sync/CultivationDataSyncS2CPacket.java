package galaxygameryt.cultivation_mastery.networking.packet.S2C.sync;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CultivationDataSyncS2CPacket {
    private final boolean cultivation;

    public CultivationDataSyncS2CPacket(boolean cultivation) {
        this.cultivation = cultivation;
    }

    public CultivationDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.cultivation = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(cultivation);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!

            if(cultivation != CultivationMastery.CLIENT_PLAYER_DATA.getCultivation()) {
                CultivationMastery.CLIENT_PLAYER_DATA.setCultivation(cultivation);
            }
        });
        return true;
    }

}
