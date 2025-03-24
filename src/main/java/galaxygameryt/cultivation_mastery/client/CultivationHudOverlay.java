package galaxygameryt.cultivation_mastery.client;

import com.mojang.blaze3d.systems.RenderSystem;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.client.data.ClientCultivationData;
import galaxygameryt.cultivation_mastery.client.data.ClientQiData;
import galaxygameryt.cultivation_mastery.util.MathHelper;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CultivationHudOverlay {
    public static final IGuiOverlay HUD_CULTIVATION = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        boolean cultivating = ClientCultivationData.getPlayerCultivation();
        float qi = MathHelper.roundFloat(ClientQiData.getPlayerQi(),2);
        String quantifier = "";
        if (qi>=1000000) {
           quantifier = "M";
        } else if (qi>=1000) {
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

        int x = 100;
        int y = 100;


        if(cultivating) {
//            guiGraphics.blit(x, y, 0, 100, 50, BACKGROUND_SCROLL);
            guiGraphics.blit(background.texture, background.left, background.top, background.xOffset, background.yOffset, background.width, background.height,
                    100,50);
            guiGraphics.drawString(gui.getFont(), "QI:  "+qi+" "+quantifier, background.left+25, (background.top+(background.height/2))-gap, 0xFFFFFF);
        }
    });
}
