package galaxygameryt.cultivation_mastery.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.event.IModBusEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    // Key Categories
    public static final String KEY_CATEGORY_CULTIVATION = "key.category.cultivation_mastery.cultivation";
    public static final String KEY_CATEGORY_DEBUG = "key.category.cultivation_mastery.debug";

    // Keys
    public static final String KEY_MEDITATE = "key.cultivation_mastery.meditate";
    public static final String KEY_BREAKTHROUGH = "key.cultivation_mastery.breakthrough";
//    public static final String KEY_CULTIVATION_GUI = "key.cultivation_mastery.cultivation_gui";

    // Key Mappings

    //  CULTIVATION
    public static final KeyMapping MEDITATE_KEY = new KeyMapping(KEY_MEDITATE, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, KEY_CATEGORY_CULTIVATION);
    public static final KeyMapping BREAKTHROUGH_KEY = new KeyMapping(KEY_BREAKTHROUGH, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY_CULTIVATION);

    // DEBUG
//    public static final KeyMapping CULTIVATION_GUI_KEY = new KeyMapping(KEY_CULTIVATION_GUI, KeyConflictContext.IN_GAME,
//            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, KEY_CATEGORY_DEBUG);


    public static void register(RegisterKeyMappingsEvent event) {
        Logger.info("Registering Key Bindings");

        event.register(KeyBinding.MEDITATE_KEY);
        event.register(KeyBinding.BREAKTHROUGH_KEY);
//            event.register(KeyBinding.CULTIVATION_GUI_KEY);
    }
}
