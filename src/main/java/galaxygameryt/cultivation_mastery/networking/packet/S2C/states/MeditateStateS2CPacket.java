package galaxygameryt.cultivation_mastery.networking.packet.S2C.states;

import galaxygameryt.cultivation_mastery.client.CultivationMasteryClient;
import galaxygameryt.cultivation_mastery.util.enums.Toasts;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class MeditateStateS2CPacket {
    private final UUID playerId;
    private final boolean meditating;

    public MeditateStateS2CPacket(UUID playerId, boolean meditating) {
        this.playerId = playerId;
        this.meditating = meditating;
    }

    public MeditateStateS2CPacket(FriendlyByteBuf buf) {
        this.playerId = buf.readUUID();
        this.meditating = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(playerId);
        buf.writeBoolean(meditating);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!


        });
        return true;
    }

}
