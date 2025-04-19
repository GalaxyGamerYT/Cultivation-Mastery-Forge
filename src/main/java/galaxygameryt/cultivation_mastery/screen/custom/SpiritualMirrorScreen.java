package galaxygameryt.cultivation_mastery.screen.custom;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.data.capability.PlayerCapabilityProvider;
import galaxygameryt.cultivation_mastery.util.data.player.ClientPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SpiritualMirrorScreen extends Screen {
    private Player player;
    private ClientPlayerData playerData;
    private final boolean self;

    public SpiritualMirrorScreen(Player target) {
        super(Component.translatable("menu.title.cultivation_mastery.spiritual_mirror_menu"));
        player = target;
        assert Minecraft.getInstance().player != null;
        if (target.getUUID() == Minecraft.getInstance().player.getUUID()) {
            playerData = CultivationMastery.CLIENT_PLAYER_DATA;
            self = true;
        } else {
            playerData = new ClientPlayerData();
            target.getCapability(PlayerCapabilityProvider.PLAYER_CAPABILITY).ifPresent(capability -> {
                playerData.setCultivation(capability.getCultivation());
                playerData.setMaxQi(capability.getMaxQi());
                playerData.setQiIncrease(capability.getQiIncrease());
                playerData.setQi(capability.getQi());
                playerData.setBody(capability.getBody());
                playerData.setRealm(capability.getRealm());
                playerData.setMaxQi(capability.getMaxQi());
            });
            self = false;
        }
    }

    private void sync() {
        if (player == Minecraft.getInstance().player) {
            playerData = CultivationMastery.CLIENT_PLAYER_DATA;
        }
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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        sync();

        var background = new Object() {
            final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID,
                    "textures/gui/spiritual_mirror_gui.png");
            final int width = 256;
            final int height = 256;
            final int left = (guiGraphics.guiWidth()/2)-(width/2);
            final int top = (guiGraphics.guiHeight()/2)-(height/2);
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
        final int label5_y = (label4_y+label_height)+gap;
        final int label6_y = (label5_y+label_height)+gap;
        final int label7_y = (label6_y+label_height)+gap;
        final int label8_y = (label7_y+label_height)+gap;

        guiGraphics.blit(background.texture, background.left, background.top, background.xOffset, background.yOffset, background.width, background.height);

//        guiGraphics.drawString(this.font, "The start of your cultivation journey!!!", label_x, label1_y, label_colour, false);
        drawCenteredString(guiGraphics, String.format("%s's Cultivation Stats", player.getDisplayName().getString()), background.left+(background.width/2), label1_y, label_colour, false);
        guiGraphics.drawString(this.font, String.format("Realm: %s", playerData.getRealmDisplay()), label_x, label2_y, label_colour, false);
        guiGraphics.drawString(this.font, String.format("Body: %.2f", playerData.getBody()), label_x, label3_y, label_colour, false);
        guiGraphics.drawString(this.font, String.format("Qi: %.2f", playerData.getQi()), label_x, label4_y, label_colour, false);
        guiGraphics.drawString(this.font, String.format("Max Qi: %d", playerData.getMaxQi()), label_x, label5_y, label_colour, false);
        guiGraphics.drawString(this.font, String.format("Base Qi Multiplier: %dX", playerData.getBaseQiMulti()), label_x, label6_y, label_colour, false);
        guiGraphics.drawString(this.font, String.format("Environment Qi Multiplier: %dX", playerData.getEnvQiMulti()), label_x, label7_y, label_colour, false);
        guiGraphics.drawString(this.font, String.format("Qi Increase: %.2f", playerData.getQiIncrease()), label_x, label8_y, label_colour, false);
    }

    private void drawCenteredString(GuiGraphics guiGraphics, String text, int pX, int pY, int color, boolean dropShadow) {
        guiGraphics.drawString(this.font, text, pX - this.font.width(text) / 2, pY, color, dropShadow);
    }


    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
    }
}
