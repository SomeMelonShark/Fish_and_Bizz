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

public class CultureFeedFeature extends Feature<DefaultFeatureConfig> {
    public CultureFeedFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        int i = 0;
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();

        int j = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, blockPos.getX(), blockPos.getZ());

        BlockPos blockPos2 = new BlockPos(blockPos.getX(), j, blockPos.getZ());
        if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER)) {
            BlockState blockState = ModBlocks.CULTURE_FEED.getDefaultState();
            int k = 1 + random.nextInt(3);
            for (int l = 0; l <= k; ++l) {
                if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER) && structureWorldAccess.getBlockState(blockPos2.up()).isOf(Blocks.WATER)) {
                    structureWorldAccess.setBlockState(blockPos2, blockState, Block.NOTIFY_LISTENERS);
                    ++i;
                } else if (l > 0) {
                    BlockPos blockPos3 = blockPos2.down();
                    if (!structureWorldAccess.getBlockState(blockPos3).isOf(ModBlocks.CULTURE_FEED)) {
                        structureWorldAccess.setBlockState(blockPos3, blockState, Block.NOTIFY_LISTENERS);
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
