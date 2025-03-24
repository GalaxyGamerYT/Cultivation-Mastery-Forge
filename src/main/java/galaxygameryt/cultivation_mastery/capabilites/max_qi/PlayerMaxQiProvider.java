package galaxygameryt.cultivation_mastery.capabilites.max_qi;

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

public class PlayerMaxQiProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerMaxQi> PLAYER_MAX_QI = CapabilityManager.get(new CapabilityToken<PlayerMaxQi>() { });

    private PlayerMaxQi max_qi = null;
    private final LazyOptional<PlayerMaxQi> optional = LazyOptional.of(this::createPlayerMaxQi);

    private PlayerMaxQi createPlayerMaxQi() {
        if(this.max_qi == null) {
            this.max_qi = new PlayerMaxQi();
        }
        return this.max_qi;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_MAX_QI) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerMaxQi().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerMaxQi().loadNBTData(nbt);
    }
}