package galaxygameryt.cultivation_mastery.util.data.capability;

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

public class PlayerCapabilityProvider  implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerCapability> PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerCapability>() { });

    private PlayerCapability capability = null;
    private final LazyOptional<PlayerCapability> optional = LazyOptional.of(this::createPlayerCapability);

    private PlayerCapability createPlayerCapability() {
        if(this.capability == null) {
            this.capability = new PlayerCapability();
        }
        return this.capability;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_CAPABILITY) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerCapability().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCapability().loadNBTData(nbt);
    }
}
