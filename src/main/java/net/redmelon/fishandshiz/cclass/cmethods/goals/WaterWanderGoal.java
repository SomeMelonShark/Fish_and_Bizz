package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class WaterWanderGoal extends WanderAroundGoal {
    private final PathAwareEntity entity;

    public WaterWanderGoal (PathAwareEntity mob, double speed) {
        super(mob, speed);
        this.entity = mob;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && entity.isInsideWaterOrBubbleColumn();
    }

    @Nullable
    @Override
    protected Vec3d getWanderTarget() {
        Vec3d target = super.getWanderTarget();
        if(target != null && entity.getWorld().getFluidState(new BlockPos((int) target.x, (int) target.y, (int) target.z)).isIn(FluidTags.WATER))
            return target;
        return null;
    }
}

