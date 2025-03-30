package galaxygameryt.cultivation_mastery.screen.custom;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.ClientPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CustomScreen extends Screen {
    private ClientPlayerData clientPlayerData;
    private Player player;

    public CustomScreen() {
        super(Component.translatable("menu.title.cultivation_mastery.custom_menu"));
    }

    private void sync() {
        this.player = Minecraft.getInstance().player;
        this.clientPlayerData = CultivationMastery.CLIENT_PLAYER_DATA_MAP.get(this.player.getUUID());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        sync();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        sync();

        var background = new Object() {
            final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID,
                    "textures/gui/spiritual_mirror_gui.png");
            final int width = 256;
            final int height = 256;
            final int left = (pGuiGraphics.guiWidth()/2)-(width/2);
            final int top = (pGuiGraphics.guiHeight()/2)-(height/2);
            final int xOffset = 0;
            final int yOffset = 0;
        };



        final int gap = 5;
        final int label_height = 10;
        final int label_colour = 0x00404040;

        final int label_x = background.left+gap;
        final int label1_y = background.top+gap;
        final int label2_y = (label1_y+label_height)+gap;

        pGuiGraphics.blit(background.texture, background.left, background.top, background.xOffset, background.yOffset, background.width, background.height);

        pGuiGraphics.drawString(this.font, "The start of your cultivation journey!!!", label_x, label1_y, label_colour, false);
        pGuiGraphics.drawString(this.font, String.format("Body: %.2f", clientPlayerData.getBody()), label_x, label2_y, label_colour, false);
    }


    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
    }

    //    @Override
//    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
////        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
//
//        var background = new Object() {
//            final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID,
//                    "textures/gui/spiritual_mirror_gui.png");
//            int width = 248;
//            int height = 166;
//            final int left = width/4;
//            final int top = height/4;
//            final int xOffset = 0;
//            final int yOffset = 0;
//        };
//
//        pGuiGraphics.blit(background.texture, background.left, background.top, background.xOffset, background.yOffset, background.width, imageHeight);
//    }
//
//    @Override
//    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
////        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
//        pGuiGraphics.drawString(this.font, "The start of your cultivation journey!!!", 0, 100, 0x00000);
//    }
//
//    @Override
//    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
//        renderBackground(pGuiGraphics);
//        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
//    }
}
