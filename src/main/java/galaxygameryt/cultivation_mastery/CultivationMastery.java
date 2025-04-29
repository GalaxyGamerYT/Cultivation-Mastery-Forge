package galaxygameryt.cultivation_mastery;

import com.mojang.logging.LogUtils;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.effect.ModEffects;
import galaxygameryt.cultivation_mastery.entity.ModEntities;
import galaxygameryt.cultivation_mastery.item.ModCreativeModeTabs;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuTypes;
import galaxygameryt.cultivation_mastery.particles.ModParticles;
import galaxygameryt.cultivation_mastery.recipe.ModRecipes;
import galaxygameryt.cultivation_mastery.util.data.player.ClientPlayerData;
import galaxygameryt.cultivation_mastery.util.data.player.ServerPlayerData;
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

    public CultivationMastery(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        ModRecipes.register(modEventBus);

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
}
