package net.redmelon.fishandshiz.world;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.KelpFeature;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.cclass.FanwortFeature;

import static net.redmelon.fishandshiz.FishAndShiz.MOD_ID;

public class ModFeatures {

    public static final Feature<DefaultFeatureConfig> FANWORT;

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, new Identifier(MOD_ID, name), feature);
    }

    static {
        FANWORT = register("fanwort", new FanwortFeature(DefaultFeatureConfig.CODEC));
    }
}
