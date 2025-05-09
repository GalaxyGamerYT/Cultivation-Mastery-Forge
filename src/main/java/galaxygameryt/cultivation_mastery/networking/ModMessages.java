package galaxygameryt.cultivation_mastery.networking;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.*;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.*;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.sync.*;
import galaxygameryt.cultivation_mastery.util.Logger;
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
        Logger.info("Registering Networking");
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        C2S();
        S2C();
    }

    private static void C2S() {
        INSTANCE.messageBuilder(ExampleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExampleC2SPacket::new)
                .encoder(ExampleC2SPacket::toBytes)
                .consumerMainThread(ExampleC2SPacket::handle)
                .add();

        C2SSync();

        INSTANCE.messageBuilder(MeditatingC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MeditatingC2SPacket::new)
                .encoder(MeditatingC2SPacket::toBytes)
                .consumerMainThread(MeditatingC2SPacket::handle)
                .add();
        INSTANCE.messageBuilder(CultivationC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CultivationC2SPacket::new)
                .encoder(CultivationC2SPacket::toBytes)
                .consumerMainThread(CultivationC2SPacket::handle)
                .add();
        INSTANCE.messageBuilder(BreakthroughKeyC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BreakthroughKeyC2SPacket::new)
                .encoder(BreakthroughKeyC2SPacket::toBytes)
                .consumerMainThread(BreakthroughKeyC2SPacket::handle)
                .add();
    }

    private static void C2SSync() {

    }

    private static void S2C() {
        S2CSync();

        // Other Packets
        INSTANCE.messageBuilder(ToastS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ToastS2CPacket::new)
                .encoder(ToastS2CPacket::toBytes)
                .consumerMainThread(ToastS2CPacket::handle)
                .add();
    }

    private static void S2CSync() {
        INSTANCE.messageBuilder(QiDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(QiDataSyncS2CPacket::new)
                .encoder(QiDataSyncS2CPacket::toBytes)
                .consumerMainThread(QiDataSyncS2CPacket::handle)
                .add();
        INSTANCE.messageBuilder(CultivationDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CultivationDataSyncS2CPacket::new)
                .encoder(CultivationDataSyncS2CPacket::toBytes)
                .consumerMainThread(CultivationDataSyncS2CPacket::handle)
                .add();
        INSTANCE.messageBuilder(BodyDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BodyDataSyncS2CPacket::new)
                .encoder(BodyDataSyncS2CPacket::toBytes)
                .consumerMainThread(BodyDataSyncS2CPacket::handle)
                .add();
        INSTANCE.messageBuilder(RealmDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RealmDataSyncS2CPacket::new)
                .encoder(RealmDataSyncS2CPacket::toBytes)
                .consumerMainThread(RealmDataSyncS2CPacket::handle)
                .add();
        INSTANCE.messageBuilder(MaxQiDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MaxQiDataSyncS2CPacket::new)
                .encoder(MaxQiDataSyncS2CPacket::toBytes)
                .consumerMainThread(MaxQiDataSyncS2CPacket::handle)
                .add();
        INSTANCE.messageBuilder(BaseQiMultiDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BaseQiMultiDataSyncS2CPacket::new)
                .encoder(BaseQiMultiDataSyncS2CPacket::toBytes)
                .consumerMainThread(BaseQiMultiDataSyncS2CPacket::handle)
                .add();
        INSTANCE.messageBuilder(EnvQiMultiDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnvQiMultiDataSyncS2CPacket::new)
                .encoder(EnvQiMultiDataSyncS2CPacket::toBytes)
                .consumerMainThread(EnvQiMultiDataSyncS2CPacket::handle)
                .add();
        INSTANCE.messageBuilder(BreakthroughDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BreakthroughDataSyncS2CPacket::new)
                .encoder(BreakthroughDataSyncS2CPacket::toBytes)
                .consumerMainThread(BreakthroughDataSyncS2CPacket::handle)
                .add();
        INSTANCE.messageBuilder(BreakthroughKeyDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BreakthroughKeyDataSyncS2CPacket::new)
                .encoder(BreakthroughKeyDataSyncS2CPacket::toBytes)
                .consumerMainThread(BreakthroughKeyDataSyncS2CPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
