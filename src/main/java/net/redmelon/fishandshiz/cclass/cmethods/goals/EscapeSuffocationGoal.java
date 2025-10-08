package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;

public class EscapeSuffocationGoal extends Goal {
    private final PathAwareEntity mob;
    private final double speed;
    private final int searchRange;

    private BlockPos targetBlockPos;

    public EscapeSuffocationGoal(PathAwareEntity mob, double speed, int searchRange) {
        this.mob = mob;
        this.speed = speed;
        this.searchRange = searchRange;
    }


    @Override
    public boolean canStart() {
        if (mob.getAir() > 60) return false;
        if (mob.isInsideWaterOrBubbleColumn() || mob.getBlockStateAtPos().isOf(Blocks.MUD)) return false;

        targetBlockPos = findNearbyTarget();
        return targetBlockPos != null;
    }

    @Override
    public void start() {
        if (targetBlockPos != null) {
            double tx = targetBlockPos.getX() + 0.5;
            double ty = targetBlockPos.getY() + 0.5;
            double tz = targetBlockPos.getZ() + 0.5;
            boolean success = mob.getNavigation().startMovingTo(tx, ty, tz, speed);
        }
    }

    @Override
    public boolean shouldContinue() {
        return !mob.isInsideWaterOrBubbleColumn() && mob.getBlockStateAtPos().isOf(Blocks.MUD) &&
                !mob.getNavigation().isIdle() &&
                targetBlockPos != null;
    }

    private BlockPos findNearbyTarget() {
        BlockPos mobPos = mob.getBlockPos();
        BlockPos closest = null;
        double closestDist = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.iterateOutwards(mobPos, searchRange, searchRange, searchRange)) {
            if (mob.getWorld().getBlockState(pos).getFluidState().isOf(Fluids.WATER) || mob.getWorld().getBlockState(pos).isOf(Blocks.MUD)) {
                double dist = mobPos.getSquaredDistance(pos);
                if (dist < closestDist) {
                    closestDist = dist;
                    closest = pos.toImmutable();
                }
            }
        }

        return closest;
    }
}
