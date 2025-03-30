package galaxygameryt.cultivation_mastery.command;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CultivationCommandsRegister {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CultivationBaseCommand.register(event.getDispatcher());
    }
}
