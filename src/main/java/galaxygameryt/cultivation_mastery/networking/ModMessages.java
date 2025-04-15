package galaxygameryt.cultivation_mastery.networking;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.BreakthroughC2SPacket;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.CultivationC2SPacket;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.ExampleC2SPacket;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.MeditatingC2SPacket;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // C2S - To The Server!
        net.messageBuilder(ExampleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExampleC2SPacket::new)
                .encoder(ExampleC2SPacket::toBytes)
                .consumerMainThread(ExampleC2SPacket::handle)
                .add();

        net.messageBuilder(MeditatingC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MeditatingC2SPacket::new)
                .encoder(MeditatingC2SPacket::toBytes)
                .consumerMainThread(MeditatingC2SPacket::handle)
                .add();
        net.messageBuilder(CultivationC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CultivationC2SPacket::new)
                .encoder(CultivationC2SPacket::toBytes)
                .consumerMainThread(CultivationC2SPacket::handle)
                .add();
        net.messageBuilder(BreakthroughC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BreakthroughC2SPacket::new)
                .encoder(BreakthroughC2SPacket::toBytes)
                .consumerMainThread(BreakthroughC2SPacket::handle)
                .add();

        // S2C - To The Client!
        net.messageBuilder(QiDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(QiDataSyncS2CPacket::new)
                .encoder(QiDataSyncS2CPacket::toBytes)
                .consumerMainThread(QiDataSyncS2CPacket::handle)
                .add();
        net.messageBuilder(CultivationDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CultivationDataSyncS2CPacket::new)
                .encoder(CultivationDataSyncS2CPacket::toBytes)
                .consumerMainThread(CultivationDataSyncS2CPacket::handle)
                .add();
        net.messageBuilder(BodyDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BodyDataSyncS2CPacket::new)
                .encoder(BodyDataSyncS2CPacket::toBytes)
                .consumerMainThread(BodyDataSyncS2CPacket::handle)
                .add();
        net.messageBuilder(RealmDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RealmDataSyncS2CPacket::new)
                .encoder(RealmDataSyncS2CPacket::toBytes)
                .consumerMainThread(RealmDataSyncS2CPacket::handle)
                .add();
        net.messageBuilder(MaxQiDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MaxQiDataSyncS2CPacket::new)
                .encoder(MaxQiDataSyncS2CPacket::toBytes)
                .consumerMainThread(MaxQiDataSyncS2CPacket::handle)
                .add();
        net.messageBuilder(BaseQiMultiDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BaseQiMultiDataSyncS2CPacket::new)
                .encoder(BaseQiMultiDataSyncS2CPacket::toBytes)
                .consumerMainThread(BaseQiMultiDataSyncS2CPacket::handle)
                .add();
        net.messageBuilder(EnvQiMultiDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnvQiMultiDataSyncS2CPacket::new)
                .encoder(EnvQiMultiDataSyncS2CPacket::toBytes)
                .consumerMainThread(EnvQiMultiDataSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
