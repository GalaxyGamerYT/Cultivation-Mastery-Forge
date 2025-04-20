package galaxygameryt.cultivation_mastery.compat.jei;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEICultivationMasteryPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_UID = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }
}
