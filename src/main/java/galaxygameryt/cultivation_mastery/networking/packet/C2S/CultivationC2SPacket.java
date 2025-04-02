package galaxygameryt.cultivation_mastery.networking.packet.C2S;

import galaxygameryt.cultivation_mastery.capabilites.cultivation.PlayerCultivationProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CultivationC2SPacket {
    boolean cultivation;

    public CultivationC2SPacket(boolean cultivation) {
        this.cultivation = cultivation;
    }

    public CultivationC2SPacket(FriendlyByteBuf buf) {
        this.cultivation = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(cultivation);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            // Toggle cultivation boolean
            player.getCapability(PlayerCultivationProvider.PLAYER_CULTIVATION).ifPresent(cultivation -> {
                cultivation.setCultivation(this.cultivation);
            });
        });
        return true;
    }

}
