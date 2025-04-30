package galaxygameryt.cultivation_mastery.client.gui.screens.custom.rune_inscribing_table;

import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuTypes;
import galaxygameryt.cultivation_mastery.recipe.ModRecipes;
import galaxygameryt.cultivation_mastery.recipe.custom.RuneInscribingRecipe;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class RuneInscribingTableMenu extends AbstractContainerMenu {
    public static final int RUNE_SLOT = 1;
    public static final int RESOURCE_SLOT = 2;
    public static final int RESULT_SLOT = 2;
    public static final int INV_SLOT_START = 3;
    public static final int INV_SLOT_END = 30;
    public static final int USE_ROW_SLOT_START = 30;
    public static final int USE_ROW_SLOT_END = 39;

    private final ContainerLevelAccess access;
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final Level level;
    private List<RuneInscribingRecipe> recipes = Lists.newArrayList();
//    private ItemStack input = ItemStack.EMPTY;
    private NonNullList<ItemStack> input = NonNullList.withSize(2, ItemStack.EMPTY);
    final Slot input1Slot;
    final Slot input2Slot;
    final Slot resultSlot;
    Runnable slotUpdateListener = () -> {};
    public final Container container = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            super.setChanged();
            RuneInscribingTableMenu.this.slotsChanged(this);
            RuneInscribingTableMenu.this.slotUpdateListener.run();
        }
    };

    final ResultContainer resultContainer = new ResultContainer();

    public RuneInscribingTableMenu(int containerId, Inventory playerInventory, FriendlyByteBuf friendlyByteBuf) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public RuneInscribingTableMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess access) {
        super(ModMenuTypes.RUNE_INSCRIBING_TABLE_MENU.get(), containerId);
        this.access = access;
        this.level = playerInventory.player.level();
        this.input1Slot = this.addSlot(new Slot(this.container, 0, 20, 16));
        this.input2Slot = this.addSlot(new Slot(this.container, 1, 20, 53));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 2, 143, 33) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                stack.onCraftedBy(player.level(), player, stack.getCount());
                RuneInscribingTableMenu.this.resultContainer.awardUsedRecipes(player, this.getRelevantItems());
                ItemStack itemStack1 = RuneInscribingTableMenu.this.input1Slot.remove(1);
                ItemStack itemStack2 = RuneInscribingTableMenu.this.input2Slot.remove(1);
                if (!itemStack1.isEmpty() && !itemStack2.isEmpty()) {
                    RuneInscribingTableMenu.this.setupResultSlot();
                }

                super.onTake(player, stack);
            }

            private List<ItemStack> getRelevantItems() {
                return List.of(RuneInscribingTableMenu.this.input2Slot.getItem());
            }
        });

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 9; row++) {
                this.addSlot(new Slot(playerInventory, row + col * 9 + 9, 8 + row * 18, 84 + col * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        this.addDataSlot(this.selectedRecipeIndex);
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public List<RuneInscribingRecipe> getRecipes() {
        return this.recipes;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public boolean hasInputItem() {
        return this.input1Slot.hasItem() && this.input2Slot.hasItem() && !this.recipes.isEmpty();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index == 1) {
                item.onCraftedBy(itemstack1, player.level(), player);
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.getRecipeManager().getRecipeFor(RecipeType.STONECUTTING, new SimpleContainer(itemstack1), this.level).isPresent()) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            this.broadcastChanges();
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, ModBlocks.RUNE_INSCRIBING_TABLE.get());
    }

    @Override
    public boolean clickMenuButton(@NotNull Player player, int id) {
        if (this.isValidRecipeIndex(id)) {
            this.selectedRecipeIndex.set(id);
            this.setupResultSlot();
        }

        return true;
    }

    private boolean isValidRecipeIndex(int recipeIndex) {
        return recipeIndex >= 0 && recipeIndex < this.recipes.size();
    }

    @Override
    public void slotsChanged(@NotNull Container inventory) {
        ItemStack itemStack1 = this.input1Slot.getItem();
        if (!itemStack1.is(this.input.get(0).getItem())) {
            this.input.set(0, itemStack1.copy());
            this.setupRecipeList((SimpleContainer) inventory, itemStack1);
        }

        ItemStack itemStack2 = this.input2Slot.getItem();
        if (!itemStack2.is(this.input.get(1).getItem())) {
            this.input.set(1, itemStack2.copy());
            this.setupRecipeList((SimpleContainer) inventory, itemStack2);
        }
    }

    private void setupRecipeList(SimpleContainer container, ItemStack stack) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.recipes = this.level.getRecipeManager().getRecipesFor(ModRecipes.Types.RUNE_INSCRIBING_TABLE_TYPE.get(), container, this.level);
            Logger.info(recipes.toString());
        }
    }

    void setupResultSlot() {
        if (this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            RuneInscribingRecipe recipe = this.recipes.get(this.selectedRecipeIndex.get());
            ItemStack itemstack = recipe.assemble((SimpleContainer) this.container, this.level.registryAccess());
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.resultContainer.setRecipeUsed(recipe);
                this.resultSlot.set(itemstack);
            } else {
                this.resultSlot.set(ItemStack.EMPTY);
            }

            this.broadcastChanges();
        }
    }

    @Override
    public @NotNull MenuType<?> getType() {
        return ModMenuTypes.RUNE_INSCRIBING_TABLE_MENU.get();
    }

    public void registerUpdateListener(Runnable listener) {
        this.slotUpdateListener = listener;
    }

    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, @NotNull Slot slot) {
        return slot.container != this.resultContainer && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute(((level1, pos) -> {
            this.clearContainer(player, this.container);
        }));
    }

    public static class Provider implements MenuProvider {
        public final Component title;
        public final ContainerLevelAccess access;

        public Provider(Component title, ContainerLevelAccess access) {
            this.title = title;
            this.access = access;
        }

        @Override
        public @NotNull Component getDisplayName() {
            return title;
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
            return new RuneInscribingTableMenu(containerId, playerInventory, access);
        }
    }
}
