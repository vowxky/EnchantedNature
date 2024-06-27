package vowxky.enchantednature;

import net.fabricmc.api.ModInitializer;
import vowxky.enchantednature.particle.EnchantedNatureParticles;

public class EnchantedNature implements ModInitializer {
    public static final String MOD_ID = "enchantednature";
    @Override
    public void onInitialize() {
        EnchantedNatureParticles.registerParticles();
    }
}
