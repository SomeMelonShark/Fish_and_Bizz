package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.block.entity.FilterBlockEntity;
import net.redmelon.fishandshiz.block.entity.NitrogenDetectorBlockEntity;
import net.redmelon.fishandshiz.cclass.FishNitrogenAccessor;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.redmelon.fishandshiz.block.entity.FilterBlockEntity.MAX_NITROGEN;

public class FilterBlock extends WallMountedBlock implements BlockEntityProvider {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public static final IntProperty NITROGEN_LEVEL = IntProperty.of("nitrogen_level", 0, 3);
    public FilterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(FACE, WallMountLocation.WALL));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NITROGEN_LEVEL, FACING, FACE);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FilterBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FilterBlockEntity filterEntity) {
                ItemStack heldStack = player.getStackInHand(hand);
                Item resetItem = Items.SPONGE;
                Block dropItem = ModBlocks.MULM;

                if (heldStack.getItem() == resetItem && filterEntity.getNitrogenStored() > 0) {
                    if (!player.getAbilities().creativeMode) {
                        heldStack.decrement(1);
                    }

                    ItemStack drop = new ItemStack(dropItem, 1);
                    ItemEntity dropEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, drop);
                    world.spawnEntity(dropEntity);

                    filterEntity.setNitrogenStored(0);
                    BlockState newState = state.with(FilterBlock.NITROGEN_LEVEL, 0);
                    world.setBlockState(pos, newState, Block.NOTIFY_ALL);
                    world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_WORK, SoundCategory.BLOCKS, 1f, 1f);

                    return ActionResult.SUCCESS;
                }

                player.sendMessage(Text.literal("Nitrogen Level: " + filterEntity.getNitrogenStored()), false);
            }
        }

        return ActionResult.SUCCESS;
    }

    public Set<BlockPos> floodFill(World world, BlockPos origin, int maxRange) {
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> toVisit = new LinkedList<>();
        toVisit.add(origin);
        visited.add(origin);

        while (!toVisit.isEmpty()) {
            BlockPos current = toVisit.poll();

            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.offset(dir);
                if (!visited.contains(neighbor) && origin.getManhattanDistance(neighbor) <= maxRange) {
                    if (world.getFluidState(neighbor).isEqualAndStill(Fluids.WATER)) {
                        visited.add(neighbor);
                        toVisit.add(neighbor);
                    }
                }
            }
        }
        return visited;
    }

    public void influenceNearbyEntities(World world, BlockPos pos, BlockState state) {
        Set<BlockPos> waterBody = floodFill(world, pos, 500);
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof FilterBlockEntity filter)) return;

        int drainRate = 100;
        int spaceLeft = MAX_NITROGEN - filter.getNitrogenStored();
        int amountToDrain = Math.min(drainRate, spaceLeft);
        int drained = 0;
        if (filter.isFull()) return;

        Box area = new Box(pos).expand(6);

        List<Entity> entities = world.getEntitiesByClass(Entity.class, area, e -> e instanceof FishNitrogenAccessor);

        for (Entity entity : entities) {
            BlockPos entityPos = entity.getBlockPos();
            if (waterBody.contains(entityPos)) {
                if (!(entity instanceof FishNitrogenAccessor nitrogenEntity)) continue;

                int entityNitrogen = nitrogenEntity.getNitrogenLevel();
                if (entityNitrogen <= 0) continue;

                int drainFromThisEntity = Math.min(amountToDrain - drained, entityNitrogen);
                if (drainFromThisEntity <= 0) break;

                nitrogenEntity.setNitrogenLevel(entityNitrogen - drainFromThisEntity);
                drained += drainFromThisEntity;

                if (drained >= amountToDrain) break;
            }
        }

        if (drained > 0) {
            filter.addNitrogen(drained);

            int VisualLevel = MathHelper.clamp(filter.getNitrogenStored() * 4 / MAX_NITROGEN, 0, 3);
            if (state.get(NITROGEN_LEVEL) != VisualLevel) {
                world.setBlockState(pos, state.with(NITROGEN_LEVEL, VisualLevel), Block.NOTIFY_ALL);
            }
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        BlockPos above = pos.up();
        BlockState aboveState = world.getBlockState(above);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof FilterBlockEntity filter)) return;

        if (aboveState.getFluidState().isStill() && aboveState.getFluidState().isOf(Fluids.WATER)) {
            if (filter.isFull()) {
                world.addParticle(ParticleTypes.BUBBLE,
                        pos.getX() + 0.5 + (random.nextFloat() - 0.5) * 0.3,
                        pos.getY() + 1.0,
                        pos.getZ() + 0.5 + (random.nextFloat() - 0.5) * 0.3,
                        0.0, 0.1, 0.0);
            } else {
                world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP,
                        pos.getX() + 0.5 + (random.nextFloat() - 0.5) * 0.3,
                        pos.getY() + 1.0,
                        pos.getZ() + 0.5 + (random.nextFloat() - 0.5) * 0.3,
                        0.0, 0.1, 0.0);
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
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
        return null;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState blockState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        if (!blockState.isAir()) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return blockState;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : (w, p, s, be) -> {
            if (be instanceof FilterBlockEntity filterBE) {
                filterBE.tick();
            }
        };
    }
}
