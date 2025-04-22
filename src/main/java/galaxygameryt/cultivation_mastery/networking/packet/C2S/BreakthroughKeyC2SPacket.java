package galaxygameryt.cultivation_mastery.networking.packet.C2S;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.player.ServerPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class BreakthroughKeyC2SPacket {
    boolean breakthroughKey;
    UUID playerId;

    public BreakthroughKeyC2SPacket(boolean breakthroughKey, UUID playerId) {
        this.breakthroughKey = breakthroughKey;
        this.playerId = playerId;
    }

    public BreakthroughKeyC2SPacket(FriendlyByteBuf buf) {
        this.breakthroughKey = buf.readBoolean();
        this.playerId = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(breakthroughKey);
        buf.writeUUID(playerId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(playerId);

            // Toggle breakthrough key
            playerData.setBreakthroughKey(breakthroughKey);
            CultivationMastery.SERVER_PLAYER_DATA_MAP.put(playerId, playerData);
        });
        return true;
    }

}
