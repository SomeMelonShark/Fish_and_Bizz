package net.redmelon.fishandshiz;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.redmelon.fishandshiz.data.ModLootTableGenerator;
import net.redmelon.fishandshiz.data.ModModelProvider;
import net.redmelon.fishandshiz.data.ModRecipeGenerator;
import net.redmelon.fishandshiz.data.ModWorldGenerator;
import net.redmelon.fishandshiz.world.ModConfiguredFeatures;
import net.redmelon.fishandshiz.world.ModPlacedFeatures;
import net.redmelon.fishandshiz.world.biome.ModBiomes;

public class FishAndShizDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModLootTableGenerator::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeGenerator::new);
		pack.addProvider(ModWorldGenerator::new);
	}
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
//		registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
	}
}
