package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.redmelon.fishandshiz.block.ModBlocks;

public class LilyPadStemBlock extends AbstractPlantStemBlock implements FluidFillable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(6.5, 0.0, 6.5, 11.0, 16.0, 11.0);
    public LilyPadStemBlock(Settings settings) {
        super(settings, Direction.DOWN, SHAPE, true, 9000);
    }

    @Override
    protected int getGrowthLength(Random random) {
        return 1;
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return state.isOf(Blocks.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.LILY_PAD_STEM;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        if (type == NavigationType.WATER && !this.collidable) {
            return true;
        }
        return super.canPathfindThrough(state, world, pos, type);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.offset(this.growthDirection);
        if (this.chooseStemState(world.getBlockState(blockPos))) { // Check if the stem state is suitable
            world.setBlockState(blockPos, state); // Set the neighboring block's state to the current state
        }
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }
}
