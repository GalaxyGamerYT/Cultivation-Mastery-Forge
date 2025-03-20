package galaxygameryt.cultivation_mastery.datagen.loot;

import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
//        this.dropSelf(Modblocks.SAPHIRE_BLOCK).get();

        this.add(ModBlocks.LOW_SPIRIT_STONE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.LOW_SPIRIT_STONE_ORE.get(), ModItems.LOW_SPIRIT_STONE.get()));
        this.add(ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_LOW_SPIRIT_STONE_ORE.get(), ModItems.LOW_SPIRIT_STONE.get()));
        this.add(ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.MEDIUM_SPIRIT_STONE_ORE.get(), ModItems.MEDIUM_SPIRIT_STONE.get()));
        this.add(ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_MEDIUM_SPIRIT_STONE_ORE.get(), ModItems.MEDIUM_SPIRIT_STONE.get()));
        this.add(ModBlocks.HIGH_SPIRIT_STONE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.HIGH_SPIRIT_STONE_ORE.get(), ModItems.HIGH_SPIRIT_STONE.get()));
        this.add(ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_HIGH_SPIRIT_STONE_ORE.get(), ModItems.HIGH_SPIRIT_STONE.get()));
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(2.0F, 5.0F)
                                ))
                                .apply(ApplyBonusCount.addOreBonusCount(
                                        Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
