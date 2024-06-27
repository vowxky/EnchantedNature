package vowxky.enchantednature.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vowxky.enchantednature.particle.EnchantedNatureParticles;

import java.util.Random;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Unique
    private final Random random = new Random();

    @Inject(method = "render", at = @At("HEAD"))
    private void renderBirdParticles(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;

        if (world != null && world.getTimeOfDay() < 13000 && world.isSkyVisible(camera.getBlockPos())) {
            Vec3d playerPos = client.player.getPos();
            float yaw = client.player.getYaw(tickDelta);
            double angle = Math.toRadians(yaw);

            double directionX = -Math.sin(angle);
            double directionZ = Math.cos(angle);

            int side = random.nextInt(3);
            double offsetX = 0;
            double offsetZ = 0;
            double movementDirectionX = directionX;
            double movementDirectionZ = directionZ;

            if (side == 1) {
                offsetX = -Math.cos(angle) * 20;
                offsetZ = -Math.sin(angle) * 20;
                movementDirectionX = Math.cos(angle);
                movementDirectionZ = Math.sin(angle);
            } else if (side == 2) {
                offsetX = Math.cos(angle) * 20;
                offsetZ = Math.sin(angle) * 20;
                movementDirectionX = -Math.cos(angle);
                movementDirectionZ = -Math.sin(angle);
            }

            long spawnInterval = 60 * 20 + random.nextInt(5 * 60 * 20);

            if (limitTime % spawnInterval == 0) {
                double spawnOffsetX = movementDirectionX * -10;
                double spawnOffsetZ = movementDirectionZ * -10;

                if (playerPos.y <= 100) {
                    RegistryKey<Biome> biome = world.getBiome(BlockPos.ofFloored(playerPos)).getKey().get();

                    if (biome == BiomeKeys.PLAINS ||
                            biome == BiomeKeys.TAIGA ||
                            biome == BiomeKeys.SWAMP ||
                            biome == BiomeKeys.JUNGLE ||
                            biome == BiomeKeys.SAVANNA ||
                            biome == BiomeKeys.DESERT ||
                            biome == BiomeKeys.BEACH ||
                            biome == BiomeKeys.OCEAN ||
                            biome == BiomeKeys.RIVER) {
                        double flockCenterX = playerPos.x + directionX * 40 + (random.nextDouble() * 20 - 10) + offsetX + spawnOffsetX;
                        double flockCenterY = playerPos.y + (random.nextDouble() * 10 + 15);
                        double flockCenterZ = playerPos.z + directionZ * 40 + (random.nextDouble() * 20 - 10) + offsetZ + spawnOffsetZ;

                        int numberOfBirdsInFlock;
                        int flockShape = random.nextInt(6);

                        switch (flockShape) {
                            case 0:
                                numberOfBirdsInFlock = 10;
                                for (int j = 0; j < numberOfBirdsInFlock; j++) {
                                    double x = flockCenterX + j * 2;
                                    double y = flockCenterY;
                                    double z = flockCenterZ;
                                    double velocityX = movementDirectionX * 0.25;
                                    double velocityY = 0;
                                    double velocityZ = movementDirectionZ * 0.25;

                                    world.addParticle(EnchantedNatureParticles.BIRD_PARTICLE, x, y, z, velocityX, velocityY, velocityZ);
                                }
                                break;
                            case 1:
                                numberOfBirdsInFlock = 15;
                                for (int j = 0; j < numberOfBirdsInFlock; j++) {
                                    double x = flockCenterX + (j % 2 == 0 ? j : -j) * 2;
                                    double y = flockCenterY;
                                    double z = flockCenterZ + j * 2;
                                    double velocityX = movementDirectionX * 0.25;
                                    double velocityY = 0;
                                    double velocityZ = movementDirectionZ * 0.25;

                                    world.addParticle(EnchantedNatureParticles.BIRD_PARTICLE, x, y, z, velocityX, velocityY, velocityZ);
                                }
                                break;
                            case 2:
                                numberOfBirdsInFlock = 12;
                                for (int j = 0; j < numberOfBirdsInFlock; j++) {
                                    double angleOffset = 2 * Math.PI * j / numberOfBirdsInFlock;
                                    double x = flockCenterX + Math.cos(angleOffset) * 10;
                                    double y = flockCenterY;
                                    double z = flockCenterZ + Math.sin(angleOffset) * 10;
                                    double velocityX = movementDirectionX * 0.25;
                                    double velocityY = 0;
                                    double velocityZ = movementDirectionZ * 0.25;

                                    world.addParticle(EnchantedNatureParticles.BIRD_PARTICLE, x, y, z, velocityX, velocityY, velocityZ);
                                }
                                break;
                            case 3:
                                numberOfBirdsInFlock = 15 + random.nextInt(11);
                                for (int j = 0; j < numberOfBirdsInFlock; j++) {
                                    double randomOffsetX = (random.nextDouble() - 0.5) * 10;
                                    double randomOffsetY = (random.nextDouble() - 0.5) * 10;
                                    double randomOffsetZ = (random.nextDouble() - 0.5) * 10;
                                    double x = flockCenterX + randomOffsetX;
                                    double y = flockCenterY + randomOffsetY;
                                    double z = flockCenterZ + randomOffsetZ;
                                    double velocityX = movementDirectionX * 0.25;
                                    double velocityY = 0;
                                    double velocityZ = movementDirectionZ * 0.25;

                                    world.addParticle(EnchantedNatureParticles.BIRD_PARTICLE, x, y, z, velocityX, velocityY, velocityZ);
                                }
                                break;
                            case 4:
                                numberOfBirdsInFlock = 16;
                                for (int j = 0; j < 4; j++) {
                                    for (int k = 0; k < 4; k++) {
                                        double x = flockCenterX + j * 2;
                                        double y = flockCenterY;
                                        double z = flockCenterZ + k * 2;
                                        double velocityX = movementDirectionX * 0.25;
                                        double velocityY = 0;
                                        double velocityZ = movementDirectionZ * 0.25;

                                        world.addParticle(EnchantedNatureParticles.BIRD_PARTICLE, x, y, z, velocityX, velocityY, velocityZ);
                                    }
                                }
                                break;
                            case 5:
                                numberOfBirdsInFlock = 10;
                                for (int j = 0; j < numberOfBirdsInFlock; j++) {
                                    double x = flockCenterX + (j % 5) * 2;
                                    double y = flockCenterY;
                                    double z = flockCenterZ + (j / 5) * 2;
                                    double velocityX = movementDirectionX * 0.25;
                                    double velocityY = 0;
                                    double velocityZ = movementDirectionZ * 0.25;

                                    world.addParticle(EnchantedNatureParticles.BIRD_PARTICLE, x, y, z, velocityX, velocityY, velocityZ);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }
}