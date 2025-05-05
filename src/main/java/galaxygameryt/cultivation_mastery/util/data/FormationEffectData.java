package galaxygameryt.cultivation_mastery.util.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class FormationEffectData {
    public final List<MobEffectInstance> effects;
    public final int levelInt;

    public FormationEffectData(List<MobEffectInstance> effects) {
        this(effects, 0);
    }

    public FormationEffectData(List<MobEffectInstance> effects, int levelInt) {
        this.effects = effects;
        this.levelInt = levelInt;
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();

        for (MobEffectInstance effect : effects) {
            CompoundTag effectTag = new CompoundTag();
            effectTag.putString("effect", ForgeRegistries.MOB_EFFECTS.getKey(effect.getEffect()).toString());
            effectTag.putInt("duration", effect.getDuration());
            effectTag.putInt("amplifier", effect.getAmplifier());
            effectTag.putBoolean("visible", effect.isVisible());
            effectTag.putBoolean("showIcon", effect.showIcon());
            list.add(effectTag);
        }

        tag.put("formationEffects", list);
        tag.putInt("levelInt", levelInt);
        return tag;
    }

    public static FormationEffectData deserializeNBT(CompoundTag tag) {
        List<MobEffectInstance> effects = new ArrayList<>();
        ListTag list = tag.getList("formationEffects", ListTag.TAG_COMPOUND);

        for (Tag t : list) {
            CompoundTag effectTag = (CompoundTag) t;
            ResourceLocation id = ResourceLocation.parse(effectTag.getString("effect"));
            int duration = effectTag.getInt("duration");
            int amplifier = effectTag.getInt("amplifier");
            boolean visible = effectTag.getBoolean("visible");
            boolean showIcon = effectTag.getBoolean("showIcon");

            if (ForgeRegistries.MOB_EFFECTS.containsKey(id)) {
                MobEffectInstance effect = new MobEffectInstance(
                        ForgeRegistries.MOB_EFFECTS.getValue(id), duration, amplifier, true, visible, showIcon
                );
                effects.add(effect);
            }
        }

        int levelInt = tag.getInt("levelInt");

        return new FormationEffectData(effects, levelInt);
    }
}
