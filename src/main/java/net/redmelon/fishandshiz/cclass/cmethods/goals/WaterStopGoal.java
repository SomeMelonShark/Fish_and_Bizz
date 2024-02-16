package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.redmelon.fishandshiz.block.ModBlocks;

public class WaterStopGoal extends MoveToTargetPosGoal {

    private int safeWaitingTime;

    public WaterStopGoal(PathAwareEntity animal, double speed, int range) {
        super(animal, speed, range);
    }

    @Override
    public boolean shouldContinue() {
        return this.tryingTime >= -this.safeWaitingTime && this.tryingTime <= 600 && this.isTargetPos(this.mob.getWorld(), this.targetPos);
    }

    @Override
    public void start() {
        this.startMovingToTarget();
        this.tryingTime = 0;
        this.safeWaitingTime = this.mob.getRandom().nextInt(this.mob.getRandom().nextInt(2400) + 1200) + 1200;
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return 0d;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos.up());
        return state.isOf(Blocks.WATER) || state.isOf(ModBlocks.AMAZON_SWORD) || state.isOf(ModBlocks.VALLISNERIA) || state.isOf(ModBlocks.FANWORT_PLANT);
    }
}
