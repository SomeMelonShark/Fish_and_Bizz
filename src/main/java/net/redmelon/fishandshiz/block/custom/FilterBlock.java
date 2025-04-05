package net.redmelon.fishandshiz.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.redmelon.fishandshiz.block.entity.FilterBlockEntity;
import net.redmelon.fishandshiz.block.entity.NitrogenDetectorBlockEntity;
import net.redmelon.fishandshiz.cclass.FishNitrogenAccessor;
import org.jetbrains.annotations.Nullable;

import static net.redmelon.fishandshiz.block.entity.FilterBlockEntity.MAX_NITROGEN;

public class FilterBlock extends Block implements BlockEntityProvider {

    public static final IntProperty NITROGEN_LEVEL = IntProperty.of("nitrogen_level", 0, 3);
    public FilterBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NITROGEN_LEVEL);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FilterBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (!world.isClient) {
            // Get BlockEntity if needed
            if (world.getBlockEntity(pos) instanceof FilterBlockEntity filterEntity) {
                int nitrogen = filterEntity.getNitrogenStored();
                player.sendMessage(Text.literal("Nitrogen Level: " + nitrogen), false);
            } else {
                player.sendMessage(Text.literal("Nitrogen Level: Unknown"), false);
            }
        }

        return ActionResult.SUCCESS;
    }

    public int getNitrogenDecreaseAmount() {
        return 1000;
    }

    public void influenceNearbyEntities(World world, BlockPos pos, BlockState state) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof FilterBlockEntity filter)) return;

        if (filter.isFull()) return;

        int searchRadius = 5;
        Box area = new Box(pos.add(-searchRadius, -searchRadius, -searchRadius),
                pos.add(searchRadius, searchRadius, searchRadius));

        for (Entity entity : world.getEntitiesByClass(Entity.class, area,
                e -> e instanceof FishNitrogenAccessor)) {

            int current = getNitrogenInfluence(entity);
            int decrease = Math.min(getNitrogenDecreaseAmount(), MAX_NITROGEN - filter.getNitrogenStored());

            if (entity instanceof FishNitrogenAccessor nitrogenEntity) {
                nitrogenEntity.setNitrogenLevel(current - decrease);
                filter.addNitrogen(decrease);
            }

            if (filter.isFull()) break;
        }

        int newLevel = MathHelper.clamp(filter.getNitrogenStored() * 4 / MAX_NITROGEN, 0, 3);
        if (state.get(NITROGEN_LEVEL) != newLevel) {
            world.setBlockState(pos, state.with(NITROGEN_LEVEL, newLevel), 3);
        }
    }

    private static int getNitrogenInfluence(Entity entity) {
        if (entity instanceof FishNitrogenAccessor nitrogenEntity) {
            return nitrogenEntity.getNitrogenLevel();
        }
        return 0;
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
