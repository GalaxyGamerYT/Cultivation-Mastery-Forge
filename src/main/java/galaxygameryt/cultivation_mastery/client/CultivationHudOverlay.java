package galaxygameryt.cultivation_mastery.client;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.ClientPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CultivationHudOverlay {
    public static final IGuiOverlay HUD_CULTIVATION = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if(player!=null) {
            ClientPlayerData clientPlayerData = CultivationMastery.CLIENT_PLAYER_DATA_MAP.get(player.getUUID());

            boolean cultivating = clientPlayerData.getCultivation();
            float qi = clientPlayerData.getQi();
            float body = clientPlayerData.getBody();
            float realm = clientPlayerData.getRealm();

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
                int width = 100;
                int height = 50;
                final int left = gap;
                final int top = screenHeight - (height + gap);
                final int xOffset = 0;
                final int yOffset = 0;
            };

            var center_label = new Object() {
                int left = background.left + 25;
                int top = (background.top + (background.height / 2)) - gap;
            };

            var top_label = new Object() {
                int left = background.left + 25;
                int top = (background.top + (background.height / 4)) - gap;
            };

            var bottom_label = new Object() {
                int left = background.left + 25;
                int top = (background.top + ((background.height / 4) * 3)) - gap;
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
            int x = 100;
            int y = 100;


            if (cultivating) {
                if (realm >= 0.7f) {
                    if (realm < 1) {
                        qi_label.left = bottom_label.left;
                        qi_label.top = bottom_label.top;
                    } else {
                        qi_label.left = center_label.left;
                        qi_label.top = center_label.top;
                    }
                    qi_label.visible = true;
                }
                if (realm < 1) {
                    if (realm >= 0.7f) {
                        body_label.left = top_label.left;
                        body_label.top = top_label.top;
                    } else {
                        body_label.left = center_label.left;
                        body_label.top = center_label.top;
                    }
                    body_label.visible = true;
                }

                guiGraphics.blit(background.texture, background.left, background.top, background.xOffset, background.yOffset, background.width, background.height,
                        100, 50);

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
