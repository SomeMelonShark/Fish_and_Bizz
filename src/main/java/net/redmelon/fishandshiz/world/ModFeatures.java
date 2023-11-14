package net.redmelon.fishandshiz.world;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.redmelon.fishandshiz.FishAndShiz;

import static net.redmelon.fishandshiz.FishAndShiz.LOGGER;
import static net.redmelon.fishandshiz.FishAndShiz.MOD_ID;

public class ModFeatures {
    private static final String FANWORT_NAME = "fanwort";
    private static final String VALLISNERIA_NAME = "vallisneria";

    public static final Feature<DefaultFeatureConfig> FANWORT = register(FANWORT_NAME, new FanwortFeature(DefaultFeatureConfig.CODEC));
    public static final VallisneriaFeature VALLISNERIA = (VallisneriaFeature) register(VALLISNERIA_NAME, new VallisneriaFeature(ProbabilityConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, new Identifier(MOD_ID, name), feature);
    }

    public static void features(){
        LOGGER.info("Registering Features for " + MOD_ID);
    }
}
