package net.redmelon.fishandshiz.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.NoiseBasedCountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.ModBlocks;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> PLACED_FANWORT = registerKey("placed_fanwort");
    public static final RegistryKey<PlacedFeature> PLACED_VALLISNERIA = registerKey("placed_vallisneria");
    public static final RegistryKey<PlacedFeature> LAKE = registerKey("lake");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup =
                context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> reference1 =
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.FANWORT_KEY);
        register(context, PLACED_FANWORT, reference1,
                NoiseBasedCountPlacementModifier.of(80, 80.0, 0.0),
                SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> reference2 =
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.VALLISNERIA_KEY);
        register(context, PLACED_VALLISNERIA, reference2,
                NoiseBasedCountPlacementModifier.of(80, 80.0, 0.0),
                SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> reference3 =
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.LAKE);
        register(context, LAKE, reference3,
                NoiseBasedCountPlacementModifier.of(80, 80.0, 0.0),
                SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }
    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(FishAndShiz.MOD_ID, name));

    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                 RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context,
                                                                                   RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    public static void placedFeatures() {
        FishAndShiz.LOGGER.info("Registering Placed Features for "+ FishAndShiz.MOD_ID);
    }
}
