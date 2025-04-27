package galaxygameryt.cultivation_mastery.item.custom.rune_stones;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RuneStoneItemRenderer extends ItemRenderer {
    public RuneStoneItemRenderer(Minecraft pMinecraft, TextureManager pTextureManager, ModelManager pModelManager, ItemColors pItemColors, BlockEntityWithoutLevelRenderer pBlockEntityRenderer) {
        super(pMinecraft, pTextureManager, pModelManager, pItemColors, pBlockEntityRenderer);
    }

    @Override
    public void render(ItemStack itemStack, @NotNull ItemDisplayContext displayContext, boolean leftHand, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int combinedLight, int combinedOverlay, @NotNull BakedModel model) {
        if (itemStack.getItem() instanceof ICreativeRuneStone runeStone) {
            int colour = runeStone.getLevelColor();

            applyCustomGlimmerEffect(poseStack, itemStack, combinedLight, combinedOverlay, colour, leftHand, displayContext,  model);
        }
        super.render(itemStack, displayContext, leftHand, poseStack, buffer, combinedLight, combinedOverlay, model);
    }

    // Method to apply the custom glimmer effect to the item
    private void applyCustomGlimmerEffect(PoseStack poseStack, ItemStack stack, int light, int overlay, int color, boolean leftHand, ItemDisplayContext displayContext, BakedModel model) {
        // Convert the hex color to RGBA
        float red = ((color >> 16) & 255) / 255.0F;
        float green = ((color >> 8) & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        // Enable blending and set the custom glimmer color
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(red, green, blue, 1.0F);

        // Render the item normally while applying the glimmer effect
        Minecraft.getInstance().getItemRenderer().render(stack, displayContext, leftHand, poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), light, overlay, model);

        // Disable blending after rendering
        RenderSystem.disableBlend();
    }
}
