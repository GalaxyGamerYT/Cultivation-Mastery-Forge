package galaxygameryt.cultivation_mastery.item.custom;

import galaxygameryt.cultivation_mastery.client.gui.screens.custom.container.ContainerMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class ContainerItem extends Item {
    private static final String INVENTORY_TAG = "containerItems";
    private static final int SLOT_COUNT = 27;
    private final String titleKey; // "menu.title.cultivation_mastery.guide_menu"

    public ContainerItem(Properties properties, String key) {
        super(properties);
        this.titleKey = key;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        return openContainer(context.getPlayer(), context.getHand());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        openContainer(pPlayer, pUsedHand);
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private InteractionResult openContainer(Player player, InteractionHand hand) {
        if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            ItemStack stack = player.getItemInHand(hand);
            SimpleContainer inventory = loadInventory(stack); // Load stored items
            String name = stack.getDisplayName().plainCopy().getString();
            name = name.substring(1, name.length() - 1);
            NetworkHooks.openScreen(serverPlayer, new ContainerMenu.Provider(inventory, stack, Component.literal(name), SLOT_COUNT, player.getInventory().selected),
                    buf -> buf.writeItem(stack));
        }
        return InteractionResult.SUCCESS;
    }

    public static SimpleContainer loadInventory(ItemStack stack) {
        SimpleContainer inventory = new SimpleContainer(SLOT_COUNT);
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(INVENTORY_TAG, Tag.TAG_LIST)) {
            ListTag list = tag.getList(INVENTORY_TAG, Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag itemTag = list.getCompound(i);
                int slot = itemTag.getInt("Slot");
                if (slot >= 0 && slot < SLOT_COUNT) {
                    inventory.setItem(slot, ItemStack.of(itemTag));
                }
            }
        }
        return inventory;
    }

    public static void saveInventory(ItemStack stack, SimpleContainer inventory) {
        ListTag list = new ListTag();
        for (int i = 0; i < SLOT_COUNT; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (!itemStack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                itemStack.save(itemTag);
                list.add(itemTag);
            }
        }
        stack.getOrCreateTag().put(INVENTORY_TAG, list);
    }
}
