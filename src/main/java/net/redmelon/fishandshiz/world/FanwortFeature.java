package net.redmelon.fishandshiz.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.block.custom.FanwortBlock;

public class FanwortFeature extends Feature<DefaultFeatureConfig> {
    public FanwortFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        int i = 0;
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();

        int j = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, blockPos.getX(), blockPos.getZ());

        BlockPos blockPos2 = new BlockPos(blockPos.getX(), j, blockPos.getZ());
        if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER)) {
            BlockState blockState = ModBlocks.FANWORT.getDefaultState();
            BlockState blockState2 = ModBlocks.FANWORT_PLANT.getDefaultState();
            int k = 1 + random.nextInt(10);
            for (int l = 0; l <= k; ++l) {
                if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER) && structureWorldAccess.getBlockState(blockPos2.up()).isOf(Blocks.WATER) && blockState2.canPlaceAt(structureWorldAccess, blockPos2)) {
                    if (l == k) {
                        structureWorldAccess.setBlockState(blockPos2, blockState.with(FanwortBlock.AGE, random.nextInt(4) + 20), Block.NOTIFY_LISTENERS);
                        ++i;
                    } else {
                        structureWorldAccess.setBlockState(blockPos2, blockState2, Block.NOTIFY_LISTENERS);
                    }
                } else if (l > 0) {
                    BlockPos blockPos3 = blockPos2.down();
                    if (!blockState.canPlaceAt(structureWorldAccess, blockPos3) || structureWorldAccess.getBlockState(blockPos3.down()).isOf(ModBlocks.FANWORT)) break;
                    structureWorldAccess.setBlockState(blockPos3, blockState.with(FanwortBlock.AGE, random.nextInt(4) + 20), Block.NOTIFY_LISTENERS);
                    ++i;
                    break;
                }
                blockPos2 = blockPos2.up();
            }
        }
        return i > 0;
    }
}
