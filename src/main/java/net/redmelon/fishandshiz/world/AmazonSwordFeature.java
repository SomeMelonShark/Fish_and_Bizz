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
import net.redmelon.fishandshiz.block.custom.AmazonSwordBlock;
import net.redmelon.fishandshiz.block.custom.MonteCarloBlock;

public class AmazonSwordFeature extends Feature<DefaultFeatureConfig> {
    public AmazonSwordFeature(Codec<DefaultFeatureConfig> codec) {
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
            BlockState blockState = ModBlocks.AMAZON_SWORD.getDefaultState();
            int k = 1 + random.nextInt(10);
            for (int l = 0; l <= k; ++l) {
                if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER) && structureWorldAccess.getBlockState(blockPos2.up()).isOf(Blocks.WATER)) {
                    if (l == k) {
                        structureWorldAccess.setBlockState(blockPos2, blockState.with(AmazonSwordBlock.AGE, random.nextInt(1)), Block.NOTIFY_LISTENERS);
                        ++i;
                    } else if (l > 0) {
                        BlockPos blockPos3 = blockPos2.down();
                        if (!blockState.canPlaceAt(structureWorldAccess, blockPos3) || structureWorldAccess.getBlockState(blockPos3.down()).isOf(ModBlocks.AMAZON_SWORD))
                            break;
                        structureWorldAccess.setBlockState(blockPos3, blockState.with(AmazonSwordBlock.AGE, random.nextInt(1)), Block.NOTIFY_LISTENERS);
                        ++i;
                        break;
                    }
                }
                blockPos2 = blockPos2.up();
            }
        }
        return i > 0;
    }
}
