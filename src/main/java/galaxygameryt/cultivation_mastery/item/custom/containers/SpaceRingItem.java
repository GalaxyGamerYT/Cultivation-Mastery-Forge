package galaxygameryt.cultivation_mastery.item.custom.containers;

import galaxygameryt.cultivation_mastery.item.custom.ContainerItem;

public class SpaceRingItem extends ContainerItem {
    private static final int SLOT_COUNT = 27;

    public SpaceRingItem(Properties properties, String key) {
        super(properties.fireResistant().stacksTo(1), key);
    }
}
