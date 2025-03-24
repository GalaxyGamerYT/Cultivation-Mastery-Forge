package galaxygameryt.cultivation_mastery.capabilites.qi;

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

public class PlayerQiProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerQi> PLAYER_QI = CapabilityManager.get(new CapabilityToken<PlayerQi>() { });

    private PlayerQi qi = null;
    private final LazyOptional<PlayerQi> optional = LazyOptional.of(this::createPlayerQi);

    private PlayerQi createPlayerQi() {
        if(this.qi == null) {
            this.qi = new PlayerQi();
        }
        return this.qi;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_QI) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerQi().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerQi().loadNBTData(nbt);
    }
}