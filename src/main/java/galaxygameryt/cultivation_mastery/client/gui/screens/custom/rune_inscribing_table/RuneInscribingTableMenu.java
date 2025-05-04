package galaxygameryt.cultivation_mastery.client.gui.screens.custom.rune_inscribing_table;

import galaxygameryt.cultivation_mastery.block.ModBlocks;
import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuTypes;
import galaxygameryt.cultivation_mastery.recipe.ModRecipes;
import galaxygameryt.cultivation_mastery.recipe.custom.RuneInscribingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneInscribingTableMenu extends AbstractContainerMenu {
    public static final int BASE_ITEM_SLOT_INDEX = 0;
    public static final int INSCRIBING_ITEM_SLOT_INDEX = 1;
    public static final int RESULT_SLOT = 2;
    public static final int INV_SLOT_START = 3;
    public static final int INV_SLOT_END = 30;
    public static final int USE_ROW_SLOT_START = 30;
    public static final int USE_ROW_SLOT_END = 39;

    private final ContainerLevelAccess access;
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final Level level;
    private List<RuneInscribingRecipe> recipes = Lists.newArrayList();
    private final NonNullList<ItemStack> input = NonNullList.withSize(2, ItemStack.EMPTY);
    final Slot BaseItemSlot;
    final Slot InscribingItemSlot;
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

    public RuneInscribingTableMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public RuneInscribingTableMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess access) {
        super(ModMenuTypes.RUNE_INSCRIBING_TABLE_MENU.get(), containerId);
        this.access = access;
        this.level = playerInventory.player.level();
        this.BaseItemSlot = this.addSlot(new Slot(this.container, BASE_ITEM_SLOT_INDEX, 20, 53));
        this.InscribingItemSlot = this.addSlot(new Slot(this.container, INSCRIBING_ITEM_SLOT_INDEX, 20, 16));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, RESULT_SLOT, 143, 33) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                stack.onCraftedBy(player.level(), player, stack.getCount());
                RuneInscribingTableMenu.this.resultContainer.awardUsedRecipes(player, this.getRelevantItems());
                ItemStack baseItemStack = RuneInscribingTableMenu.this.BaseItemSlot.remove(1);
                ItemStack inscribingItemStack = RuneInscribingTableMenu.this.InscribingItemSlot.remove(1);
                if (!baseItemStack.isEmpty() && !inscribingItemStack.isEmpty()) {
                    RuneInscribingTableMenu.this.setupResultSlot();
                }

                super.onTake(player, stack);
            }

            private List<ItemStack> getRelevantItems() {
                return List.of(RuneInscribingTableMenu.this.InscribingItemSlot.getItem());
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
        return this.BaseItemSlot.hasItem() && this.InscribingItemSlot.hasItem() && !this.recipes.isEmpty();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack originalStack = slot.getItem();
        ItemStack stackToMove = originalStack.copy();

        // Clicked slot is output slot
        if (index == RESULT_SLOT) {
            if (!this.moveItemStackTo(originalStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                return ItemStack.EMPTY;
            }
            slot.onQuickCraft(originalStack, stackToMove);
        }

        // Clicked slot is in player inventory
        else if (index >= INV_SLOT_START) {
            boolean baseItemValidForRecipe = this.level.getRecipeManager()
                    .getAllRecipesFor(ModRecipes.Types.RUNE_INSCRIBING_TABLE_TYPE.get()).stream()
                    .anyMatch(recipe -> recipe.getInputItems().get(BASE_ITEM_SLOT_INDEX).test(originalStack));

            if (baseItemValidForRecipe) {
                if (!this.moveItemStackTo(originalStack, BASE_ITEM_SLOT_INDEX, INSCRIBING_ITEM_SLOT_INDEX, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                boolean inscribingItemValidForRecipe = this.level.getRecipeManager()
                        .getAllRecipesFor(ModRecipes.Types.RUNE_INSCRIBING_TABLE_TYPE.get()).stream()
                        .anyMatch(recipe -> recipe.getInputItems().get(INSCRIBING_ITEM_SLOT_INDEX).test(originalStack));

                if (!inscribingItemValidForRecipe) {
                    return ItemStack.EMPTY;
                }

                if (!this.moveItemStackTo(originalStack, INSCRIBING_ITEM_SLOT_INDEX, INSCRIBING_ITEM_SLOT_INDEX + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        // Clicked slot is an input slot
        else {
            // Send back to player inventory
            if (!this.moveItemStackTo(originalStack, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (originalStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (originalStack.getCount() == stackToMove.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, originalStack);
        return stackToMove;
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
        ItemStack baseItemStack = this.BaseItemSlot.getItem();
        ItemStack InscribingItemStack = this.InscribingItemSlot.getItem();
        if (!ItemStack.matches(baseItemStack, this.input.get(BASE_ITEM_SLOT_INDEX)) || !ItemStack.matches(InscribingItemStack, this.input.get(INSCRIBING_ITEM_SLOT_INDEX))) {
            this.input.set(BASE_ITEM_SLOT_INDEX, baseItemStack.copy());
            this.input.set(INSCRIBING_ITEM_SLOT_INDEX, InscribingItemStack.copy());
            this.setupRecipeList(inventory);
            this.setupResultSlot();
        }
    }

    private void setupRecipeList(Container container) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!input.isEmpty()) {
            this.recipes = this.level.getRecipeManager().getRecipesFor(ModRecipes.Types.RUNE_INSCRIBING_TABLE_TYPE.get(), container, this.level);
        }
    }

    void setupResultSlot() {
        int selectedRecipeIndex = this.selectedRecipeIndex.get();
        if (this.recipes != null && selectedRecipeIndex >= 0 && selectedRecipeIndex < this.recipes.size()) {
            RuneInscribingRecipe recipe = this.recipes.get(selectedRecipeIndex);
            ItemStack result = recipe.assemble(this.container, this.level.registryAccess());

            this.resultSlot.set(result);
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
        }
        this.broadcastChanges();
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
