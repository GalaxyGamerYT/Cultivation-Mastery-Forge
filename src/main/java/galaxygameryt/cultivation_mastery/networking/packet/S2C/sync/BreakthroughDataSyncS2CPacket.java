package galaxygameryt.cultivation_mastery.networking.packet.S2C.sync;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BreakthroughDataSyncS2CPacket {
    boolean breakthrough;

    public BreakthroughDataSyncS2CPacket(boolean breakthrough) {
        this.breakthrough = breakthrough;
    }

    public BreakthroughDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.breakthrough = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(breakthrough);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!

            if(breakthrough != CultivationMastery.CLIENT_PLAYER_DATA.getBreakthrough()) {
                CultivationMastery.CLIENT_PLAYER_DATA.setBreakthrough(breakthrough);
            }
        });
        return true;
    }

}
