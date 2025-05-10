package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.item.ModItems;
import galaxygameryt.cultivation_mastery.item.custom.SpiritStoneItem;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.BasicRuneStoneItem;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.RuneStoneItem;
import galaxygameryt.cultivation_mastery.util.enums.RuneStoneAttributes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        simpleItem(ModItems.ARRAY.get());

        simpleItem(ModItems.BLANK_RUNE_STONE.get());
        simpleItem(ModItems.BASIC_RUNE_STONE.get());
        for (RuneStoneAttributes.Basic attr : RuneStoneAttributes.Basic.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.BASIC_RUNE_STONE.get(), attr.name());
            simpleRuneStone(stack.getItem(), "basic_rune_stone");
        }
        simpleItem(ModItems.LOW_RUNE_STONE.get());
        for (RuneStoneAttributes.Low attr : RuneStoneAttributes.Low.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.LOW_RUNE_STONE.get(), attr.name());
            simpleRuneStone(stack.getItem(), "low_rune_stone");
        }
        simpleItem(ModItems.MEDIUM_RUNE_STONE.get());
        for (RuneStoneAttributes.Medium attr : RuneStoneAttributes.Medium.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.MEDIUM_RUNE_STONE.get(), attr.name());
            simpleRuneStone(stack.getItem(), "medium_rune_stone");
        }
        simpleItem(ModItems.HIGH_RUNE_STONE.get());
        for (RuneStoneAttributes.High attr : RuneStoneAttributes.High.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.HIGH_RUNE_STONE.get(), attr.name());
            simpleRuneStone(stack.getItem(), "high_rune_stone");
        }
        simpleItem(ModItems.IMMORTAL_RUNE_STONE.get());
        for (RuneStoneAttributes.Immortal attr : RuneStoneAttributes.Immortal.values()) {
            ItemStack stack = RuneStoneItem.createWithAttribute((RuneStoneItem) ModItems.IMMORTAL_RUNE_STONE.get(), attr.name());
            simpleRuneStone(stack.getItem(), "immortal_rune_stone");
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
