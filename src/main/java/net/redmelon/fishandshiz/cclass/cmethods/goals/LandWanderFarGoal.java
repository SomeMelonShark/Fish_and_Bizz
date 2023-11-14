package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class LandWanderFarGoal extends WanderAroundGoal {

    private final PathAwareEntity entity;
    public static final float CHANCE = 0.001F;
    protected final float probability;

    public LandWanderFarGoal(PathAwareEntity pathAwareEntity, double d) {
        this(pathAwareEntity, d, 0.001F);
    }

    public LandWanderFarGoal(PathAwareEntity mob, double speed, float probability) {
        super(mob, speed);
        this.probability = probability;
        this.entity = mob;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !entity.isInsideWaterOrBubbleColumn();
    }

    @Nullable
    protected Vec3d getWanderTarget() {
        if (this.mob.isInsideWaterOrBubbleColumn()) {
            Vec3d vec3d = FuzzyTargeting.find(this.mob, 15, 7);
            return vec3d == null ? super.getWanderTarget() : vec3d;
        } else {
            return this.mob.getRandom().nextFloat() >= this.probability ? FuzzyTargeting.find(this.mob, 10, 7) : super.getWanderTarget();
        }
    }
}
