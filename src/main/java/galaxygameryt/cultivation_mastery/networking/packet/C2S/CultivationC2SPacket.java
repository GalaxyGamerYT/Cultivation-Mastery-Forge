package galaxygameryt.cultivation_mastery.networking.packet.C2S;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.player.ServerPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CultivationC2SPacket {
    boolean cultivation;
    UUID playerId;

    public CultivationC2SPacket(boolean cultivation, UUID playerId) {
        this.cultivation = cultivation;
        this.playerId = playerId;
    }

    public CultivationC2SPacket(FriendlyByteBuf buf) {
        this.cultivation = buf.readBoolean();
        this.playerId = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(cultivation);
        buf.writeUUID(playerId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(playerId);

            // Toggle cultivation boolean
            playerData.setCultivation(cultivation);
            CultivationMastery.SERVER_PLAYER_DATA_MAP.put(playerId, playerData);
        });
        return true;
    }

}
