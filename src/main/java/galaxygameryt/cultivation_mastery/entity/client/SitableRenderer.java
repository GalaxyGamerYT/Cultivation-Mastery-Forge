package galaxygameryt.cultivation_mastery.entity.client;

import galaxygameryt.cultivation_mastery.entity.custom.SitableEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SitableRenderer extends EntityRenderer<SitableEntity> {
    public SitableRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(SitableEntity pEntity) {
        return null;
    }

    @Override
    public boolean shouldRender(SitableEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }
}
