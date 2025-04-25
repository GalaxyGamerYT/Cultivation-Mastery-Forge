package galaxygameryt.cultivation_mastery.entity.custom;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SittingEntity extends Entity {
    public SittingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void removePassenger(Entity pPassenger) {
        super.removePassenger(pPassenger);
        this.kill();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.getControllingPassenger() instanceof Player player) {
            this.level().addParticle(ParticleTypes.END_ROD,
                    player.getX() + (random.nextDouble() - 0.5) * 0.5,
                    player.getY() + 1.2 + (random.nextDouble() * 0.2),
                    player.getZ() + (random.nextDouble() - 0.5) * 0.5,
                    0, 0.01, 0);
        }
    }

}
