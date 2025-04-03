package galaxygameryt.cultivation_mastery.item;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.item.custom.BackpackItem;
import galaxygameryt.cultivation_mastery.item.custom.SpiritualMirrorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CultivationMastery.MOD_ID);


    public static final RegistryObject<Item> SPIRITUAL_MIRROR = ITEMS.register("spiritual_mirror",
            () -> new SpiritualMirrorItem(new Item.Properties()));

//    public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack",
//            () -> new BackpackItem(new Item.Properties()));


    public static final RegistryObject<Item> LOW_SPIRIT_STONE = ITEMS.register("low_spirit_stone",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEDIUM_SPIRIT_STONE = ITEMS.register("medium_spirit_stone",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HIGH_SPIRIT_STONE = ITEMS.register("high_spirit_stone",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPIRITUAL_IRON_INGOT = ITEMS.register("spiritual_iron_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> YIN_YANG = ITEMS.register("yin_yang",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
