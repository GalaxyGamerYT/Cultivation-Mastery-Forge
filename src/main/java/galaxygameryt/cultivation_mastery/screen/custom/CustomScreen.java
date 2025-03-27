package galaxygameryt.cultivation_mastery.screen.custom;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.event.ModEvents;
import galaxygameryt.cultivation_mastery.util.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.UUID;

public class CustomScreen extends Screen {
    private static HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();
    Player player = Minecraft.getInstance().player;

    public CustomScreen() {
        super(Component.translatable("menu.title.cultivation_mastery.custom_menu"));
    }

    private void sync() {
        this.player = Minecraft.getInstance().player;
        CustomScreen.playerDataMap = ModEvents.playerDataMap;
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

        pGuiGraphics.blit(background.texture, background.left, background.top, background.xOffset, background.yOffset, background.width, background.height);

        pGuiGraphics.drawString(this.font, "The start of your cultivation journey!!!", background.left+gap, background.top+gap, 0x00000);
        pGuiGraphics.drawString(this.font, String.format("Body: %.2f", ), background.left+gap, background.top+gap, 0x00000);
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
