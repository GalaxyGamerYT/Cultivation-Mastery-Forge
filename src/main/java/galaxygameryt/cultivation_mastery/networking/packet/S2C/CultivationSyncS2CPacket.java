package galaxygameryt.cultivation_mastery.networking.packet.S2C;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.player_data.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CultivationSyncS2CPacket {
    private final boolean cultivation;

    public CultivationSyncS2CPacket(boolean cultivation) {
        this.cultivation = cultivation;
    }

    public CultivationSyncS2CPacket(FriendlyByteBuf buf) {
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
