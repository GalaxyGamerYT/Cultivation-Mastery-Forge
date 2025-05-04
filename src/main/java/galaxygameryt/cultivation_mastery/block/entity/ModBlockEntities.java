package galaxygameryt.cultivation_mastery.block.entity;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.block.entity.custom.FormationCoreBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CultivationMastery.MOD_ID);

    public static final RegistryObject<BlockEntityType<FormationCoreBlockEntity>> FORMATION_CORE_BE =
            BLOCK_ENTITIES.register("formation_core_be", () ->
                    BlockEntityType.Builder.of(FormationCoreBlockEntity::new,
                            ModBlocks.FORMATION_CORE.get()).build(null));

    public static void register(IEventBus event) {
        BLOCK_ENTITIES.register(event);
    }
}
