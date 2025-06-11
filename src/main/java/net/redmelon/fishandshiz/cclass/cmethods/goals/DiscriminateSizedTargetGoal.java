package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.SizeCategory;

import java.util.function.Predicate;

public class DiscriminateSizedTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
    private final SizeCategory attackerSize;
    private final int minSkip;
    private final int maxSkip;
    private final Predicate<? super LivingEntity> entityFilter;

    //The same goal but with entity filters
    public DiscriminateSizedTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, SizeCategory attackerSize, int minSkip, int maxSkip, Predicate<? super LivingEntity> entityFilter) {
        super(mob, targetClass, 10, checkVisibility, false, (target) -> {
            if (!(target instanceof EntitySize sizedTarget)) return false;
            if (!(mob instanceof EntitySize)) return false;

            int skip = attackerSize.ordinal() - sizedTarget.getSizeCategory().ordinal();
            return skip >= minSkip && skip <= maxSkip && entityFilter.test(target);
        });

        this.attackerSize = attackerSize;
        this.minSkip = minSkip;
        this.maxSkip = maxSkip;
        this.entityFilter = entityFilter;
    }
}
