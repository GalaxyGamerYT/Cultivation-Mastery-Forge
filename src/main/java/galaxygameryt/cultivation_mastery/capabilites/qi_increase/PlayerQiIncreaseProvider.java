package galaxygameryt.cultivation_mastery.capabilites.qi_increase;

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

public class PlayerQiIncreaseProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerQiIncrease> PLAYER_QI_INCREASE = CapabilityManager.get(new CapabilityToken<PlayerQiIncrease>() { });

    private PlayerQiIncrease qi_increase = null;
    private final LazyOptional<PlayerQiIncrease> optional = LazyOptional.of(this::createPlayerQiIncrease);

    private PlayerQiIncrease createPlayerQiIncrease() {
        if(this.qi_increase == null) {
            this.qi_increase = new PlayerQiIncrease();
        }
        return this.qi_increase;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_QI_INCREASE) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerQiIncrease().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerQiIncrease().loadNBTData(nbt);
    }
}