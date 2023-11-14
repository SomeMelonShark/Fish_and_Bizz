package net.redmelon.fishandshiz;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.block.entity.ModBlockEntities;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.*;
import net.redmelon.fishandshiz.item.ModItemGroup;
import net.redmelon.fishandshiz.item.ModItems;
import net.redmelon.fishandshiz.sound.ModSounds;
import net.redmelon.fishandshiz.util.ModLootTableModifiers;
import net.redmelon.fishandshiz.villager.ModVillagers;
import net.redmelon.fishandshiz.world.ModConfiguredFeatures;
import net.redmelon.fishandshiz.world.ModFeatures;
import net.redmelon.fishandshiz.world.ModPlacedFeatures;
import net.redmelon.fishandshiz.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class FishAndShiz implements ModInitializer {
	public static final String MOD_ID = "fishandshiz";
	public static final Logger LOGGER = LoggerFactory.getLogger("fishandshiz");

	@Override
	public void onInitialize() {
		ModItems.registerModitems();
		ModItems.init();
		ModItemGroup.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerAllBlockEntities();
		ModSounds.registerSounds();

		ModVillagers.registerVillagers();
		ModVillagers.registerTrades();

		ModWorldGeneration.generateModWorldGen();
		ModFeatures.features();
		ModConfiguredFeatures.configuredFeatures();
		ModPlacedFeatures.placedFeatures();

		ModLootTableModifiers.modifyLootTables();

		GeckoLib.initialize();

		FabricDefaultAttributeRegistry.register(ModEntities.ANGELFISH, AngelfishEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ANGELFISH_FRY, AngelfishFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ANGELFISH_EGG, AngelfishEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARCHERFISH, ArcherfishEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MILKFISH, MilkfishEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MILKFISH_FRY, MilkfishFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MILKFISH_EGG, MilkfishEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.NEON_TETRA, NeonTetraEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.NEON_TETRA_FRY, NeonTetraFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.NEON_TETRA_EGG, NeonTetraEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CLOWNFISH, ClownfishEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CLOWNFISH_FRY, ClownfishFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CLOWNFISH_EGG, ClownfishEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CORYDORAS, CorydorasEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CORYDORAS_FRY, CorydorasFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CORYDORAS_EGG, CorydorasEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.OSCAR, OscarEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.OSCAR_FRY, OscarEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.OSCAR_EGG, OscarEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.RAINBOWFISH, RainbowfishEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.RAINBOWFISH_FRY, RainbowfishFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.RAINBOWFISH_EGG, RainbowfishEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.AURATUS, AuratusEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.AURATUS_FRY, AuratusFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.AURATUS_EGG, AuratusEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GRAYLING, GraylingEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GRAYLING_FRY, GraylingFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GRAYLING_EGG, GraylingEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.AMUR_CARP, AmurCarpEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.AMUR_CARP_FRY, AmurCarpFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.AMUR_CARP_EGG, AmurCarpEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SALMON_EGG, SalmonEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SALMON_FRY, SalmonFryEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MUD_CRAB, MudCrabEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MUD_CRAB_LARVA, MudCrabLarvaEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MUD_CRAB_EGG, MudCrabEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.LION_MANE_JELLYFISH, ManeJellyfishEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.VOLCANO_SNAIL, VolcanoSnailEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.VOLCANO_SNAIL_EGG, VolcanoSnailEggEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAPYBARA, CapybaraEntity.setAttributes());
	}
}