package galaxygameryt.cultivation_mastery.event.handlers;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.entity.custom.FormationCoreBlockEntity;
import galaxygameryt.cultivation_mastery.effect.ModEffects;
import galaxygameryt.cultivation_mastery.util.SavedData.FormationCoreSavedData;
import galaxygameryt.cultivation_mastery.util.data.FormationEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID)
public class FormationEffectHandler {
    public static final Set<BlockPos> LOADED_FORMATION_CORES = ConcurrentHashMap.newKeySet();

    private static final int BASE_RADIUS = 10;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;

        Player player = event.player;
        Level level = player.level();
        BlockPos playerPos = player.blockPosition();
        FormationCoreSavedData data = FormationCoreSavedData.get(level);

        for (BlockPos pos : LOADED_FORMATION_CORES) {
            if (level.getBlockEntity(pos) instanceof FormationCoreBlockEntity blockEntity && blockEntity.isActive()) {
                if (pos.closerThan(playerPos, BASE_RADIUS * blockEntity.getLevelInt())) {
                    FormationEffectData effectData = data.getEffectData(pos);
                    if (effectData != null && !effectData.effects.isEmpty()) {
                        for (MobEffectInstance instance : effectData.effects) {
                            MobEffectInstance existing = player.getEffect(instance.getEffect());
                            if (existing == null || existing.getDuration() < Math.max(instance.getDuration(), 200)) {
                                if (existing != null) {
                                    player.removeEffect(existing.getEffect());
                                }
                                player.addEffect(instance);
                            }
                        }

                    }
                }
            }
        }
    }
}
