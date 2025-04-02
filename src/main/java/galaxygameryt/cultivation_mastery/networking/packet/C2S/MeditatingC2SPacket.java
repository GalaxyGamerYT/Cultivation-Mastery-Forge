package galaxygameryt.cultivation_mastery.networking.packet.C2S;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.player_data.ServerPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class MeditatingC2SPacket {
    boolean meditating;
    UUID playerId;

    public MeditatingC2SPacket(boolean meditating, UUID playerId) {
        this.meditating = meditating;
        this.playerId = playerId;
    }

    public MeditatingC2SPacket(FriendlyByteBuf buf) {
        this.meditating = buf.readBoolean();
        this.playerId = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(meditating);
        buf.writeUUID(playerId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(playerId);

            // Toggle meditating boolean
            playerData.setMeditating(meditating);
            CultivationMastery.SERVER_PLAYER_DATA_MAP.put(playerId, playerData);
        });
        return true;
    }

}
