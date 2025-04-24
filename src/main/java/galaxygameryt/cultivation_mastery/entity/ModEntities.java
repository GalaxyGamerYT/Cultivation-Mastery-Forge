package galaxygameryt.cultivation_mastery.entity;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.entity.custom.SittingEntity;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CultivationMastery.MOD_ID);

    public static final RegistryObject<EntityType<SittingEntity>> SITTING =
            ENTITY_TYPES.register("sitting_entity", () -> EntityType.Builder.of(SittingEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.4f).build("sitting_entity"));

    public static void register(IEventBus eventBus) {
        Logger.info("Registering Entities");

        ENTITY_TYPES.register(eventBus);
    }
}
