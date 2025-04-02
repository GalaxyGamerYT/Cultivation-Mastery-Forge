package galaxygameryt.cultivation_mastery.networking.packet.S2C;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.player_data.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
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

            if(realm != CultivationMastery.CLIENT_PLAYER_DATA.getRealm()) {
                CultivationMastery.CLIENT_PLAYER_DATA.setRealm(realm);
            }
        });
        return true;
    }

}
