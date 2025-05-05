package galaxygameryt.cultivation_mastery.util.helpers;

import galaxygameryt.cultivation_mastery.effect.ModEffects;
import galaxygameryt.cultivation_mastery.item.custom.rune_stones.*;
import galaxygameryt.cultivation_mastery.util.enums.RuneStoneAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RuneEffectResolver {

    public static List<MobEffectInstance> resolveEffectsFromRunes(List<ItemStack> runes, ItemStack spiritStone, int levelInt) {
        List<MobEffectInstance> effectInstances = new ArrayList<>();

        ItemStack slot1 = runes.get(0);

        int duration = 600;
        if (spiritStone.isEmpty()) return effectInstances;

        if (slot1.isEmpty()) return effectInstances;

        List<EffectObject> effects = new ArrayList<>();
        Item basicRune = slot1.getItem();

        List<EffectObject> basicEffects = getBasicEffects(basicRune, levelInt);
        if (!basicEffects.isEmpty()) effects.addAll(basicEffects);

        for (ItemStack runeStack : runes) {
            if (runeStack.getItem() instanceof LowRuneStoneItem runeItem) {
                // Low
                List<EffectObject> lowEffects = getLowEffects(runeItem, basicRune, levelInt);
                if (!lowEffects.isEmpty()) effects.addAll(lowEffects);
            } else if (runeStack.getItem() instanceof MediumRuneStoneItem runeItem) {
                // Medium
                List<EffectObject> mediumEffects = getMediumEffects(runeItem, basicRune, levelInt);
                if (!mediumEffects.isEmpty()) effects.addAll(mediumEffects);
            } else if (runeStack.getItem() instanceof HighRuneStoneItem runeItem) {
                // High
                List<EffectObject> highEffects = getHighEffects(runeItem, basicRune, levelInt);
                if (!highEffects.isEmpty()) effects.addAll(highEffects);
            } else if (runeStack.getItem() instanceof ImmortalRuneStoneItem runeItem) {
                // Immortal
                List<EffectObject> immortalEffects = getImmortalEffects(runeItem, basicRune, levelInt);
                if (!immortalEffects.isEmpty()) effects.addAll(immortalEffects);
            }
        }

        for (EffectObject object : effects) {
            effectInstances.add(newEffectInstance(object.effect, duration, object.amplifier));
        }

        return effectInstances;
    }

    private static boolean isAttribute(Item rune, String attr) {
        RuneStoneItem runeItem = (RuneStoneItem) rune;
        return runeItem.getAttribute().equalsIgnoreCase(attr);
    }
    private static boolean isBasicAttr(Item rune, RuneStoneAttributes.Basic attr) {
        return isAttribute(rune, attr.name());
    }
    private static boolean isLowAttr(Item rune, RuneStoneAttributes.Low attr) {
        return isAttribute(rune, attr.name());
    }
    private static boolean isMediumAttr(Item rune, RuneStoneAttributes.Medium attr) {
        return isAttribute(rune, attr.name());
    }
    private static boolean isHighAttr(Item rune, RuneStoneAttributes.High attr) {
        return isAttribute(rune, attr.name());
    }
    private static boolean isImmortalAttr(Item rune, RuneStoneAttributes.Immortal attr) {
        return isAttribute(rune, attr.name());
    }

    private static MobEffectInstance newEffectInstance(MobEffect effect, int duration, int amplifier) {
        return new MobEffectInstance(effect, duration, amplifier, true, false, true);
    }
    private static EffectObject newEffectObject(MobEffect effect, int levelInt) {
        return new EffectObject(effect, levelInt);
    }
    private static EffectObject newEffectObject(MobEffect effect, int levelInt, int amplifier) {
        if (levelInt - 1 == 0) amplifier = (int) Math.floor((double) amplifier /2);
        return new EffectObject(effect, levelInt * amplifier);
    }

    private static List<EffectObject> getBasicEffects(Item basicRune, int levelInt) {
        List<EffectObject> effects = new ArrayList<>();

        if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.ENERGY_GATHERING)) {
            effects.add(newEffectObject(ModEffects.QI_ABSORPTION.get(), levelInt));
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.HEALING)) {
            effects.add(newEffectObject(MobEffects.REGENERATION, levelInt, 2));
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.PROTECTION)) {
            effects.add(newEffectObject(MobEffects.DAMAGE_RESISTANCE, levelInt));
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.OFFENSE)) {
            effects.add(newEffectObject(MobEffects.DAMAGE_BOOST, levelInt));
        }

        return effects;
    }
    private static List<EffectObject> getLowEffects(Item runeItem, Item basicRune, int levelInt) {
        List<EffectObject> effects = new ArrayList<>();

        if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.ENERGY_GATHERING)) {
            // Energy Gathering
            if (isLowAttr(runeItem, RuneStoneAttributes.Low.FIRE)) {
                // Fire
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WATER)) {
                // Water
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.EARTH)) {
                // Earth
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.METAL)) {
                // Metal
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WOOD)) {
                // Wood
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.HEALING)) {
            // Healing
            if (isLowAttr(runeItem, RuneStoneAttributes.Low.FIRE)) {
                // Fire
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WOOD)) {
                // Water
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.EARTH)) {
                // Earth
                effects.add(newEffectObject(MobEffects.ABSORPTION, levelInt, 2));
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.METAL)) {
                // Metal
            }
            else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WOOD)) {
                // Wood
                effects.add(newEffectObject(MobEffects.HEALTH_BOOST, levelInt, 2));
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.PROTECTION)) {
            // Protection
            if (isLowAttr(runeItem, RuneStoneAttributes.Low.FIRE)) {
                // Fire
                effects.add(newEffectObject(MobEffects.FIRE_RESISTANCE, levelInt, 2));
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WATER)) {
                // Water
                effects.add(newEffectObject(MobEffects.WATER_BREATHING, levelInt, 4));
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.EARTH)) {
                // Earth
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.METAL)) {
                // Metal
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WOOD)) {
                // Wood
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.OFFENSE)) {
            // Offense
            if (isLowAttr(runeItem, RuneStoneAttributes.Low.FIRE)) {
                // Fire
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WATER)) {
                // Water
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.EARTH)) {
                // Earth
                effects.add(newEffectObject(MobEffects.DAMAGE_BOOST, levelInt));
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.METAL)) {
                // Metal
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WOOD)) {
                // Wood
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.DEFENSE)) {
            if (isLowAttr(runeItem, RuneStoneAttributes.Low.FIRE)) {
                // Fire
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WATER)) {
                // Water
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.EARTH)) {
                // Earth
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.METAL)) {
                // Metal
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WOOD)) {
                // Wood
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.UTILITY)) {
            // Utility
            if (isLowAttr(runeItem, RuneStoneAttributes.Low.FIRE)) {
                // Fire
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WATER)) {
                // Water
                effects.add(newEffectObject(MobEffects.DOLPHINS_GRACE, levelInt));
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.EARTH)) {
                // Earth
                effects.add(newEffectObject(MobEffects.MOVEMENT_SPEED, levelInt));
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.METAL)) {
                // Metal
                effects.add(newEffectObject(MobEffects.DIG_SPEED, levelInt));
            } else if (isLowAttr(runeItem, RuneStoneAttributes.Low.WOOD)) {
                // Wood
                effects.add(newEffectObject(MobEffects.CONDUIT_POWER, levelInt));
                effects.add(newEffectObject(MobEffects.NIGHT_VISION, levelInt));
            }
        }

        return effects;
    }
    private static List<EffectObject> getMediumEffects(Item runeItem, Item basicRune, int levelInt) {
        List<EffectObject> effects = new ArrayList<>();

        if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.ENERGY_GATHERING)) {
            // Energy Gathering
            if (isMediumAttr(runeItem, RuneStoneAttributes.Medium.ILLUSION)) {
                // Illusion
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.HEALING)) {
            if (isMediumAttr(runeItem, RuneStoneAttributes.Medium.ILLUSION)) {
                // Illusion
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.PROTECTION)) {
            if (isMediumAttr(runeItem, RuneStoneAttributes.Medium.ILLUSION)) {
                effects.add(newEffectObject(MobEffects.INVISIBILITY, levelInt));
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.OFFENSE)) {
            if (isMediumAttr(runeItem, RuneStoneAttributes.Medium.ILLUSION)) {
                // Illusion
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.DEFENSE)) {
            if (isMediumAttr(runeItem, RuneStoneAttributes.Medium.ILLUSION)) {
                effects.add(newEffectObject(MobEffects.GLOWING, levelInt));
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.UTILITY)) {
            if (isMediumAttr(runeItem, RuneStoneAttributes.Medium.ILLUSION)) {
                // Illusion
            }
        }

        return effects;
    }
    private static List<EffectObject> getHighEffects(Item runeItem, Item basicRune, int levelInt) {
        List<EffectObject> effects = new ArrayList<>();

        if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.ENERGY_GATHERING)) {
            // Energy Gathering
            if (isHighAttr(runeItem, RuneStoneAttributes.High.TRIBULATION)) {
                effects.add(newEffectObject(ModEffects.QI_ABSORPTION.get(), levelInt, 5));
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.HEALING)) {
            if (isHighAttr(runeItem, RuneStoneAttributes.High.TRIBULATION)) {
                effects.add(newEffectObject(MobEffects.ABSORPTION, levelInt, 6));
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.PROTECTION)) {
            if (isHighAttr(runeItem, RuneStoneAttributes.High.TRIBULATION)) {
                effects.add(newEffectObject(MobEffects.DAMAGE_RESISTANCE, levelInt, 5));
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.OFFENSE)) {
            if (isHighAttr(runeItem, RuneStoneAttributes.High.TRIBULATION)) {
                // Tribulation
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.DEFENSE)) {
            if (isHighAttr(runeItem, RuneStoneAttributes.High.TRIBULATION)) {
                // Tribulation
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.UTILITY)) {
            if (isHighAttr(runeItem, RuneStoneAttributes.High.TRIBULATION)) {
                // Tribulation
            }
        }

        return effects;
    }
    private static List<EffectObject> getImmortalEffects(Item runeItem, Item basicRune, int levelInt) {
        List<EffectObject> effects = new ArrayList<>();

        if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.ENERGY_GATHERING)) {
            // Energy Gathering
            if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.ASCENSION)) {
                effects.add(newEffectObject(ModEffects.QI_ABSORPTION.get(), levelInt , 10));
            } else if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.INVULNERABLE)) {
                // Invulnerable
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.HEALING)) {
            if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.ASCENSION)) {
                effects.add(newEffectObject(MobEffects.HEALTH_BOOST, levelInt , 5));
                effects.add(newEffectObject(MobEffects.ABSORPTION, levelInt , 10));
                effects.add(newEffectObject(MobEffects.REGENERATION, levelInt , 10));
                effects.add(newEffectObject(MobEffects.HEAL, levelInt , 10));
            } else if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.INVULNERABLE)) {
                effects.add(newEffectObject(MobEffects.HEALTH_BOOST, levelInt ,105));
                effects.add(newEffectObject(MobEffects.ABSORPTION, levelInt , 15));
                effects.add(newEffectObject(MobEffects.REGENERATION, levelInt , 15));
                effects.add(newEffectObject(MobEffects.HEAL, levelInt , 15));
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.PROTECTION)) {
            if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.ASCENSION)) {
                effects.add(newEffectObject(MobEffects.DAMAGE_RESISTANCE, levelInt,10));
                effects.add(newEffectObject(MobEffects.FIRE_RESISTANCE, levelInt,10));
                effects.add(newEffectObject(MobEffects.WATER_BREATHING, levelInt,10));
            } else if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.INVULNERABLE)) {
                effects.add(newEffectObject(MobEffects.DAMAGE_RESISTANCE, levelInt,15));
                effects.add(newEffectObject(MobEffects.FIRE_RESISTANCE, levelInt,15));
                effects.add(newEffectObject(MobEffects.WATER_BREATHING, levelInt,15));
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.OFFENSE)) {
            if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.ASCENSION)) {
                effects.add(newEffectObject(MobEffects.DAMAGE_BOOST, levelInt,10));
            } else if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.INVULNERABLE)) {
                effects.add(newEffectObject(MobEffects.DAMAGE_BOOST, levelInt,15));
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.DEFENSE)) {
            if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.ASCENSION)) {
                // Ascension
            } else if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.INVULNERABLE)) {
                // Invulnerable
            }
        } else if (isBasicAttr(basicRune, RuneStoneAttributes.Basic.UTILITY)) {
            if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.ASCENSION)) {
                // Ascension
            } else if (isImmortalAttr(runeItem, RuneStoneAttributes.Immortal.INVULNERABLE)) {
                // Invulnerable
            }
        }

        return effects;
    }

    private static class EffectObject {
        public MobEffect effect;
        public int amplifier;

        public EffectObject(MobEffect effect, int amplifier) {
            this.effect = effect;
            this.amplifier = amplifier;
        }
    }
}
