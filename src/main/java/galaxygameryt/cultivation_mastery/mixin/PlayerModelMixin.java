package galaxygameryt.cultivation_mastery.mixin;

import galaxygameryt.cultivation_mastery.entity.custom.MeditationEntity;
import galaxygameryt.cultivation_mastery.entity.custom.SitableEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> {
    @Shadow @Final public ModelPart jacket;

    @Shadow @Final public ModelPart leftSleeve;

    @Shadow @Final public ModelPart rightSleeve;

    @Shadow @Final public ModelPart leftPants;

    @Shadow @Final public ModelPart rightPants;

    public PlayerModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "setupAnim", at = @At("HEAD"), cancellable = true)
    public void injectMeditationPose(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                                      float netHeadYaw, float headPitch, CallbackInfo ci) {

        if (!(entity instanceof Player player)) return;

        if (!(player.getVehicle() instanceof MeditationEntity)) return;

        meditationPose(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // Prevent vanilla animation override
        ci.cancel();
    }

    @Unique
    private void meditationPose(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float breath = (float) Math.sin(ageInTicks * 0.1f) * 0.05f;

        // Posture
        this.body.xRot = breath;
        this.body.yRot = 0;

        // Arms (resting on knees or together)
        this.leftArm.xRot = (float) -Math.toRadians(65) + breath;
        this.leftArm.yRot = (float) -Math.toRadians(10);
        this.leftArm.zRot = (float) Math.toRadians(5);

        this.rightArm.xRot = (float) -Math.toRadians(65) + breath;
        this.rightArm.yRot = (float) Math.toRadians(10);
        this.rightArm.zRot = (float) -Math.toRadians(5);

        // Legs (cross-legged)
        this.leftLeg.xRot = (float) -Math.toRadians(90);
        this.leftLeg.yRot = (float) Math.toRadians(30);
        this.leftLeg.zRot = (float) Math.toRadians(20);

        this.rightLeg.xRot = (float) -Math.toRadians(90);
        this.rightLeg.yRot = (float) -Math.toRadians(30);
        this.rightLeg.zRot = (float) -Math.toRadians(20);

        // Head - calm tilt
        this.head.xRot = (float) Math.toRadians(10);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);

        this.crouching = false;

        // Sync second skin layer parts
        this.hat.copyFrom(this.head);
        this.jacket.copyFrom(this.body);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
    }
}
