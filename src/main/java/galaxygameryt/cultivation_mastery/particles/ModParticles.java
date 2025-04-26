package galaxygameryt.cultivation_mastery.particles;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, CultivationMastery.MOD_ID);

    public static final RegistryObject<SimpleParticleType> QI_PARTICLE =
            PARTICLES.register("qi_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus event) {
        PARTICLES.register(event);
    }
}
