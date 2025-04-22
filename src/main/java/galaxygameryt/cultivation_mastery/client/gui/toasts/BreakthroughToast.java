package galaxygameryt.cultivation_mastery.client.gui.toasts;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BreakthroughToast implements Toast {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, "textures/gui/sprites/breakthrough_toast.png");
    private static final Component TITLE = Component.translatable("misc.cultivation_mastery.toast.breakthrough.title");

    private static final long TIME_VISIBLE = 2500;
    private static final int MARGIN = 4;

    @Override
    public @NotNull Visibility render(@NotNull GuiGraphics guiGraphics, @NotNull ToastComponent toastComponent, long timeSinceLastVisible) {
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        Font font = Minecraft.getInstance().font;

        var image = new Object() {
            final ResourceLocation texture = TEXTURE;
            final int width = 160;
            final int height = 32;
            final int left = (screenWidth-width) - MARGIN;
            final int top = MARGIN;
            final int xOffset = 0;
            final int yOffset = 0;
            final int textureWidth = width;
            final int textureHeight = height;
        };

        guiGraphics.blit(image.texture, 0, 0, image.xOffset, image.yOffset, image.width, image.height, image.textureWidth, image.textureHeight);

        drawCenteredString(guiGraphics, font, TITLE, image.width/2, image.height/2, ChatFormatting.BLACK.getColor());

        return CultivationMastery.CLIENT_PLAYER_DATA.getBreakthrough() ? Visibility.HIDE : Visibility.SHOW;
    }

    private void drawCenteredString(GuiGraphics guiGraphics, Font font, Component text, int x, int y, int color) {
        guiGraphics.drawString(font, text, x - font.width(text.getString()) / 2, y - (font.lineHeight/2), color, false);
    }
}
