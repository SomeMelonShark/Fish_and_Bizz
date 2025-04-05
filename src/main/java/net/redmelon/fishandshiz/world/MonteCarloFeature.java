package net.redmelon.fishandshiz.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.block.custom.AnubiasBlock;
import net.redmelon.fishandshiz.block.custom.FanwortBlock;
import net.redmelon.fishandshiz.block.custom.MonteCarloBlock;

public class MonteCarloFeature extends Feature<DefaultFeatureConfig> {
    public MonteCarloFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        WorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        BlockState blockState = ModBlocks.MONTE_CARLO.getDefaultState();

        int placedPlants = 0;
        int patchSize = 3 + random.nextInt(8);

        for (int i = 0; i < patchSize; i++) {
            BlockPos plantPos = origin.add(
                    random.nextInt(3) - 1,
                    0,
                    random.nextInt(3) - 1
            );

            if (world.getBlockState(plantPos).isOf(Blocks.WATER) &&
                    world.getBlockState(plantPos.down()).isSolidBlock(world, plantPos.down())) {

                world.setBlockState(plantPos, blockState.with(MonteCarloBlock.AGE, random.nextInt(3)), Block.NOTIFY_LISTENERS);
                placedPlants++;
            }
        }

        return placedPlants > 0;
    }
}
