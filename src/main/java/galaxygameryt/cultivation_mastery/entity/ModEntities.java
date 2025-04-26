package galaxygameryt.cultivation_mastery.entity;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.entity.custom.MeditationEntity;
import galaxygameryt.cultivation_mastery.entity.custom.SitableEntity;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CultivationMastery.MOD_ID);

//    public static final RegistryObject<EntityType<SitableEntity>> SITABLE =
//            ENTITY_TYPES.register("sitable_entity", () -> EntityType.Builder.of(SitableEntity::new, MobCategory.MISC)
//                    .sized(0.5f, 0.4f).build("sitting_entity"));

    public static final RegistryObject<EntityType<MeditationEntity>> MEDITATION = registerEntity("meditation_entity",
            MeditationEntity::new, MobCategory.MISC, 0.5f, 0.4f);



    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> entity,
                                                                                   MobCategory category, float width, float height) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(entity, category).sized(width, height).build(name));
    }

    public static void register(IEventBus eventBus) {
        Logger.info("Registering Entities");

        ENTITY_TYPES.register(eventBus);
    }
}
