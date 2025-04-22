package galaxygameryt.cultivation_mastery.networking.packet.S2C;

import galaxygameryt.cultivation_mastery.client.CultivationMasteryClient;
import galaxygameryt.cultivation_mastery.util.enums.Toasts;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToastS2CPacket {
    Toasts toast;
    public ToastS2CPacket(Toasts toast) {
        this.toast = toast;
    }

    public ToastS2CPacket(FriendlyByteBuf buf) {
        toast = buf.readEnum(Toasts.class);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(toast);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!

            CultivationMasteryClient.addToast(toast);
        });
        return true;
    }

}
