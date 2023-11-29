package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.util.math.Vec3d;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import org.jetbrains.annotations.Nullable;

public class SwimToRandomPlaceGoal
        extends WanderAroundGoal {
    private final AnimalWaterEntity pathAwareEntity;

    public SwimToRandomPlaceGoal(AnimalWaterEntity pathAwareEntity, double d, int i) {
        super(pathAwareEntity, d, i);
        this.pathAwareEntity = pathAwareEntity;
    }

    @Nullable
    protected Vec3d getWanderTarget() {
        return LookTargetUtil.find(this.mob, 10, 7);
    }

    @Override
    public boolean canStart() {
        return this.pathAwareEntity.hasSelfControl() && super.canStart() && pathAwareEntity.isInsideWaterOrBubbleColumn() && !pathAwareEntity.isOnGround();
    }
}
