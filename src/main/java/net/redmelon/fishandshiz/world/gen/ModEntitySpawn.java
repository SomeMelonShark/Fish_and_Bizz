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
import net.redmelon.fishandshiz.entity.custom.MudCrabEntity;
import net.redmelon.fishandshiz.entity.custom.VolcanoSnailEntity;

public class ModEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_AMBIENT,
                ModEntities.ANGELFISH, 5, 2, 4);

        SpawnRestriction.register(ModEntities.ANGELFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_AMBIENT,
                ModEntities.ANGELFISH_FRY, 1, 4, 6);

        SpawnRestriction.register(ModEntities.ANGELFISH_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_AMBIENT,
                ModEntities.ANGELFISH_EGG, 1, 1, 3);

        SpawnRestriction.register(ModEntities.ANGELFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_AMBIENT,
                ModEntities.NEON_TETRA, 6, 5, 9);

        SpawnRestriction.register(ModEntities.NEON_TETRA, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.MILKFISH, 1, 7, 15);

        SpawnRestriction.register(ModEntities.MILKFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH), SpawnGroup.WATER_AMBIENT,
                ModEntities.MILKFISH_EGG, 1, 3, 5);

        SpawnRestriction.register(ModEntities.MILKFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_AMBIENT,
                ModEntities.CORYDORAS, 4, 4, 8);

        SpawnRestriction.register(ModEntities.CORYDORAS, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_AMBIENT,
                ModEntities.SALMON_EGG, 2, 4, 7);

        SpawnRestriction.register(ModEntities.SALMON_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_AMBIENT,
                ModEntities.OSCAR, 1, 1, 2);

        SpawnRestriction.register(ModEntities.OSCAR, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_AMBIENT,
                ModEntities.SALMON_FRY, 1, 4, 6);

        SpawnRestriction.register(ModEntities.SALMON_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.MILKFISH_FRY, 1, 3, 5);

        SpawnRestriction.register(ModEntities.MILKFISH_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP, BiomeKeys.SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.ARCHERFISH, 1, 3, 5);

        SpawnRestriction.register(ModEntities.ARCHERFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.CLOWNFISH, 1, 6, 8);

        SpawnRestriction.register(ModEntities.CLOWNFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BAMBOO_JUNGLE), SpawnGroup.WATER_AMBIENT,
                ModEntities.RAINBOWFISH, 1, 3, 5);

        SpawnRestriction.register(ModEntities.RAINBOWFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA), SpawnGroup.WATER_AMBIENT,
                ModEntities.AURATUS, 5, 2, 4);

        SpawnRestriction.register(ModEntities.AURATUS, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), SpawnGroup.WATER_AMBIENT,
                ModEntities.GRAYLING, 1, 1, 4);

        SpawnRestriction.register(ModEntities.GRAYLING, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER, BiomeKeys.TAIGA), SpawnGroup.WATER_AMBIENT,
                ModEntities.AMUR_CARP, 1, 2, 5);

        SpawnRestriction.register(ModEntities.AMUR_CARP, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelfishEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE), SpawnGroup.AMBIENT,
                ModEntities.CAPYBARA, 1, 4, 7);

        SpawnRestriction.register(ModEntities.CAPYBARA, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.MUD_CRAB, 2, 1, 4);

        SpawnRestriction.register(ModEntities.MUD_CRAB, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MudCrabEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN, BiomeKeys.OCEAN, BiomeKeys.FROZEN_OCEAN), SpawnGroup.WATER_CREATURE,
                ModEntities.LION_MANE_JELLYFISH, 20, 1, 3);

        SpawnRestriction.register(ModEntities.LION_MANE_JELLYFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.VOLCANO_SNAIL, 5, 1, 2);

        SpawnRestriction.register(ModEntities.VOLCANO_SNAIL, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VolcanoSnailEntity::canSpawn);

    }
}
