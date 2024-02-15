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
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabEntity;
import net.redmelon.fishandshiz.entity.custom.VolcanoSnailEntity;
import net.redmelon.fishandshiz.world.biome.ModBiomes;

public class ModEntitySpawn {
    public static void addEntitySpawn() {

        // Angelfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.ANGELFISH, 20, 2, 4);

        SpawnRestriction.register(ModEntities.ANGELFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.ANGELFISH_FRY, 1, 4, 6);

        SpawnRestriction.register(ModEntities.ANGELFISH_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.ANGELFISH_EGG, 1, 1, 3);

        SpawnRestriction.register(ModEntities.ANGELFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Neon Tetra Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.NEON_TETRA, 60, 5, 9);

        SpawnRestriction.register(ModEntities.NEON_TETRA, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.NEON_TETRA_FRY, 1, 7, 9);

        SpawnRestriction.register(ModEntities.NEON_TETRA_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.NEON_TETRA_EGG, 1, 1, 2);

        SpawnRestriction.register(ModEntities.NEON_TETRA_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Milkfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.MILKFISH, 1, 7, 15);

        SpawnRestriction.register(ModEntities.MILKFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.MILKFISH_FRY, 1, 3, 5);

        SpawnRestriction.register(ModEntities.MILKFISH_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH), SpawnGroup.WATER_AMBIENT,
                ModEntities.MILKFISH_EGG, 1, 3, 5);

        SpawnRestriction.register(ModEntities.MILKFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        // Corydoras Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.CORYDORAS, 30, 4, 8);

        SpawnRestriction.register(ModEntities.CORYDORAS, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.CORYDORAS_EGG, 1, 1, 2);

        SpawnRestriction.register(ModEntities.CORYDORAS_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Salmon Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_AMBIENT,
                ModEntities.SALMON_FRY, 1, 4, 6);

        SpawnRestriction.register(ModEntities.SALMON_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.WATER_AMBIENT,
                ModEntities.SALMON_EGG, 2, 4, 7);

        SpawnRestriction.register(ModEntities.SALMON_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        // Oscar Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE), SpawnGroup.WATER_CREATURE,
                ModEntities.OSCAR, 30, 1, 2);

        SpawnRestriction.register(ModEntities.OSCAR, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Archerfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP, BiomeKeys.SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.ARCHERFISH, 1, 3, 5);

        SpawnRestriction.register(ModEntities.ARCHERFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Clownfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.CLOWNFISH, 2, 6, 8);

        SpawnRestriction.register(ModEntities.CLOWNFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.CLOWNFISH_EGG, 1, 1, 2);

        SpawnRestriction.register(ModEntities.CLOWNFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        // Rainbowfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BAMBOO_JUNGLE), SpawnGroup.WATER_AMBIENT,
                ModEntities.RAINBOWFISH, 1, 3, 5);

        SpawnRestriction.register(ModEntities.RAINBOWFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        // Auratus Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA), SpawnGroup.WATER_AMBIENT,
                ModEntities.AURATUS, 5, 2, 4);

        SpawnRestriction.register(ModEntities.AURATUS, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA), SpawnGroup.WATER_AMBIENT,
                ModEntities.AURATUS_EGG, 1, 2, 4);

        SpawnRestriction.register(ModEntities.AURATUS_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Grayling Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), SpawnGroup.WATER_AMBIENT,
                ModEntities.GRAYLING, 2, 1, 4);

        SpawnRestriction.register(ModEntities.GRAYLING, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER), SpawnGroup.WATER_AMBIENT,
                ModEntities.GRAYLING_EGG, 1, 3, 7);

        SpawnRestriction.register(ModEntities.GRAYLING_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        // Amur Carp Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER, BiomeKeys.TAIGA), SpawnGroup.WATER_AMBIENT,
                ModEntities.AMUR_CARP, 1, 2, 5);

        SpawnRestriction.register(ModEntities.AMUR_CARP, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        //Betta Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.PLAINS), SpawnGroup.WATER_AMBIENT,
                ModEntities.BETTA, 2, 1, 2);

        SpawnRestriction.register(ModEntities.BETTA, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.PLAINS), SpawnGroup.WATER_AMBIENT,
                ModEntities.BETTA_FRY, 1, 1, 3);

        SpawnRestriction.register(ModEntities.BETTA_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.PLAINS), SpawnGroup.WATER_AMBIENT,
                ModEntities.BETTA_EGG, 1, 1, 1);

        SpawnRestriction.register(ModEntities.BETTA_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        //Red Tail Catfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_CREATURE,
                ModEntities.RED_TAIL_CATFISH, 10, 1, 1);

        SpawnRestriction.register(ModEntities.RED_TAIL_CATFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Capybara Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE), SpawnGroup.AMBIENT,
                ModEntities.CAPYBARA, 1, 4, 7);

        SpawnRestriction.register(ModEntities.CAPYBARA, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        // Mud Crab Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.MUD_CRAB, 2, 1, 4);

        SpawnRestriction.register(ModEntities.MUD_CRAB, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MudCrabEntity::canSpawn);

        // Crayfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.CRAYFISH, 1, 1, 2);

        SpawnRestriction.register(ModEntities.CRAYFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MudCrabEntity::canSpawn);

        // Lione's Mane Jellyfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN, BiomeKeys.OCEAN, BiomeKeys.FROZEN_OCEAN), SpawnGroup.WATER_CREATURE,
                ModEntities.LION_MANE_JELLYFISH, 20, 1, 3);

        SpawnRestriction.register(ModEntities.LION_MANE_JELLYFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        // Volcano Snail Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.VOLCANO_SNAIL, 5, 1, 2);

        SpawnRestriction.register(ModEntities.VOLCANO_SNAIL, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VolcanoSnailEntity::canSpawn);

    }
}
