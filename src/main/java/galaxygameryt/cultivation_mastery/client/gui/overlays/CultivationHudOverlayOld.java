package galaxygameryt.cultivation_mastery.client.gui.overlays;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.player.ClientPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CultivationHudOverlayOld {
    public static final IGuiOverlay HUD_CULTIVATION = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if(player!=null) {
            ClientPlayerData playerData = CultivationMastery.CLIENT_PLAYER_DATA;
            boolean cultivating = playerData.getCultivation();
            float qi = playerData.getQi();
            float body = playerData.getBody();
            float realm = playerData.getRealm();

            String quantifier = "";
            if (qi >= 1000000) {
                quantifier = "M";
            } else if (qi >= 1000) {
                quantifier = "K";
            }

            int gap = 5;

            var background = new Object() {
                final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID,
                        "textures/hud/background_scroll.png");
                final int width = 150;
                final int height = 75;
                final int left = gap;
                final int top = gap;
                final int xOffset = 0;
                final int yOffset = 0;
            };

            var center_label = new Object() {
                final int left = background.left + 25;
                final int top = (background.top + (background.height / 2)) - gap;
            };

            var top_label = new Object() {
                final int left = background.left + 25;
                final int top = background.top + (background.height / 4);
            };

            var bottom_label = new Object() {
                final int left = background.left + 25;
                final int top = (background.top + (background.height / 4) * 3) - gap;
            };

            var left_label = new Object() {
                final int left = (screenWidth/2)+(screenWidth/5);
                final int top = screenHeight - (gap*2);
            };

            var realm_label = new Object() {
                final int left = screenWidth/2;
                final int top = screenHeight - 50;
            };

            var qi_label = new Object() {
                boolean visible = false;
                int left;
                int top;
            };

            var body_label = new Object() {
                boolean visible = false;
                int left;
                int top;
            };

            final int label_colour = 0xFFFFFF;

            if (cultivating) {
//                if (realm >= 1.6f) {
//                    if (realm < 2) {
//                        qi_label.left = bottom_label.left;
//                        qi_label.top = bottom_label.top;
//                    } else {
//                        qi_label.left = center_label.left;
//                        qi_label.top = center_label.top;
//                    }
//                    qi_label.visible = true;
//                }
//                if (realm < 2) {
//                    if (realm >= 1.6f) {
//                        body_label.left = top_label.left;
//                        body_label.top = top_label.top;
//                    } else {
//                        body_label.left = center_label.left;
//                        body_label.top = center_label.top;
//                    }
//                    body_label.visible = true;
//                }

                guiGraphics.blit(background.texture, background.left, background.top, background.xOffset, background.yOffset, background.width, background.height,
                        background.width, background.height);

                body_label.left = left_label.left;
                body_label.top = left_label.top;
                body_label.visible = true;
                guiGraphics.drawCenteredString(gui.getFont(), playerData.getRealmDisplay(),realm_label.left, realm_label.top, label_colour);
                if (body_label.visible) {
                    guiGraphics.drawString(gui.getFont(), String.format("Body: %.2f", body), body_label.left, body_label.top, label_colour);
                }
                if (qi_label.visible) {
                    guiGraphics.drawString(gui.getFont(), String.format("Qi: %.2f%s",qi, quantifier), qi_label.left, qi_label.top, label_colour);
                }
            }
        }
    });
}
