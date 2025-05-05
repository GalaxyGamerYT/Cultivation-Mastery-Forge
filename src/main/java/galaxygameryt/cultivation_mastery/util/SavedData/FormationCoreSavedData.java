package galaxygameryt.cultivation_mastery.util.SavedData;

import galaxygameryt.cultivation_mastery.util.data.FormationEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormationCoreSavedData extends SavedData {
    private static final String DATA_NAME = "formation_core_data";
    private final Map<BlockPos, FormationEffectData> formationCores = new HashMap<>();

    public FormationCoreSavedData() {}

    public FormationEffectData getEffectData(BlockPos pos) {
        return formationCores.get(pos);
    }

    public void addFormation(BlockPos pos, FormationEffectData effectData) {
        formationCores.put(pos, effectData);
        setDirty();
    }

    public void removeFormation(BlockPos pos) {
        formationCores.remove(pos);
        setDirty();
    }

    public void addEffect(BlockPos pos, MobEffectInstance effect) {
        this.formationCores.computeIfAbsent(pos, p -> new FormationEffectData(new ArrayList<>())).effects.add(effect);
        setDirty();
    }

    public void removeEffect(BlockPos pos, MobEffectInstance effect) {
        FormationEffectData effectData = formationCores.get(pos);
        effectData.effects.remove(effect);
        formationCores.put(pos, effectData);
    }

    public static FormationCoreSavedData load(CompoundTag tag) {
        FormationCoreSavedData data = new FormationCoreSavedData();
        ListTag listTag = tag.getList("FormationCores", Tag.TAG_COMPOUND);
        for (Tag element : listTag) {
            CompoundTag coreTag = (CompoundTag) element;
            BlockPos pos = BlockPos.of(coreTag.getLong("pos"));
            FormationEffectData effectData = FormationEffectData.deserializeNBT(coreTag.getCompound("effect_data"));
            data.formationCores.put(pos, effectData);
        }
        return data;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        ListTag listTag = new ListTag();
        for (Map.Entry<BlockPos, FormationEffectData> entry : formationCores.entrySet()) {
            CompoundTag coreTag = new CompoundTag();
            coreTag.putLong("pos", entry.getKey().asLong());
            coreTag.put("effect_data", entry.getValue().serializeNBT());
            listTag.add(coreTag);
        }
        tag.put("FormationCores", listTag);
        return tag;
    }

    public static FormationCoreSavedData get(Level level) {
        return level.getServer().overworld().getDataStorage()
                .computeIfAbsent(FormationCoreSavedData::load, FormationCoreSavedData::new, DATA_NAME);
    }

    public BlockPos[] getAllFormationCores() {
        return formationCores.keySet().toArray(new BlockPos[0]);
    }
}
