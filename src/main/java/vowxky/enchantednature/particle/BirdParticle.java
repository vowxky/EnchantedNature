package vowxky.enchantednature.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;


public class BirdParticle extends SpriteBillboardParticle {

    private final double directionX;
    private final double directionZ;
    private final double initialX;
    private final double initialZ;
    private static final double MAX_DISTANCE = 50.0;
    protected BirdParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.scale(2F);
        this.maxAge = 140 + world.getRandom().nextInt(41);
        this.setSpriteForAge(spriteProvider);
        this.alpha = 0.0F;
        this.directionX = velocityX;
        this.directionZ = velocityZ;
        this.initialX = x;
        this.initialZ = z;
    }

    @Override
    public void tick() {
        super.tick();
        double distanceTravelled = Math.sqrt(Math.pow(this.x - this.initialX, 2) + Math.pow(this.z - this.initialZ, 2));
        if (this.age++ >= this.maxAge || !isPathClear() || distanceTravelled > MAX_DISTANCE) {
            this.markDead();
        } else {
            this.velocityX = directionX;
            this.velocityY = 0;
            this.velocityZ = directionZ;
            this.move(this.velocityX, this.velocityY, this.velocityZ);

            if (this.age < this.maxAge / 2) {
                this.alpha = 0.5F * ((float) this.age / ((float) this.maxAge / 2));
            } else {
                this.alpha = 0.5F * (1.0F - ((float) (this.age - this.maxAge / 2) / ((float) this.maxAge / 2)));
            }
        }
    }

    private boolean isPathClear() {
        BlockPos nextPos = new BlockPos((int) (this.x + this.velocityX), (int) this.y, (int) (this.z + this.velocityZ));
        return world.getBlockState(nextPos).isAir();
    }
    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class BirdParticleFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public BirdParticleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new BirdParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}