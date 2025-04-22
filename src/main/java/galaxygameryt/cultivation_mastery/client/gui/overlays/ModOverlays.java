package galaxygameryt.cultivation_mastery.client.gui.overlays;

import galaxygameryt.cultivation_mastery.client.gui.overlays.custom.CultivationHudOverlay;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;

public class ModOverlays {
    public static final CultivationHudOverlay CULTIVATION_HUD_OVERLAY = new CultivationHudOverlay();

    public static void register(RegisterGuiOverlaysEvent event) {
        Logger.info("Registering Gui Overlays");

        event.registerAboveAll("cultivation", CULTIVATION_HUD_OVERLAY);
    }
}
