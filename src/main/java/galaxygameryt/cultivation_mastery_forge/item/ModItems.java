package galaxygameryt.cultivation_mastery_forge.item;

import galaxygameryt.cultivation_mastery_forge.CultivationMasteryForge;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CultivationMasteryForge.MOD_ID);


    public static final RegistryObject<Item> SPIRITUAL_MIRROR = ITEMS.register("spiritual_mirror",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
