package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
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
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.redmelon.fishandshiz.block.entity.ModBlockEntities;
import net.redmelon.fishandshiz.block.entity.NitrogenDetectorBlockEntity;
import net.redmelon.fishandshiz.block.entity.SeaAnemoneBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NitrogenDetectorBlock extends FacingBlock implements FluidFillable, BlockEntityProvider {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    public static final BooleanProperty UNSTABLE = BooleanProperty.of("unstable");
    public NitrogenDetectorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(UNSTABLE, false));
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NitrogenDetectorBlockEntity(pos, state);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
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

    public void reactToNitrogenLevel(World world, BlockPos pos, int nitrogenLevel) {
        int threshold = 1000;
        BlockState state = world.getBlockState(pos);
        boolean isThresholdReached = state.get(UNSTABLE);

        if (nitrogenLevel > threshold && !isThresholdReached) {
            world.setBlockState(pos, state.with(UNSTABLE, true), 3);
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat() * 0.2f + 0.8f);
        } else if (nitrogenLevel <= threshold && isThresholdReached) {
            world.setBlockState(pos, state.with(UNSTABLE, false), 3);
        }
    }

    public boolean isThresholdReached(BlockState state) {
        return state.get(UNSTABLE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UNSTABLE);
        builder.add(FACING);
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
