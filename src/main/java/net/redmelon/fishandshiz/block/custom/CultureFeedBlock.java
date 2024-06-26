package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CultureFeedBlock extends AbstractPlantStemBlock implements FluidDrainable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 12.0, 12.0);
    public CultureFeedBlock(Settings settings) {
        super(settings, Direction.UP, SHAPE, true, 0.2);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.WATER.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    @Override
    protected int getGrowthLength(Random random) {
        return 0;
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return state.isOf(Blocks.MOSS_BLOCK);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());
        return (fluidState.getFluid() == Fluids.WATER && fluidState2.getFluid() == Fluids.WATER);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        if (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) {
            return super.getPlacementState(ctx);
        }
        return null;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.CULTURE_FEED;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        if (type == NavigationType.WATER && !this.collidable) {
            return true;
        }
        return super.canPathfindThrough(state, world, pos, type);
    }

    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, Blocks.WATER.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
        if (!world.isClient()) {
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
        }
        return new ItemStack(ModItems.CULTURE_FEED_BUCKET);
    }

    @Override
    public void grow(ServerWorld world, Random random,BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.offset(this.growthDirection);
        if (this.chooseStemState(world.getBlockState(blockPos))) { // Check if the stem state is suitable
            world.setBlockState(blockPos, state); // Set the neighboring block's state to the current state
        }
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }
}
