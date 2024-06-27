package vowxky.enchantednature.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import vowxky.enchantednature.particle.BirdParticle;
import vowxky.enchantednature.particle.EnchantedNatureParticles;

public class EnchantedNatureClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(EnchantedNatureParticles.BIRD_PARTICLE, BirdParticle.BirdParticleFactory::new);
    }
}
