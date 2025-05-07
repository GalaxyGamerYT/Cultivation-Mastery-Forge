package galaxygameryt.cultivation_mastery.client.gui.screens.custom.formation_core;

import com.mojang.blaze3d.systems.RenderSystem;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.Logger;
import galaxygameryt.cultivation_mastery.util.data.player.ClientPlayerData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FormationCoreScreen extends AbstractContainerScreen<FormationCoreMenu> {
    private static final ResourceLocation BASE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/containers/formation_core/formation_core_base.png");
    private static final List<ResourceLocation> ACTIVE_ARRAY_TEXTURES = List.of(
            ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/containers/formation_core/active_array/active_array_0.png"),
            ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/containers/formation_core/active_array/active_array_1.png"),
            ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/containers/formation_core/active_array/active_array_2.png"),
            ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/containers/formation_core/active_array/active_array_3.png"),
            ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/containers/formation_core/active_array/active_array_4.png"),
            ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/containers/formation_core/active_array/active_array_5.png")
    );
    private static final ResourceLocation INACTIVE_ARRAY =
            ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/containers/formation_core/inactive_array.png");
    private static final ResourceLocation ARRAY_SLOTS =
            ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/containers/formation_core/array_slots.png");

    public FormationCoreScreen(FormationCoreMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 175;
        this.imageHeight = 216;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        this.renderBackground(guiGraphics);
        int x = leftPos;
        int y = topPos;
        guiGraphics.blit(BASE_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        ClientPlayerData playerData = CultivationMastery.CLIENT_PLAYER_DATA;
        if (menu.getActive() == 0) {
            if (playerData.tickCounterExists("formation_core_gui_array")) playerData.removeTickCounter("formation_core_gui_array");
            guiGraphics.blit(INACTIVE_ARRAY, x, y, 0, 0, this.imageWidth, this.imageHeight);
        } else if (menu.getActive() == 1) {
            if (!playerData.tickCounterExists("formation_core_gui_array")) playerData.createTickCounter("formation_core_gui_array");

            int frameRate = 2;
            double frame = Math.floor((double) playerData.getTickCounter("formation_core_gui_array") / frameRate);
            int index = (int) Math.min(frame, 5);
            guiGraphics.blit(ACTIVE_ARRAY_TEXTURES.get(index), x, y, 0, 0, this.imageWidth, this.imageHeight);
        }
        guiGraphics.blit(ARRAY_SLOTS, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBg(guiGraphics, partialTick, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void removed() {
        CultivationMastery.CLIENT_PLAYER_DATA.removeTickCounter("formation_core_gui_array");
        super.removed();
    }
}
