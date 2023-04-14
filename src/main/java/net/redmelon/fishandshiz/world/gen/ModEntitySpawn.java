package net.redmelon.fishandshiz.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.redmelon.fishandshiz.entity.ModEntities;

public class ModEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_CREATURE,
                ModEntities.ANGELFISH, 40, 5, 8);

        SpawnRestriction.register(ModEntities.ANGELFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_CREATURE,
                ModEntities.ANGELFISH_FRY, 10, 6, 9);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_CREATURE,
                ModEntities.ANGELFISH_EGG, 2, 1, 3);

        SpawnRestriction.register(ModEntities.ANGELFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        SpawnRestriction.register(ModEntities.ANGELFISH_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.LUKEWARM_OCEAN), SpawnGroup.WATER_CREATURE,
                ModEntities.MILKFISH, 30, 8, 15);

        SpawnRestriction.register(ModEntities.MILKFISH, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH), SpawnGroup.WATER_CREATURE,
                ModEntities.MILKFISH_EGG, 2, 10, 15);

        SpawnRestriction.register(ModEntities.MILKFISH_EGG, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP), SpawnGroup.WATER_CREATURE,
                ModEntities.MILKFISH_FRY, 10, 8, 12);

        SpawnRestriction.register(ModEntities.MILKFISH_FRY, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);
    }
}
