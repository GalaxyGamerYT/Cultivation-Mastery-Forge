package galaxygameryt.cultivation_mastery.networking.packet.S2C.sync;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BreakthroughKeyDataSyncS2CPacket {
    boolean breakthroughKey;

    public BreakthroughKeyDataSyncS2CPacket(boolean breakthroughKey) {
        this.breakthroughKey = breakthroughKey;
    }

    public BreakthroughKeyDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.breakthroughKey = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(breakthroughKey);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!

            if(breakthroughKey != CultivationMastery.CLIENT_PLAYER_DATA.getBreakthroughKey()) {
                CultivationMastery.CLIENT_PLAYER_DATA.setBreakthroughKey(breakthroughKey);
            }
        });
        return true;
    }

}
