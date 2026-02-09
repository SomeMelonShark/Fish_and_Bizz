package net.redmelon.fishandshiz.world.biome;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.world.ModPlacedFeatures;

public class TerrestriaCompat {

    public static RegistryKey<Biome> CYPRESS_SWAMP = RegistryKey.of(RegistryKeys.BIOME, new Identifier("terrestria", "cypress_swamp"));
    public static RegistryKey<Biome> OASIS = RegistryKey.of(RegistryKeys.BIOME, new Identifier("terrestria", "oasis"));
    public static RegistryKey<Biome> SAKURA_FOREST = RegistryKey.of(RegistryKeys.BIOME, new Identifier("terrestria", "sakura_forest"));
    public static RegistryKey<Biome> JAPANESE_MAPLE_FOREST = RegistryKey.of(RegistryKeys.BIOME, new Identifier("terrestria", "japanese_maple_forest"));
    public static RegistryKey<Biome> VOLCANIC_ISLAND = RegistryKey.of(RegistryKeys.BIOME, new Identifier("terrestria", "volcanic_island"));
    public static RegistryKey<Biome> HEMLOCK_RAINFOREST = RegistryKey.of(RegistryKeys.BIOME, new Identifier("terrestria", "hemlock_rainforest"));
    public static RegistryKey<Biome> CANYON = RegistryKey.of(RegistryKeys.BIOME, new Identifier("terrestria", "canyon"));

    public static void injectStuff() {
        if (FabricLoader.getInstance().isModLoaded("terrestria")) {
            // Cypress Swamp
            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(CYPRESS_SWAMP),
                    GenerationStep.Feature.VEGETAL_DECORATION,
                    ModPlacedFeatures.PLACED_VALLISNERIA
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(CYPRESS_SWAMP),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.BETTA,
                    2, 1, 2
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(CYPRESS_SWAMP),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.BETTA_EGG,
                    1, 1, 1
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(CYPRESS_SWAMP),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.CRAYFISH,
                    1, 1, 2
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(CYPRESS_SWAMP),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.RAMSHORN_SNAIL,
                    3, 2, 4
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(CYPRESS_SWAMP),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.RAMSHORN_SNAIL,
                    1, 1, 1
            );

            // Oasis
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(OASIS),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.AURATUS,
                    5, 2, 4
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(OASIS),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.KILLIFISH,
                    4, 1, 3
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(OASIS),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.KILLIFISH_EGG,
                    4, 1, 3
            );

            // Sakura Forest
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(SAKURA_FOREST),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.AMUR_CARP,
                    2, 2, 5
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(SAKURA_FOREST),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.AMUR_CARP_EGG,
                    1, 1, 1
            );

            // Japanese Maple Forest
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(JAPANESE_MAPLE_FOREST),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.GOLDFISH,
                    2, 3, 5
            );

            // Volcanic Island
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(VOLCANIC_ISLAND),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.TANG,
                    10, 2, 4
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(VOLCANIC_ISLAND),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.BUTTERFLYFISH,
                    10, 4, 7
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(VOLCANIC_ISLAND),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.MILKFISH_FRY,
                    3, 3, 5
            );

            // Hemlock Rainforest
            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(HEMLOCK_RAINFOREST),
                    GenerationStep.Feature.VEGETAL_DECORATION,
                    ModPlacedFeatures.PLACED_FANWORT
            );
            BiomeModifications.addFeature(
                    BiomeSelectors.includeByKey(HEMLOCK_RAINFOREST),
                    GenerationStep.Feature.VEGETAL_DECORATION,
                    ModPlacedFeatures.PLACED_ANUBIAS
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(CANYON),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.RAMSHORN_SNAIL,
                    2, 2, 4
            );
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(CANYON),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.RAMSHORN_SNAIL_EGG,
                    1, 1, 1
            );

            // Canyon
            BiomeModifications.addSpawn(
                    BiomeSelectors.includeByKey(CANYON),
                    SpawnGroup.WATER_CREATURE,
                    ModEntities.PLATY,
                    2, 3, 6
            );
        }
    }
}
