package galaxygameryt.cultivation_mastery.particles.custom;

import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class QiParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected QiParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z);
        this.sprites = sprites;
        Random random = new Random();

//        this.xd = vx;
//        this.yd = vy;
//        this.zd = vz;

        this.lifetime = 40 + random.nextInt(20);
        this.gravity = 0.0f;

        this.quadSize = 0.1f;
        this.rCol = 0.4f;
        this.gCol = 0.2f;
        this.bCol = 1.0f;
        this.alpha = 0.9f;

        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
//        this.alpha *= 0.96f;
//        this.quadSize *= 0.98f;
//        this.quadSize *= 1.02f;
        this.alpha *= 0.5f;

        if (this.age % 2 == 0) {
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level,
                                       double x, double y, double z,
                                       double vx, double vy, double vz) {
//            Logger.info("[DEBUG] Creating QiParticle with sprite: " + (sprite == null ? "NULL" : "OK"));
            QiParticle particle = new QiParticle(level, x, y, z, sprite);
            particle.pickSprite(sprite);
            return particle;
        }
    }
}
