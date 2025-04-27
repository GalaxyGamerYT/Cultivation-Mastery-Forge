package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.item.custom.SpiritStoneItem;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.BasicRuneStoneItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CultivationMastery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.SPIRITUAL_MIRROR.get());
        simpleItem(ModItems.SPACE_RING.get());

        simpleItem(ModItems.LOW_SPIRIT_STONE.get());
        simpleItem(ModItems.MEDIUM_SPIRIT_STONE.get());
        simpleItem(ModItems.HIGH_SPIRIT_STONE.get());
        simpleItem(ModItems.IMMORTAL_SPIRIT_STONE.get());

        simpleItem(ModItems.SPIRITUAL_IRON_INGOT.get());

//        simpleItem(ModItems.BACKPACK.get());

        simpleItem(ModItems.YIN_YANG.get());

        simpleItem(ModItems.BLANK_RUNE_STONE.get());
        simpleItem(ModItems.BASIC_RUNE_STONE.get());
        registerRuneStones(ModItems.BASIC_RUNE_STONES, "basic_rune_stone");
        simpleItem(ModItems.LOW_RUNE_STONE.get());
        registerRuneStones(ModItems.LOW_RUNE_STONES, "low_rune_stone");
        simpleItem(ModItems.MEDIUM_RUNE_STONE.get());
        registerRuneStones(ModItems.MEDIUM_RUNE_STONES, "medium_rune_stone");
        simpleItem(ModItems.HIGH_RUNE_STONE.get());
        registerRuneStones(ModItems.HIGH_RUNE_STONES, "high_rune_stone");
    }

    private void registerRuneStones(List<RegistryObject<Item>> items, String texture) {
        for (RegistryObject<Item> itemEntry : items.stream().toList()) {
            simpleRuneStone(itemEntry.get(), texture);
        }
    }

    private void simpleRuneStone(Item item, String texture) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
        withExistingParent(itemId.getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "item/"+texture));
    }

    private void simpleItem(Item item) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
        withExistingParent(itemId.getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "item/" + itemId.getPath()));
    }
}
