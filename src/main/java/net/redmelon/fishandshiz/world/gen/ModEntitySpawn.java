package net.redmelon.fishandshiz.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.MudCrabEntity;
import net.redmelon.fishandshiz.entity.custom.VolcanoSnailEntity;
import net.redmelon.fishandshiz.world.biome.ModBiomes;

public class ModEntitySpawn {
    public static void addEntitySpawn() {

        // Angelfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.ANGELFISH, 20, 2, 4);

        SpawnRestriction.register(ModEntities.ANGELFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.ANGELFISH_FRY, 1, 4, 6);

        SpawnRestriction.register(ModEntities.ANGELFISH_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.ANGELFISH_EGG, 1, 1, 3);

        SpawnRestriction.register(ModEntities.ANGELFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        // Neon Tetra Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.NEON_TETRA, 60, 5, 9);

        SpawnRestriction.register(ModEntities.NEON_TETRA, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.NEON_TETRA_FRY, 1, 7, 9);

        SpawnRestriction.register(ModEntities.NEON_TETRA_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.NEON_TETRA_EGG, 1, 1, 2);

        SpawnRestriction.register(ModEntities.NEON_TETRA_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        // Milkfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.MILKFISH, 20, 7, 15);

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
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_AMBIENT,
                ModEntities.CORYDORAS_EGG, 1, 1, 2);

        SpawnRestriction.register(ModEntities.CORYDORAS_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

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
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_CREATURE,
                ModEntities.OSCAR, 30, 1, 2);

        SpawnRestriction.register(ModEntities.OSCAR, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        // Archerfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP, BiomeKeys.SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.ARCHERFISH, 1, 3, 5);

        SpawnRestriction.register(ModEntities.ARCHERFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        // Rainbowfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BAMBOO_JUNGLE), SpawnGroup.WATER_AMBIENT,
                ModEntities.RAINBOWFISH, 1, 3, 5);

        SpawnRestriction.register(ModEntities.RAINBOWFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        // Auratus Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA), SpawnGroup.WATER_AMBIENT,
                ModEntities.AURATUS, 5, 2, 4);

        SpawnRestriction.register(ModEntities.AURATUS, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

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
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        // Platy Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP, BiomeKeys.BADLANDS), SpawnGroup.WATER_AMBIENT,
                ModEntities.PLATY, 2, 3, 6);

        SpawnRestriction.register(ModEntities.PLATY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP, BiomeKeys.BADLANDS), SpawnGroup.WATER_AMBIENT,
                ModEntities.PLATY_FRY, 1, 2, 4);

        SpawnRestriction.register(ModEntities.PLATY_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        // Betta Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.PLAINS), SpawnGroup.WATER_AMBIENT,
                ModEntities.BETTA, 2, 1, 2);

        SpawnRestriction.register(ModEntities.BETTA, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.PLAINS), SpawnGroup.WATER_AMBIENT,
                ModEntities.BETTA_FRY, 1, 1, 3);

        SpawnRestriction.register(ModEntities.BETTA_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.PLAINS), SpawnGroup.WATER_AMBIENT,
                ModEntities.BETTA_EGG, 1, 1, 1);

        SpawnRestriction.register(ModEntities.BETTA_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        // Clownfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.CLOWNFISH, 20, 3, 7);

        SpawnRestriction.register(ModEntities.CLOWNFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.CLOWNFISH_EGG, 1, 1, 2);

        SpawnRestriction.register(ModEntities.CLOWNFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Tang Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.TANG, 25, 2, 4);

        SpawnRestriction.register(ModEntities.TANG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.TANG_FRY, 4, 3, 5);

        SpawnRestriction.register(ModEntities.TANG_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Goatfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.BEACH, BiomeKeys.STONY_SHORE), SpawnGroup.WATER_AMBIENT,
                ModEntities.GOATFISH, 15, 5, 7);

        SpawnRestriction.register(ModEntities.GOATFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnLow);

        // Dottyback Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.DOTTYBACK, 15, 1, 2);

        SpawnRestriction.register(ModEntities.DOTTYBACK, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnLow);

        // Marine Angelfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.MARINE_ANGELFISH, 15, 2, 3);

        SpawnRestriction.register(ModEntities.MARINE_ANGELFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnLow);

        // Parrotfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.PARROTFISH, 15, 1, 3);

        SpawnRestriction.register(ModEntities.PARROTFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnLow);

        // Butterflyfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_AMBIENT,
                ModEntities.BUTTERFLYFISH, 25, 4, 7);

        SpawnRestriction.register(ModEntities.BUTTERFLYFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawn);

        // Triggerfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.TRIGGERFISH, 10, 1, 3);

        SpawnRestriction.register(ModEntities.TRIGGERFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnLow);

        // Red Tail Catfish Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiomes.JUNGLE_BASIN), SpawnGroup.WATER_CREATURE,
                ModEntities.RED_TAIL_CATFISH, 10, 1, 1);

        SpawnRestriction.register(ModEntities.RED_TAIL_CATFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SchoolingBreedEntity::canSpawnHigh);

        // Capybara Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, ModBiomes.JUNGLE_BASIN), SpawnGroup.AMBIENT,
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

        // Lion's Mane Jellyfish Spawns
//        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.COLD_OCEAN, BiomeKeys.OCEAN, BiomeKeys.FROZEN_OCEAN), SpawnGroup.WATER_CREATURE,
//                ModEntities.LION_MANE_JELLYFISH, 20, 1, 3);
//
//        SpawnRestriction.register(ModEntities.LION_MANE_JELLYFISH, SpawnRestriction.Location.IN_WATER,
//                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        // Volcano Snail Spawns
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.WATER_AMBIENT,
                ModEntities.VOLCANO_SNAIL, 5, 1, 2);

        SpawnRestriction.register(ModEntities.VOLCANO_SNAIL, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VolcanoSnailEntity::canSpawn);

    }
}
