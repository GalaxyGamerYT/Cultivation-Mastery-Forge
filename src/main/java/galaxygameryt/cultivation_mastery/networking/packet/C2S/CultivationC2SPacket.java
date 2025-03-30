package galaxygameryt.cultivation_mastery.networking.packet.C2S;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.capabilites.cultivation.PlayerCultivationProvider;
import galaxygameryt.cultivation_mastery.util.data.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CultivationC2SPacket {
    public CultivationC2SPacket() {
    }

    public CultivationC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ClientPlayerData clientPlayerData = CultivationMastery.CLIENT_PLAYER_DATA_MAP.get(player.getUUID());

            // Toggle cultivation boolean
            player.getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).ifPresent(cultivation -> {
                cultivation.setCultivation(clientPlayerData.getCultivation());
            });
        });
        return true;
    }

}
