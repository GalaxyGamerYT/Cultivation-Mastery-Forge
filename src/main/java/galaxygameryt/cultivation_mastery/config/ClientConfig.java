package galaxygameryt.cultivation_mastery.config;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.S2C.CultivationSyncS2CPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue CULTIVATION_GUI = BUILDER
            .comment("Whether the cultivation gui should be shown")
            .define("cultivationGUI", false);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean cultivationGUI;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        cultivationGUI = CULTIVATION_GUI.get();
        ModMessages.sendToServer(new CultivationSyncS2CPacket(cultivationGUI));
    }
}
