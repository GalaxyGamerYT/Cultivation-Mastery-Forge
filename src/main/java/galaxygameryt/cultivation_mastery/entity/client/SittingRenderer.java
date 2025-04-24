package galaxygameryt.cultivation_mastery.entity.client;

import galaxygameryt.cultivation_mastery.entity.custom.SittingEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SittingRenderer extends EntityRenderer<SittingEntity> {
    public SittingRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(SittingEntity pEntity) {
        return null;
    }

    @Override
    public boolean shouldRender(SittingEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }
}
