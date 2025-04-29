package galaxygameryt.cultivation_mastery.client.gui.screens.custom.container;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ContainerScreen extends AbstractContainerScreen<ContainerMenu> {
    private static final ResourceLocation SLOT_TEXTURE = ResourceLocation
            .withDefaultNamespace("textures/gui/sprites/container/slot.png");
    private static final ResourceLocation SHULKER_BOX_TEXTURE = ResourceLocation
            .withDefaultNamespace("textures/gui/container/shulker_box.png");

    public ContainerScreen(ContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        // 176 166
        var background = new Object() {
            final ResourceLocation texture = SHULKER_BOX_TEXTURE;
            final int width = 176;
            final int height = 166;
            final int left = (guiGraphics.guiWidth()/2)-(width/2);
            final int top = (guiGraphics.guiHeight()/2)-(height/2);
            final int xOffset = 0;
            final int yOffset = 0;
        };

        guiGraphics.blit(background.texture, background.left, background.top, background.xOffset, background.yOffset, background.width, background.height);
    }
}
