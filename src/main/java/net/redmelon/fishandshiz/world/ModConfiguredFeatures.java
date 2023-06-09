package net.redmelon.fishandshiz.world;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.cclass.FanwortFeature;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> FANWORT_KEY = registerKey("fanwort");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LAKE = registerKey("lake");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, FANWORT_KEY, Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.FANWORT)));
        register(context, LAKE, Feature.LAKE,
                new LakeFeature.Config(BlockStateProvider.of(Blocks.WATER.getDefaultState()),
                        BlockStateProvider.of(Blocks.DIRT.getDefaultState())));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(FishAndShiz.MOD_ID, name));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
