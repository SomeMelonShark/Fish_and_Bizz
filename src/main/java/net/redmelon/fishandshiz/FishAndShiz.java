package net.redmelon.fishandshiz;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.block.entity.ModBlockEntities;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.*;
import net.redmelon.fishandshiz.entity.custom.fish.*;
import net.redmelon.fishandshiz.entity.variant.*;
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

public class 	FishAndShiz implements ModInitializer {
	public static final String MOD_ID = "fishandshiz";
	public static final Logger LOGGER = LoggerFactory.getLogger("fishandshiz");

	@Override
	public void onInitialize() {
		ModItems.registerModitems();
		ModItems.init();
		ModItemGroup.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModEntities.setAttributes();
		ModBlockEntities.registerAllBlockEntities();
		ModSounds.registerSounds();

		ModVariants.initializeVariants();

		ModVillagers.registerVillagers();
		ModVillagers.registerTrades();

		ModWorldGeneration.generateModWorldGen();
		ModFeatures.features();
		ModConfiguredFeatures.configuredFeatures();
		ModPlacedFeatures.placedFeatures();

		ModLootTableModifiers.modifyLootTables();

		GeckoLib.initialize();
	}
}