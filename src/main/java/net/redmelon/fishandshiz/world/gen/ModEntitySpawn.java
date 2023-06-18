package net.redmelon.fishandshiz.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;

public class ModEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_CREATURE,
                ModEntities.ANGELFISH, 80, 5, 8);

        SpawnRestriction.register(ModEntities.ANGELFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_CREATURE,
                ModEntities.ANGELFISH_FRY, 20, 6, 9);

        SpawnRestriction.register(ModEntities.ANGELFISH_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_CREATURE,
                ModEntities.ANGELFISH_EGG, 40, 1, 3);

        SpawnRestriction.register(ModEntities.ANGELFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_CREATURE,
                ModEntities.NEON_TETRA, 100, 9, 15);

        SpawnRestriction.register(ModEntities.NEON_TETRA, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.LUKEWARM_OCEAN), SpawnGroup.WATER_CREATURE,
                ModEntities.MILKFISH, 30, 8, 15);

        SpawnRestriction.register(ModEntities.MILKFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH), SpawnGroup.WATER_CREATURE,
                ModEntities.MILKFISH_EGG, 2, 10, 15);

        SpawnRestriction.register(ModEntities.MILKFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_CREATURE,
                ModEntities.CORYDORAS, 100, 7, 12);

        SpawnRestriction.register(ModEntities.CORYDORAS, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_CREATURE,
                ModEntities.SALMON_EGG, 100, 8, 10);

        SpawnRestriction.register(ModEntities.SALMON_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_CREATURE,
                ModEntities.OSCAR, 30, 1, 2);

        SpawnRestriction.register(ModEntities.OSCAR, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_CREATURE,
                ModEntities.SALMON_FRY, 5, 8, 10);

        SpawnRestriction.register(ModEntities.SALMON_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_CREATURE,
                ModEntities.MILKFISH_FRY, 10, 8, 12);

        SpawnRestriction.register(ModEntities.MILKFISH_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.SWAMP), SpawnGroup.WATER_CREATURE,
                ModEntities.ARCHERFISH, 20, 4, 7);

        SpawnRestriction.register(ModEntities.ARCHERFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_CREATURE,
                ModEntities.CLOWNFISH, 50, 6, 8);

        SpawnRestriction.register(ModEntities.CLOWNFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE), SpawnGroup.CREATURE,
                ModEntities.CAPYBARA, 10, 4, 7);

        SpawnRestriction.register(ModEntities.CAPYBARA, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_CREATURE,
                ModEntities.MUD_CRAB, 10, 4, 7);

        SpawnRestriction.register(ModEntities.MUD_CRAB, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalWaterEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN, BiomeKeys.OCEAN, BiomeKeys.FROZEN_OCEAN), SpawnGroup.WATER_CREATURE,
                ModEntities.LION_MANE_JELLYFISH, 10, 1, 3);

        SpawnRestriction.register(ModEntities.LION_MANE_JELLYFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

    }
}
