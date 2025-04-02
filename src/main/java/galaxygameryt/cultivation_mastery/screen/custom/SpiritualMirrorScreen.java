package galaxygameryt.cultivation_mastery.screen.custom;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.player_data.ClientPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SpiritualMirrorScreen extends Screen {
    private Player player;

    public SpiritualMirrorScreen() {
        super(Component.translatable("menu.title.cultivation_mastery.spiritual_mirror_menu"));
    }

    private void sync() {
        this.player = Minecraft.getInstance().player;
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

        final int gap = 10;
        final int label_height = 10;
        final int label_colour = 0x00404040;

        final int label_x = background.left+gap;
        final int label1_x = background.left+(background.width/2);
        final int label1_y = background.top+gap;
        final int label2_y = (label1_y+label_height)+gap;
        final int label3_y = (label2_y+label_height)+gap;
        final int label4_y = (label3_y+label_height)+gap;

        pGuiGraphics.blit(background.texture, background.left, background.top, background.xOffset, background.yOffset, background.width, background.height);

//        pGuiGraphics.drawString(this.font, "The start of your cultivation journey!!!", label_x, label1_y, label_colour, false);
        drawCenteredString(pGuiGraphics, String.format("%s's Cultivation Stats", player.getDisplayName().getString()), background.left+(background.width/2), label1_y, label_colour, false);
        pGuiGraphics.drawString(this.font, String.format("Realm: %s", CultivationMastery.CLIENT_PLAYER_DATA.getRealmDisplay()), label_x, label2_y, label_colour, false);
        pGuiGraphics.drawString(this.font, String.format("Body: %.2f", CultivationMastery.CLIENT_PLAYER_DATA.getBody()), label_x, label3_y, label_colour, false);
        pGuiGraphics.drawString(this.font, String.format("Qi: %.2f", CultivationMastery.CLIENT_PLAYER_DATA.getQi()), label_x, label4_y, label_colour, false);
    }

    private void drawCenteredString(GuiGraphics guiGraphics, String text, int pX, int pY, int pColor, boolean dropShadow) {
        guiGraphics.drawString(this.font, text, pX - this.font.width(text) / 2, pY, pColor, dropShadow);
    }


    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
    }
}
