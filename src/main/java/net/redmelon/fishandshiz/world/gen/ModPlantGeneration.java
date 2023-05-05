package net.redmelon.fishandshiz.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.redmelon.fishandshiz.world.ModPlacedFeatures;

public class ModPlantGeneration {
    public static void generateFanwort() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.RIVER),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PLACED_FANWORT);
    }

    public static void generateAnemone() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PLACED_SEA_ANEMONE);
    }
}
