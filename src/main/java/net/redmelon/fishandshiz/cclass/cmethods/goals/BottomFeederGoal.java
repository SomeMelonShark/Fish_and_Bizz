package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.redmelon.fishandshiz.block.ModBlocks;

public class BottomFeederGoal extends MoveToTargetPosGoal {
    public static int FORAGE_DURATION = Math.abs(-60);

    private int forageDuration;

    public BottomFeederGoal(PathAwareEntity animal, double speed, int range) {
        super(animal, speed, range);
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return 0d;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos.up());
        return state.isOf(Blocks.MUD) || state.isOf(Blocks.DIRT) || state.isOf(Blocks.SAND) || state.isOf(Blocks.GRAVEL);
    }
}
