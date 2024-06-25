package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.redmelon.fishandshiz.block.ModBlocks;

public class PothosRootBlock extends AbstractPlantStemBlock implements FluidFillable {
    public PothosRootBlock(Settings settings) {
        super(settings, Direction.DOWN, VoxelShapes.fullCube(), true, 0.18);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return state.isOf(Blocks.WATER);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.POTHOS_ROOTS;
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    @Override
    protected int getGrowthLength(Random random) {
        return 1;
    }


    public static class PothosRootsBlock
            extends AbstractPlantBlock
            implements FluidFillable {
        public PothosRootsBlock(Settings settings) {
            super(settings, Direction.DOWN, VoxelShapes.fullCube(), true);
        }

        @Override
        protected AbstractPlantStemBlock getStem() {
            return (AbstractPlantStemBlock) ModBlocks.POTHOS_ROOT_CAP;
        }

        @Override
        public FluidState getFluidState(BlockState state) {
            return Fluids.WATER.getStill(false);
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
}
