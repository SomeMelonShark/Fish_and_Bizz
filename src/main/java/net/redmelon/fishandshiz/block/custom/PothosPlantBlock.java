package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.redmelon.fishandshiz.block.ModBlocks;

public class PothosPlantBlock extends PlantBlock implements Fertilizable{
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 2, 15.0);
    public PothosPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world instanceof ServerWorld && entity instanceof BoatEntity) {
            world.breakBlock(new BlockPos(pos), true, entity);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            FluidState fluidState = world.getFluidState(pos.down());
            if (fluidState.getFluid() == Fluids.WATER || state.getBlock() instanceof IceBlock) {
                BlockPos adjacentPos = pos.down();
                BlockState adjacentBlockState = ModBlocks.POTHOS_ROOT_CAP.getDefaultState();
                world.setBlockState(adjacentPos, adjacentBlockState, 3);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        double growthChance = 1;
        if (random.nextDouble() < growthChance) {
            BlockPos adjacentPos = pos.down();
            FluidState fluidState = world.getFluidState(adjacentPos);
            BlockState adjacentBlockState1 = world.getBlockState(adjacentPos);

            if (fluidState.isOf(Fluids.WATER) && !adjacentBlockState1.isOf(ModBlocks.POTHOS_ROOTS)) {
                BlockState adjacentBlockState2 = ModBlocks.POTHOS_ROOT_CAP.getDefaultState();
                world.setBlockState(adjacentPos, adjacentBlockState2, 3);
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());
        return (fluidState.getFluid() == Fluids.WATER || floor.getBlock() instanceof IceBlock) && fluidState2.getFluid() == Fluids.EMPTY || floor.isIn(BlockTags.DIRT);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        PothosPlantBlock.dropStack(world, pos, new ItemStack(this));
    }
}
