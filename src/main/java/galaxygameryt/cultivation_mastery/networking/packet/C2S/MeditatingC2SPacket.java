package galaxygameryt.cultivation_mastery.networking.packet.C2S;

import galaxygameryt.cultivation_mastery.capabilites.meditating.PlayerMeditatingProvider;
import galaxygameryt.cultivation_mastery.capabilites.qi.PlayerQiProvider;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.QiDataSyncS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MeditatingC2SPacket {
    private static final String MESSAGE_MEDITATE = "message.cultivation_mastery.meditate";
    private static final String MESSAGE_NOT_MEDITATE = "message.cultivation_mastery.not_meditate";

    boolean meditating;

    public MeditatingC2SPacket(boolean meditating) {
        this.meditating = meditating;
    }

    public MeditatingC2SPacket(FriendlyByteBuf buf) {
        this.meditating = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(meditating);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            // Toggle meditating boolean
            player.getCapability(PlayerMeditatingProvider.PLAYER_MEDITATING).ifPresent(meditating -> {
                if(this.meditating) {
                    // Meditating
                    meditating.setMeditating(true);
                    player.sendSystemMessage(Component.translatable(MESSAGE_MEDITATE).withStyle(ChatFormatting.GOLD));
                    level.playSound(player, player.getOnPos(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS,
                            0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                } else if (!this.meditating) {
                    // Not Meditating
                    meditating.setMeditating(false);
                    player.sendSystemMessage(Component.translatable(MESSAGE_NOT_MEDITATE).withStyle(ChatFormatting.GOLD));
                    level.playSound(player, player.getOnPos(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS,
                            0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                }
            });
        });
        return true;
    }

}
