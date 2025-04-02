package galaxygameryt.cultivation_mastery.networking.packet.S2C;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.player_data.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class QiDataSyncS2CPacket {
    private final float qi;

    public QiDataSyncS2CPacket(float qi) {
        this.qi = qi;
    }

    public QiDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.qi  = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(qi);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!

            if (qi != CultivationMastery.CLIENT_PLAYER_DATA.getQi()) {
                CultivationMastery.CLIENT_PLAYER_DATA.setQi(qi);
            }
        });
        return true;
    }

}
