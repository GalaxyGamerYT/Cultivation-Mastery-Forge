package galaxygameryt.cultivation_mastery.effect.custom;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.player.ServerPlayerData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.jetbrains.annotations.NotNull;

public class QiAbsorptionEffect extends MobEffect {
    public QiAbsorptionEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int amplifier) {
        if (livingEntity instanceof ServerPlayer entity) {
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(entity.getUUID());
            playerData.addBaseQiMulti(amplifier);
        }

        super.addAttributeModifiers(livingEntity, attributeMap, amplifier);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int amplifier) {
        if (livingEntity instanceof ServerPlayer entity) {
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(entity.getUUID());
            playerData.subBaseQiMulti(amplifier);
        }

        super.removeAttributeModifiers(livingEntity, attributeMap, amplifier);
    }
}
