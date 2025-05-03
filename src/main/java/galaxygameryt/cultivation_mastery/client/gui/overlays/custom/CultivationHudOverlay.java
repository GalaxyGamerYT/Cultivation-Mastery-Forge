package galaxygameryt.cultivation_mastery.client.gui.overlays.custom;

import com.mojang.blaze3d.platform.Window;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.player.ClientPlayerData;
import galaxygameryt.cultivation_mastery.util.helpers.MathHelper;
import galaxygameryt.cultivation_mastery.util.objects.labels.DataLabelObject;
import galaxygameryt.cultivation_mastery.util.objects.labels.LabelObject;
import galaxygameryt.cultivation_mastery.util.objects.labels.centered.CenteredDataLabelObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CultivationHudOverlay implements IGuiOverlay {
    public static final ResourceLocation SCROLL_TEXTURE = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID,
            "textures/gui/hud/background_scroll.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
        Font font = gui.getFont();
        int scaledScreenHeight = window.getGuiScaledHeight();
        int scaledScreenWidth = window.getGuiScaledWidth();

        Player player = minecraft.player;
        if(player!=null) {
            ClientPlayerData playerData = CultivationMastery.CLIENT_PLAYER_DATA;
            boolean cultivating = playerData.getCultivation();
            float qi = playerData.getQi();
            float body = playerData.getBody();
            float realm = playerData.getRealm();

            String quantifier = "";
            if (qi >= 1000000) {
                quantifier = "M";
                qi = MathHelper.roundFloat(qi/1000000,2);
            } else if (qi >= 1000) {
                quantifier = "K";
                qi = MathHelper.roundFloat(qi/1000, 2);
            }

            int gap = 5;

            var scrollImage = new Object() {
                final ResourceLocation texture = SCROLL_TEXTURE;
                final int width = 100;
                final int height = 50;
                final int left = (screenWidth-width) - gap;
                final int top = (screenHeight/2)-(height/2);
                final int xOffset = 0;
                final int yOffset = 0;
                final int textureWidth = width;
                final int textureHeight = height;
                boolean visible = false;
            };

            LabelObject scrollTopLabel = new LabelObject(scrollImage.left + 25, scrollImage.top + (scrollImage.height / 4));
            LabelObject scrollCenterLabel = new LabelObject(scrollImage.left + 25, (scrollImage.top + (scrollImage.height / 2)) - 3);
            LabelObject scrollBottomLabel = new LabelObject(scrollImage.left + 25, (scrollImage.top + (scrollImage.height / 4) * 3) - gap);

            CenteredDataLabelObject realmLabel = new CenteredDataLabelObject(scaledScreenWidth/2, scaledScreenHeight - 50);
            DataLabelObject bodyLabel = new DataLabelObject();
            DataLabelObject qiLabel = new DataLabelObject();

            final int label_colour = 0xFFFFFF;


            if (cultivating) {
                realmLabel.visible = true;
                scrollImage.visible = true;

                if (player.isCreative()) {
                    realmLabel.y = scaledScreenHeight - 35;
                }

                if (realm < 2) {
                    bodyLabel.visible = true;
                    if (realm < 1.6) {
                        bodyLabel.setLabel(scrollCenterLabel);
                    } else {
                        bodyLabel.setLabel(scrollTopLabel);
                    }
                }

                if (realm > 1.6) {
                    qiLabel.visible = true;
                    if (realm < 2) {
                        qiLabel.setLabel(scrollBottomLabel);
                    } else {
                        qiLabel.setLabel(scrollCenterLabel);
                    }
                }
            }

            if (scrollImage.visible) {
                guiGraphics.blit(scrollImage.texture, scrollImage.left, scrollImage.top, scrollImage.xOffset, scrollImage.yOffset,
                        scrollImage.width, scrollImage.height, scrollImage.textureWidth, scrollImage.textureHeight);
            }

            if (realmLabel.visible) {
                guiGraphics.drawCenteredString(font, playerData.getRealmDisplay(), realmLabel.x, realmLabel.y, label_colour);
            }
            if (bodyLabel.visible) {
                guiGraphics.drawString(font, String.format("Body: %.2f", body), bodyLabel.left, bodyLabel.top, label_colour);
            }
            if (qiLabel.visible) {
                guiGraphics.drawString(font, String.format("Qi: %.2f%s", qi, quantifier), qiLabel.left, qiLabel.top, label_colour);
            }
        }
    }
}
