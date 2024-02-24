package net.redmelon.fishandshiz.world.biome;

import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.world.ModPlacedFeatures;

public class ModBiomes {
    public static final RegistryKey<Biome> JUNGLE_BASIN = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(FishAndShiz.MOD_ID, "jungle_basin"));

    public static void bootstrap(Registerable<Biome> context) {
        context.register(JUNGLE_BASIN, jungleBasin(context));
    }

    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    public static Biome jungleBasin(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addDefaultDisks(biomeBuilder);
        DefaultBiomeFeatures.addJungleTrees(biomeBuilder);

        biomeBuilder.feature(GenerationStep.Feature.LAKES, ModPlacedFeatures.LAKE);
        DefaultBiomeFeatures.addExtraDefaultFlowers(biomeBuilder);
        DefaultBiomeFeatures.addJungleGrass(biomeBuilder);
        DefaultBiomeFeatures.addLargeFerns(biomeBuilder);

        DefaultBiomeFeatures.addDefaultMushrooms(biomeBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder);
        biomeBuilder.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.PLACED_CULTURE_FEED);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.9f)
                .temperature(0.95f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x381400)
                        .waterFogColor(0xfa7900)
                        .skyColor(0x22a1e6)
                        .grassColor(0x612200)
                        .fogColor(0x22a1e6)
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_SPARSE_JUNGLE)).build())
                .build();
    }
}
