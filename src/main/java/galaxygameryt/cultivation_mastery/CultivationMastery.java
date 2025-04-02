package galaxygameryt.cultivation_mastery;

import com.mojang.logging.LogUtils;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.item.ModCreativeModeTabs;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.screen.ModMenuTypes;
import galaxygameryt.cultivation_mastery.util.player_data.ClientPlayerData;
import galaxygameryt.cultivation_mastery.util.player_data.ServerPlayerData;
import galaxygameryt.cultivation_mastery.util.objects.MajorRealmObjectType;
import galaxygameryt.cultivation_mastery.util.objects.MinorRealmObjectType;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CultivationMastery.MOD_ID)
public class CultivationMastery
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "cultivation_mastery";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Player Data
    public static final HashMap<UUID, ServerPlayerData> SERVER_PLAYER_DATA_MAP = new HashMap<>();
    public static final ClientPlayerData CLIENT_PLAYER_DATA = new ClientPlayerData();

    public static final MajorRealmObjectType[] REALMS = CultivationMastery.setCultivationRealms();

    public CultivationMastery(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
//        context.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
//        context.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
//        context.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    private static MajorRealmObjectType[] setCultivationRealms() {
        return new MajorRealmObjectType[]{
                new MajorRealmObjectType("Mortal", 1, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("", "")
                }),
                new MajorRealmObjectType("Body Tempering", 9, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("", "I"),
                        new MinorRealmObjectType("", "II"),
                        new MinorRealmObjectType("", "III"),
                        new MinorRealmObjectType("", "IV"),
                        new MinorRealmObjectType("", "V"),
                        new MinorRealmObjectType("", "VI"),
                        new MinorRealmObjectType("", "VII"),
                        new MinorRealmObjectType("", "VIII")
                }),
                new MajorRealmObjectType("Qi Refinement", 9, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("", "I"),
                        new MinorRealmObjectType("", "II"),
                        new MinorRealmObjectType("", "III"),
                        new MinorRealmObjectType("", "IV"),
                        new MinorRealmObjectType("", "V"),
                        new MinorRealmObjectType("", "VI"),
                        new MinorRealmObjectType("", "VII"),
                        new MinorRealmObjectType("", "VIII")
                }),
                new MajorRealmObjectType("Foundation Establishment", 9, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("", "I"),
                        new MinorRealmObjectType("", "II"),
                        new MinorRealmObjectType("", "III"),
                        new MinorRealmObjectType("", "IV"),
                        new MinorRealmObjectType("", "V"),
                        new MinorRealmObjectType("", "VI"),
                        new MinorRealmObjectType("", "VII"),
                        new MinorRealmObjectType("", "VIII")
                }),
                new MajorRealmObjectType("Core Formation", 9, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("", "I"),
                        new MinorRealmObjectType("", "II"),
                        new MinorRealmObjectType("", "III"),
                        new MinorRealmObjectType("", "IV"),
                        new MinorRealmObjectType("", "V"),
                        new MinorRealmObjectType("", "VI"),
                        new MinorRealmObjectType("", "VII"),
                        new MinorRealmObjectType("", "VIII")
                }),
                new MajorRealmObjectType("Golden Core", 9, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("", "I"),
                        new MinorRealmObjectType("", "II"),
                        new MinorRealmObjectType("", "III"),
                        new MinorRealmObjectType("", "IV"),
                        new MinorRealmObjectType("", "V"),
                        new MinorRealmObjectType("", "VI"),
                        new MinorRealmObjectType("", "VII"),
                        new MinorRealmObjectType("", "VIII")
                }),
                new MajorRealmObjectType("Nascent Soul", 9, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("", "I"),
                        new MinorRealmObjectType("", "II"),
                        new MinorRealmObjectType("", "III"),
                        new MinorRealmObjectType("", "IV"),
                        new MinorRealmObjectType("", "V"),
                        new MinorRealmObjectType("", "VI"),
                        new MinorRealmObjectType("", "VII"),
                        new MinorRealmObjectType("", "VIII")
                }),
                new MajorRealmObjectType("Half-Step Saint", 1, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("", "")
                }),
                new MajorRealmObjectType("Saint", 3, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("1st Order", ""),
                        new MinorRealmObjectType("2nd Order", ""),
                        new MinorRealmObjectType("3rd Order", ""),
                }),
                new MajorRealmObjectType("Return To Origin", 3, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("1st Order", ""),
                        new MinorRealmObjectType("2nd Order", ""),
                        new MinorRealmObjectType("3rd Order", ""),
                }),
                new MajorRealmObjectType("Immortal", 1, new MinorRealmObjectType[]{
                        new MinorRealmObjectType("", "")
                })
        };
    }
}
