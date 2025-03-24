package galaxygameryt.cultivation_mastery.capabilites.meditating;

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

public class PlayerMeditatingProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerMeditating> PLAYER_MEDITATING = CapabilityManager.get(new CapabilityToken<PlayerMeditating>() { });

    private PlayerMeditating meditating = null;
    private final LazyOptional<PlayerMeditating> optional = LazyOptional.of(this::createPlayerMeditating);

    private PlayerMeditating createPlayerMeditating() {
        if(this.meditating == null) {
            this.meditating = new PlayerMeditating();
        }
        return this.meditating;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_MEDITATING) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerMeditating().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerMeditating().loadNBTData(nbt);
    }
}