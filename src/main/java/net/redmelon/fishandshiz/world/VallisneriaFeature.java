package net.redmelon.fishandshiz.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.block.custom.TallVallisneriaBlock;

public class VallisneriaFeature extends Feature<ProbabilityConfig> {
    public VallisneriaFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<ProbabilityConfig> context) {
        boolean bl = false;
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        ProbabilityConfig probabilityConfig = (ProbabilityConfig)context.getConfig();
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
        BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
        if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER)) {
            boolean bl2 = random.nextDouble() < (double)probabilityConfig.probability;
            BlockState blockState = bl2 ? ModBlocks.TALL_VALLISNERIA.getDefaultState() : ModBlocks.VALLISNERIA.getDefaultState();
            if (blockState.canPlaceAt(structureWorldAccess, blockPos2)) {
                if (bl2) {
                    BlockState blockState2 = (BlockState)blockState.with(TallVallisneriaBlock.HALF, DoubleBlockHalf.UPPER);
                    BlockPos blockPos3 = blockPos2.up();
                    if (structureWorldAccess.getBlockState(blockPos3).isOf(Blocks.WATER)) {
                        structureWorldAccess.setBlockState(blockPos2, blockState, 2);
                        structureWorldAccess.setBlockState(blockPos3, blockState2, 2);
                    }
                } else {
                    structureWorldAccess.setBlockState(blockPos2, blockState, 2);
                }

                bl = true;
            }
        }

        return bl;
    }
}
