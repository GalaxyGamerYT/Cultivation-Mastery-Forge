package galaxygameryt.cultivation_mastery.capabilites.cultivation;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerCultivationProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerCultivation> PLAYER_CULTIVATION = CapabilityManager.get(new CapabilityToken<PlayerCultivation>() { });

    private PlayerCultivation cultivation = null;
    private final LazyOptional<PlayerCultivation> optional = LazyOptional.of(this::createPlayerCultivation);

    private PlayerCultivation createPlayerCultivation() {
        if(this.cultivation == null) {
            this.cultivation = new PlayerCultivation();
        }
        return this.cultivation;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_CULTIVATION) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerCultivation().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCultivation().loadNBTData(nbt);
    }
}