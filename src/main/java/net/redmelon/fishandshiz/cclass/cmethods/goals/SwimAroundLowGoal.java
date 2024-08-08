package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SwimAroundLowGoal extends WanderAroundGoal {
    private final int minYLevel;
    private final int maxYLevel;
    public SwimAroundLowGoal(PathAwareEntity mob, double speed, int minYLevel, int maxYLevel) {
        super(mob, speed);
        this.minYLevel = minYLevel;
        this.maxYLevel = maxYLevel;
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() {
        Vec3d target = LookTargetUtil.find(this.mob, 10, 7);
        if (target == null) {
            return null;
        }

        BlockPos targetPos = new BlockPos((int) target.x, (int) target.y, (int) target.z);
        World world = this.mob.getWorld();

        if (targetPos.getY() < this.minYLevel || targetPos.getY() > this.maxYLevel) {
            return null;
        }

        while (targetPos.getY() > this.minYLevel && world.isWater(targetPos.down())) {
            targetPos = targetPos.down();
        }

        return Vec3d.ofBottomCenter(targetPos);
    }
}
