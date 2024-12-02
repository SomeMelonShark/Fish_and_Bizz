package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.redmelon.fishandshiz.cclass.FishNitrogenAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnubiasBlock extends PlantBlock implements Fertilizable {
    public static final int MAX_AGE = 2;
    public static final IntProperty AGE = IntProperty.of("age", 0, 2);
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 5, 15.0);

    public AnubiasBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP) && !floor.isOf(Blocks.MAGMA_BLOCK);
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 2;
    }

    public int getAge(BlockState state) {
        return (Integer)state.get(this.getAgeProperty());
    }

    public BlockState withAge(int age) {
        return (BlockState)this.getDefaultState().with(this.getAgeProperty(), age);
    }

    public final boolean isMature(BlockState blockState) {
        return this.getAge(blockState) >= this.getMaxAge();
    }

    public boolean hasRandomTicks(BlockState state) {
        return !this.isMature(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8 ? super.getPlacementState(ctx) : null;
    }

    public int getNitrogenDecreaseAmount() {
        return 2;
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

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState blockState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        if (!blockState.isAir()) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return blockState;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMature(state);
    }

    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 1, 2);
    }

    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getGrowthAmount(world);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        world.setBlockState(pos, this.withAge(i), 2);
    }

    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getStill(false);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.applyGrowth(world, pos, state);
    }

    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getStackInHand(hand);
        if (heldItem.getItem() instanceof ShearsItem) {
            int age = state.get(AGE);

            if (age == 2) {
                if (!world.isClient) {
                    dropStack(world, pos, new ItemStack(this.asItem()));
                }

                world.setBlockState(pos, state.with(AGE, 0), Block.NOTIFY_LISTENERS);

                world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.3f);

                if (!player.getAbilities().creativeMode) {
                    heldItem.damage(1, player, p -> p.sendToolBreakStatus(hand));
                }

                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}

