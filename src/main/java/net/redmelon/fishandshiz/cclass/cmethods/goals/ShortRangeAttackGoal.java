package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class ShortRangeAttackGoal extends MeleeAttackGoal {
    private final double attackRangeSquared;

    public ShortRangeAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle, float attackRange) {
        super(mob, speed, pauseWhenMobIdle);
        this.attackRangeSquared = attackRange * attackRange;
    }

    @Override
    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        return this.attackRangeSquared;
    }
}
