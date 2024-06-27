package vowxky.enchantednature.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import vowxky.enchantednature.EnchantedNature;

public class EnchantedNatureParticles {
    public static final DefaultParticleType BIRD_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(EnchantedNature.MOD_ID, "bird_particle"),
                BIRD_PARTICLE);
    }
}
