package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CultivationMastery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.SPIRITUAL_MIRROR.get());

        simpleItem(ModItems.LOW_SPIRIT_STONE.get());
        simpleItem(ModItems.MEDIUM_SPIRIT_STONE.get());
        simpleItem(ModItems.HIGH_SPIRIT_STONE.get());
    }

    private ItemModelBuilder simpleItem(Item item) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
        return withExistingParent(itemId.getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "item/" + itemId.getPath()));
    }
}
