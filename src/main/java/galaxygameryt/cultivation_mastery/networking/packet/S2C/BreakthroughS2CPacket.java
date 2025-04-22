package galaxygameryt.cultivation_mastery.networking.packet.S2C;

import galaxygameryt.cultivation_mastery.client.CultivationMasteryClient;
import galaxygameryt.cultivation_mastery.util.enums.Toasts;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BreakthroughS2CPacket {
    public BreakthroughS2CPacket() {

    }

    public BreakthroughS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!

            CultivationMasteryClient.addToast(Toasts.BREAKTHROUGH);
        });
        return true;
    }

}
