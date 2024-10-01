package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.cclass.FishNitrogenAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PothosRootBlock extends AbstractPlantStemBlock implements FluidFillable {
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 11.0, 2.0, 14.0, 16.0, 14.0);
    public PothosRootBlock(Settings settings) {
        super(settings, Direction.DOWN, VoxelShapes.fullCube(), true, 0.18);
    }

    public int getNitrogenDecreaseAmount() {
        return 4;
    }

    public void influenceNearbyEntities(World world, BlockPos pos) {
        int searchRadius = 3;
        Box area = new Box(pos.add(-searchRadius, -searchRadius, -searchRadius),
                pos.add(searchRadius, searchRadius, searchRadius));

        List<Entity> nearbyEntities = world.getEntitiesByClass(Entity.class, area,
                entity -> entity instanceof FishNitrogenAccessor);

        for (Entity entity : nearbyEntities) {
            int nitrogenInfluence = getNitrogenInfluence(entity);

            if (entity instanceof FishNitrogenAccessor nitrogenEntity) {
                nitrogenEntity.setNitrogenLevel(nitrogenInfluence - getNitrogenDecreaseAmount());
            }
        }
    }

    private static int getNitrogenInfluence(Entity entity) {
        if (entity instanceof FishNitrogenAccessor nitrogenEntity) {
            return nitrogenEntity.getNitrogenLevel();
        }
        return 0;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        influenceNearbyEntities(world, pos);
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
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
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

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        if (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) {
            return super.getPlacementState(ctx);
        }
        return null;
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

        public int getNitrogenDecreaseAmount() {
            return 4;
        }

        public void influenceNearbyEntities(World world, BlockPos pos) {
            int searchRadius = 3;
            Box area = new Box(pos.add(-searchRadius, -searchRadius, -searchRadius),
                    pos.add(searchRadius, searchRadius, searchRadius));

            List<Entity> nearbyEntities = world.getEntitiesByClass(Entity.class, area,
                    entity -> entity instanceof FishNitrogenAccessor);

            for (Entity entity : nearbyEntities) {
                int nitrogenInfluence = getNitrogenInfluence(entity);

                if (entity instanceof FishNitrogenAccessor nitrogenEntity) {
                    nitrogenEntity.setNitrogenLevel(nitrogenInfluence - getNitrogenDecreaseAmount());
                }
            }
        }

        private static int getNitrogenInfluence(Entity entity) {
            if (entity instanceof FishNitrogenAccessor nitrogenEntity) {
                return nitrogenEntity.getNitrogenLevel();
            }
            return 0;
        }

        @Override
        public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            super.randomTick(state, world, pos, random);
            influenceNearbyEntities(world, pos);
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
