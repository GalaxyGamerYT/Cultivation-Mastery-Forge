package galaxygameryt.cultivation_mastery.item;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.item.custom.SpiritStoneItem;
import galaxygameryt.cultivation_mastery.item.custom.SpiritualMirrorItem;
import galaxygameryt.cultivation_mastery.item.custom.containers.SpaceRingItem;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.*;
import galaxygameryt.cultivation_mastery.util.Logger;
import galaxygameryt.cultivation_mastery.util.enums.RuneStoneAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CultivationMastery.MOD_ID);

    public static final RegistryObject<Item> SPIRITUAL_MIRROR = ITEMS.register("spiritual_mirror",
            () -> new SpiritualMirrorItem(new Item.Properties()));

    public static final RegistryObject<Item> SPACE_RING = ITEMS.register("space_ring",
            () -> new SpaceRingItem(new Item.Properties(), "menu.title.cultivation_mastery.space_ring"));

//    public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack",
//            () -> new ContainerItem(new Item.Properties()));


    public static final RegistryObject<Item> LOW_SPIRIT_STONE = ITEMS.register("low_spirit_stone",
            () -> new SpiritStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> MEDIUM_SPIRIT_STONE = ITEMS.register("medium_spirit_stone",
            () -> new SpiritStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> HIGH_SPIRIT_STONE = ITEMS.register("high_spirit_stone",
            () -> new SpiritStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> IMMORTAL_SPIRIT_STONE = ITEMS.register("immortal_spirit_stone",
            () -> new SpiritStoneItem(new Item.Properties()));

    public static final RegistryObject<Item> BLANK_RUNE_STONE = ITEMS.register("blank_rune_stone",
            () -> new RuneStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> BASIC_RUNE_STONE = ITEMS.register("basic_rune_stone",
            () -> new BasicRuneStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> LOW_RUNE_STONE = ITEMS.register("low_rune_stone",
            () -> new LowRuneStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> MEDIUM_RUNE_STONE = ITEMS.register("medium_rune_stone",
            () -> new MediumRuneStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> HIGH_RUNE_STONE = ITEMS.register("high_rune_stone",
            () -> new HighRuneStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> IMMORTAL_RUNE_STONE = ITEMS.register("immortal_rune_stone",
            () -> new ImmortalRuneStoneItem(new Item.Properties()));

    public static final RegistryObject<Item> SPIRITUAL_IRON_INGOT = ITEMS.register("spiritual_iron_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> YIN_YANG = ITEMS.register("yin_yang",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARRAY = ITEMS.register("array",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        Logger.info("Registering Items");

        ITEMS.register(eventBus);
    }
}
