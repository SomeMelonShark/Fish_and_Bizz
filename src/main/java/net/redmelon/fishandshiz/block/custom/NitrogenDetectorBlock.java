package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.redmelon.fishandshiz.block.entity.ModBlockEntities;
import net.redmelon.fishandshiz.block.entity.NitrogenDetectorBlockEntity;
import net.redmelon.fishandshiz.block.entity.SeaAnemoneBlockEntity;
import net.redmelon.fishandshiz.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NitrogenDetectorBlock extends WallMountedBlock implements FluidFillable, BlockEntityProvider {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    protected static final VoxelShape CEILING_SHAPE = Block.createCuboidShape(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape FLOOR_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    public static final BooleanProperty UNSTABLE = BooleanProperty.of("unstable");
    public NitrogenDetectorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(UNSTABLE, false).with(FACE, WallMountLocation.WALL));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NitrogenDetectorBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        switch (state.get(FACE)) {
            case FLOOR: {
                if (direction.getAxis() == Direction.Axis.X) {
                    return FLOOR_SHAPE;
                }
                return FLOOR_SHAPE;
            }
            case WALL: {
                return switch (direction) {
                    default -> throw new IncompatibleClassChangeError();
                    case EAST -> {
                        yield EAST_SHAPE;
                    }
                    case WEST -> {
                        yield WEST_SHAPE;
                    }
                    case SOUTH -> {
                        yield SOUTH_SHAPE;
                    }
                    case NORTH, UP, DOWN -> NORTH_SHAPE;
                };
            }
        }
        return CEILING_SHAPE;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        if (!world.isClient) {
            NitrogenDetectorBlockEntity blockEntity = (NitrogenDetectorBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.influenceFromNearbyEntities();
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof NitrogenDetectorBlockEntity) {
                NitrogenDetectorBlockEntity nitrogenBlockEntity = (NitrogenDetectorBlockEntity) blockEntity;
                int nitrogenLevel = nitrogenBlockEntity.nitrogenLevel;
                player.sendMessage(Text.literal("Nitrogen Level: " + nitrogenLevel), false);

                nitrogenBlockEntity.influenceFromNearbyEntities();
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction.getOpposite()));
        if (blockState.isOf(this) && blockState.get(FACING) == direction) {
            return this.getDefaultState().with(FACING, direction.getOpposite());
        }
        if (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) {
            return super.getPlacementState(ctx);
        }
        return this.getDefaultState().with(FACING, direction);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public void reactToNitrogenLevel(World world, BlockPos pos, int nitrogenLevel) {
        int threshold = 800;
        BlockState state = world.getBlockState(pos);
        boolean isThresholdReached = state.get(UNSTABLE);

        if (nitrogenLevel > threshold && !isThresholdReached) {
            world.setBlockState(pos, state.with(UNSTABLE, true), 3);
        } else if (nitrogenLevel <= threshold && isThresholdReached) {
            world.setBlockState(pos, state.with(UNSTABLE, false), 3);
        }
    }

    public boolean isThresholdReached(BlockState state) {
        return state.get(UNSTABLE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UNSTABLE, FACING, FACE);
    }

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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (type == ModBlockEntities.NITROGEN_DETECTOR_ENTITY) {
            return (world1, pos, state1, t) -> {
                if (t instanceof NitrogenDetectorBlockEntity) {
                    NitrogenDetectorBlockEntity.tick(world1, pos, state1, (NitrogenDetectorBlockEntity) t);
                }
            };
        }
        return null;
    }

}
