package net.redmelon.fishandshiz.world;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.ModBlocks;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> FANWORT_KEY = registerKey("fanwort");
    public static final RegistryKey<ConfiguredFeature<?, ?>> VALLISNERIA_KEY = registerKey("vallisneria");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MONTE_CARLO_KEY = registerKey("monte_carlo");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LAKE = registerKey("lake");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        ConfiguredFeatures.register(context, FANWORT_KEY, ModFeatures.FANWORT);
        ConfiguredFeatures.register(context, MONTE_CARLO_KEY, ModFeatures.MONTE_CARLO);
        ConfiguredFeatures.register(context, VALLISNERIA_KEY, ModFeatures.VALLISNERIA, new ProbabilityConfig(0.8F));
        register(context, LAKE, Feature.LAKE,
                new LakeFeature.Config(BlockStateProvider.of(Blocks.WATER.getDefaultState()),
                        BlockStateProvider.of(ModBlocks.MULM.getDefaultState())));
    }

    public static void configuredFeatures() {
        FishAndShiz.LOGGER.info("Registering Configured Features for "+ FishAndShiz.MOD_ID);
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(FishAndShiz.MOD_ID, name));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
