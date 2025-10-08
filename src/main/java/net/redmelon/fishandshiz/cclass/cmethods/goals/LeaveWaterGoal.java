package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;

public class LeaveWaterGoal extends EscapeSuffocationGoal{
    private final PathAwareEntity mob;
    private final int searchRange;
    private final int chance;

    private BlockPos targetBlockPos;
    public LeaveWaterGoal(PathAwareEntity mob, double speed, int searchRange, int chance) {
        super(mob, speed, searchRange);
        this.mob = mob;
        this.searchRange = searchRange;
        this.chance = chance;
    }

    @Override
    public boolean canStart() {
        if (!mob.isInsideWaterOrBubbleColumn()) return false;
        if (mob.getRandom().nextInt(chance) != 0) return false;

        targetBlockPos = findNearbyLand();
        return targetBlockPos != null;
    }

    @Override
    public boolean shouldContinue() {
        return mob.isInsideWaterOrBubbleColumn()
                && !mob.getNavigation().isIdle()
                && targetBlockPos != null;
    }

    private BlockPos findNearbyLand() {
        BlockPos mobPos = mob.getBlockPos();
        for (BlockPos pos : BlockPos.iterateOutwards(mobPos, searchRange, searchRange, searchRange)) {
            if (mob.getWorld().getBlockState(pos).isSolidBlock(mob.getWorld(), pos)
                    && mob.getWorld().getBlockState(pos.up()).isAir()) {
                return pos.toImmutable();
            }
        }
        return null;
    }
}
