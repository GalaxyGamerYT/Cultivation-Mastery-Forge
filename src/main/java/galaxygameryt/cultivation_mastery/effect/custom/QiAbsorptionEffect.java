package galaxygameryt.cultivation_mastery.effect.custom;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.data.player.ServerPlayerData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class QiAbsorptionEffect extends MobEffect {
    public QiAbsorptionEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int amplifier) {
        if (livingEntity instanceof ServerPlayer entity) {
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(entity.getUUID());
            playerData.addBaseQiMulti(amplifier);
        }

        super.addAttributeModifiers(livingEntity, attributeMap, amplifier);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int amplifier) {
        if (livingEntity instanceof ServerPlayer entity) {
            ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(entity.getUUID());
            playerData.subBaseQiMulti(amplifier);
        }

        super.removeAttributeModifiers(livingEntity, attributeMap, amplifier);
    }
}
