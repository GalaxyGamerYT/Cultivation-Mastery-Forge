package galaxygameryt.cultivation_mastery.effect;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.effect.custom.BreakthroughEffect;
import galaxygameryt.cultivation_mastery.effect.custom.EnvironmentQiAbsorptionEffect;
import galaxygameryt.cultivation_mastery.effect.custom.QiAbsorptionEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CultivationMastery.MOD_ID);

    public static final RegistryObject<MobEffect> BREAKTHROUGH = MOB_EFFECTS.register("breakthrough",
            () -> new BreakthroughEffect(MobEffectCategory.BENEFICIAL, ChatFormatting.GOLD.getColor()));
    public static final RegistryObject<MobEffect> QI_ABSORPTION = MOB_EFFECTS.register("qi_absorption",
            () -> new QiAbsorptionEffect(MobEffectCategory.BENEFICIAL, ChatFormatting.AQUA.getColor()));
    public static final RegistryObject<MobEffect> ENVIRONMENT_QI_ABSORPTION = MOB_EFFECTS.register("environment_qi_absorption",
            () -> new EnvironmentQiAbsorptionEffect(MobEffectCategory.BENEFICIAL, ChatFormatting.GREEN.getColor()));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
