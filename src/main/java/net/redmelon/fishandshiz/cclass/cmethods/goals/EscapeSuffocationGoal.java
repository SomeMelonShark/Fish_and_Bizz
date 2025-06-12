package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;

public class EscapeSuffocationGoal extends Goal {
    private final PathAwareEntity mob;
    private final double speed;
    private final int searchRange;

    private BlockPos targetWaterPos;

    public EscapeSuffocationGoal(PathAwareEntity mob, double speed, int searchRange) {
        this.mob = mob;
        this.speed = speed;
        this.searchRange = searchRange;
    }


    @Override
    public boolean canStart() {
        if (mob.getAir() <= 600) return false;
        if (mob.isInsideWaterOrBubbleColumn()) return false;

        targetWaterPos = findNearbyWater();
        return targetWaterPos != null;
    }

    @Override
    public void start() {
        if (targetWaterPos != null) {
            mob.getNavigation().startMovingTo(
                    targetWaterPos.getX() + 0.5,
                    targetWaterPos.getY() + 1,
                    targetWaterPos.getZ() + 0.5,
                    speed
            );
        }
    }

    @Override
    public boolean shouldContinue() {
        return !mob.isInsideWaterOrBubbleColumn() &&
                !mob.getNavigation().isIdle() &&
                targetWaterPos != null;
    }

    private BlockPos findNearbyWater() {
        BlockPos mobPos = mob.getBlockPos();
        for (BlockPos pos : BlockPos.iterateOutwards(mobPos, searchRange, searchRange, searchRange)) {
            if (mob.getWorld().getBlockState(pos).getFluidState().isOf(Fluids.WATER)) {
                return pos.toImmutable();
            }
        }
        return null;
    }
}
