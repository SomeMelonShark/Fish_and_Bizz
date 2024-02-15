package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.FluidTags;

import java.util.EnumSet;

public class FloatGoal extends Goal {
    private final MobEntity mob;

    public FloatGoal(MobEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.JUMP));
        mob.getNavigation().setCanSwim(true);
    }

    @Override
    public boolean canStart() {
        return this.mob.isTouchingWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getSwimHeight() || this.mob.isInLava();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.mob.getRandom().nextFloat() < 1.0f) {
            this.mob.getJumpControl().setActive();
        }
    }
}
