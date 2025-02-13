package net.redmelon.fishandshiz.world;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.*;
import net.redmelon.fishandshiz.FishAndShiz;

import static net.redmelon.fishandshiz.FishAndShiz.LOGGER;
import static net.redmelon.fishandshiz.FishAndShiz.MOD_ID;

public class ModFeatures {
    private static final String FANWORT_NAME = "fanwort";
    private static final String MONTE_CARLO_NAME = "monte_carlo";
    private static final String AMAZON_SWORD_NAME = "amazon_sword";
    private static final String CULTURE_FEED_NAME = "culture_feed";
    private static final String VALLISNERIA_NAME = "vallisneria";
    private static final String ANUBIAS_NAME = "anubias";

    public static final Feature<DefaultFeatureConfig> FANWORT = register(FANWORT_NAME, new FanwortFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> CULTURE_FEED = register(CULTURE_FEED_NAME, new CultureFeedFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> MONTE_CARLO = register(MONTE_CARLO_NAME, new MonteCarloFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> AMAZON_SWORD = register(AMAZON_SWORD_NAME, new AmazonSwordFeature(DefaultFeatureConfig.CODEC));
    public static final VallisneriaFeature VALLISNERIA = register(VALLISNERIA_NAME, new VallisneriaFeature(ProbabilityConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> ANUBIAS = register(ANUBIAS_NAME, new AnubiasFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, new Identifier(MOD_ID, name), feature);
    }

    public static void features(){
        LOGGER.info("Registering Features for " + MOD_ID);
    }
}
