package galaxygameryt.cultivation_mastery.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_CULTIVATION = "key.category.cultivation_mastery.cultivation";
    public static final String KEY_MEDITATE = "key.cultivation_mastery.meditate";

    public static final KeyMapping MEDITATE_KEY = new KeyMapping(KEY_MEDITATE, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, KEY_CATEGORY_CULTIVATION);


}
