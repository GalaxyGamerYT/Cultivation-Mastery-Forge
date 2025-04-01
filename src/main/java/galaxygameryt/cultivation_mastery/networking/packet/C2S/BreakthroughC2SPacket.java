package galaxygameryt.cultivation_mastery.networking.packet.C2S;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.ServerPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BreakthroughC2SPacket {
    public BreakthroughC2SPacket() {
    }

    public BreakthroughC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerPlayerData playerdata = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());

            // Toggle breakthrough
            playerdata.setBreakthrough(true);
            CultivationMastery.SERVER_PLAYER_DATA_MAP.put(player.getUUID(), playerdata);
        });
        return true;
    }

}
