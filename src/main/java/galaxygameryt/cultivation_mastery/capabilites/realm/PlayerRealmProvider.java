package galaxygameryt.cultivation_mastery.capabilites.realm;

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

public class PlayerRealmProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerRealm> PLAYER_REALM = CapabilityManager.get(new CapabilityToken<PlayerRealm>() { });

    private PlayerRealm realm = null;
    private final LazyOptional<PlayerRealm> optional = LazyOptional.of(this::createPlayerRealm);

    private PlayerRealm createPlayerRealm() {
        if(this.realm == null) {
            this.realm = new PlayerRealm();
        }
        return this.realm;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_REALM) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerRealm().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerRealm().loadNBTData(nbt);
    }
}