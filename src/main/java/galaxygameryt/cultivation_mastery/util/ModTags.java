package galaxygameryt.cultivation_mastery.util;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> LOW_SPIRIT_STONE_ORES = tag("low_spirit_stone_ores");
        public static final TagKey<Block> MEDIUM_SPIRIT_STONE_ORES = tag("medium_spirit_stone_ores");
        public static final TagKey<Block> HIGH_SPIRIT_STONE_ORES = tag("high_spirit_stone_ores");

        public static final TagKey<Block> TRAINING_POSTS = tag("training_posts");
        public static final TagKey<Block> WOOD_TRAINING_POSTS = tag("wood_training_posts");

        public static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> LOW_SPIRIT_STONE_ORES = tag("low_spirit_stone_ores");
        public static final TagKey<Item> MEDIUM_SPIRIT_STONE_ORES = tag("medium_spirit_stone_ores");
        public static final TagKey<Item> HIGH_SPIRIT_STONE_ORES = tag("high_spirit_stone_ores");

        public static final TagKey<Item> SPIRITUAL_STONES = tag("spiritual_stones");

        public static final TagKey<Item> WOOD_TRAINING_POSTS = tag("wood_training_posts");
        public static final TagKey<Item> TRAINING_POSTS = tag("wood_training_posts");

        public static final TagKey<Item> USABLE_RUNE_STONES = tag("usable_rune_stones");
        public static final TagKey<Item> ATTRIBUTE_RUNE_STONES = tag("attribute_rune_stones");
        public static final TagKey<Item> BASIC_RUNE_STONES = tag("basic_rune_stones");
        public static final TagKey<Item> LOW_RUNE_STONES = tag("low_rune_stones");
        public static final TagKey<Item> MEDIUM_RUNE_STONES = tag("medium_rune_stones");
        public static final TagKey<Item> HIGH_RUNE_STONES = tag("high_rune_stones");
        public static final TagKey<Item> IMMORTAL_RUNE_STONES = tag("immortal_rune_stones");

        public static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> MEDIUM_SPIRIT_BIOMES = tag("medium_spirit_biomes");
        public static final TagKey<Biome> HIGH_SPIRIT_BIOMES = tag("high_spirit_biomes");

        private static TagKey<Biome> tag(String Name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, Name));
        }
    }
}
