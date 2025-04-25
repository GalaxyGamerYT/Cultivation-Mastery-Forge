package galaxygameryt.cultivation_mastery.mixin;

import galaxygameryt.cultivation_mastery.entity.custom.SittingEntity;
import galaxygameryt.cultivation_mastery.util.handlers.PlayerMeditationHandler;
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

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
    public void injectMeditationPose(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                                      float netHeadYaw, float headPitch, CallbackInfo ci) {

        if (!(entity instanceof Player player)) return;

        if (!(player.getVehicle() instanceof SittingEntity)) return;

        float progress = PlayerMeditationHandler.meditateProgress.getOrDefault(player.getUUID(), 0f);
        if (progress == 0f) return;

        float breath = (float) Math.sin(ageInTicks * 0.1f) * 0.05f * progress;

        this.body.xRot = cultivation_Mastery_Forge$lerp(0, breath, progress);

        this.leftArm.xRot = cultivation_Mastery_Forge$lerp(this.leftArm.xRot, (float) -Math.toRadians(65), progress) + breath;
        this.leftArm.yRot = cultivation_Mastery_Forge$lerp(this.leftArm.yRot, (float) -Math.toRadians(10), progress);
        this.leftArm.zRot = cultivation_Mastery_Forge$lerp(this.leftArm.zRot, (float) Math.toRadians(5), progress);

        this.rightArm.xRot = cultivation_Mastery_Forge$lerp(this.rightArm.xRot, (float) -Math.toRadians(65), progress) + breath;
        this.rightArm.yRot = cultivation_Mastery_Forge$lerp(this.rightArm.yRot, (float) Math.toRadians(10), progress);
        this.rightArm.zRot = cultivation_Mastery_Forge$lerp(this.rightArm.zRot, (float) -Math.toRadians(5), progress);

        this.leftLeg.xRot = cultivation_Mastery_Forge$lerp(this.leftLeg.xRot, (float) -Math.toRadians(90), progress);
        this.leftLeg.yRot = cultivation_Mastery_Forge$lerp(this.leftLeg.yRot, (float) Math.toRadians(30), progress);
        this.leftLeg.zRot = cultivation_Mastery_Forge$lerp(this.leftLeg.zRot, (float) Math.toRadians(20), progress);

        this.rightLeg.xRot = cultivation_Mastery_Forge$lerp(this.rightLeg.xRot, (float) -Math.toRadians(90), progress);
        this.rightLeg.yRot = cultivation_Mastery_Forge$lerp(this.rightLeg.yRot, (float) -Math.toRadians(30), progress);
        this.rightLeg.zRot = cultivation_Mastery_Forge$lerp(this.rightLeg.zRot, (float) -Math.toRadians(20), progress);

        this.head.xRot = cultivation_Mastery_Forge$lerp(this.head.xRot, (float) Math.toRadians(10), progress);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);

        this.crouching = false;

        this.hat.copyFrom(this.head);
        this.jacket.copyFrom(this.body);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);

        ci.cancel();
    }

    @Unique
    private float cultivation_Mastery_Forge$lerp(float from, float to, float progress) {
        return from + (to - from) * progress;
    }
}
