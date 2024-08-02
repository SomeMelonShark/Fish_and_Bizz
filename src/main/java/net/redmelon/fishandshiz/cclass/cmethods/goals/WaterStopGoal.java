package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;
import net.redmelon.fishandshiz.block.ModBlocks;

import java.util.EnumSet;
import java.util.Random;

public class WaterStopGoal extends Goal {
    private final PathAwareEntity mob;
    private final int minStopDuration;
    private final int maxStopDuration;
    private int stopTimer;
    private int stopDuration;
    private final Random random;

    public WaterStopGoal(PathAwareEntity mob, int minStopDuration, int maxStopDuration) {
        this.mob = mob;
        this.minStopDuration = minStopDuration;
        this.maxStopDuration = maxStopDuration;
        this.random = new Random();
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return this.mob.isTouchingWater() && this.nearbyBlockPresent() && this.mob.getRandom().nextInt(100) < 5;
    }

    @Override
    public void start() {
        this.stopTimer = 0;
        this.stopDuration = this.minStopDuration + this.random.nextInt(this.maxStopDuration - this.minStopDuration + 1);
        stopEntityNavigation();
    }

    @Override
    public boolean shouldContinue() {
        return this.stopTimer < this.stopDuration;
    }

    @Override
    public void stop() {
    }

    @Override
    public void tick() {
        this.stopTimer++;
    }

    private void stopEntityNavigation() {
        EntityNavigation waterNavigation = this.mob.getNavigation();
        if (waterNavigation != null) {
            waterNavigation.stop();
        }
    }

    private boolean nearbyBlockPresent() {
        BlockPos entityPos = mob.getBlockPos();
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        int blockCount = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    mutablePos.set(entityPos.getX() + dx, entityPos.getY() + dy, entityPos.getZ() + dz);
                    BlockState blockState = mob.getWorld().getBlockState(mutablePos);

                    if (!blockState.getFluidState().isIn(FluidTags.WATER) && !blockState.isAir()) {
                        if (blockState.isOpaque()) {
                            blockCount++;
                            if (blockCount >= 6) {
                                return true;
                            }
                        }
                        if (blockState.isOf(ModBlocks.FANWORT_PLANT) || blockState.isOf(ModBlocks.AMAZON_SWORD) || blockState.isOf(ModBlocks.VALLISNERIA) || blockState.isOf(ModBlocks.TALL_VALLISNERIA)) {
                            blockCount++;
                            if (blockCount >= 2) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
