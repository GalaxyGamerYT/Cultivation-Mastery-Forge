package galaxygameryt.cultivation_mastery.compat.patchouli.processors;

import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class RuneStoneProcessor implements IComponentProcessor {

    private String attribute;

    @Override
    public void setup(Level level, IVariableProvider variables) {
        attribute = variables.get("attribute").asString();
    }

    @Override
    public IVariable process(Level level, String key) {
        if (key.startsWith("item")) {

        }
        return null;
    }
}
