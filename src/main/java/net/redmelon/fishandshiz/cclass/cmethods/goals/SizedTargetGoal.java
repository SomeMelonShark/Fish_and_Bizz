package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.SizeCategory;

import java.util.function.Predicate;

public class SizedTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
    private final SizeCategory attackerSize;
    private final int minSkip;
    private final int maxSkip;


    public SizedTargetGoal(PassiveWaterEntity mob, Class<T> targetClass, boolean checkVisibility, SizeCategory attackerSize, int minSkip, int maxSkip) {
        super(mob, targetClass, 10, checkVisibility, false, (target) -> {
            if (target instanceof PlayerEntity) {
                return false;
            }
            if (!(mob instanceof EntitySize attacker)) return false;
            boolean nearConduit = mob.isNearConduit();
            if (nearConduit) return false;

            SizeCategory targetSize;
            if (target instanceof EntitySize sizedTarget) {
                targetSize = sizedTarget.getSizeCategory();
            } else {
                targetSize = SizeCategory.VERY_LARGE; // fallback
            }

            int skip = attacker.getSizeCategory().ordinal() - targetSize.ordinal();
            return skip >= minSkip && skip <= maxSkip;
        });

        this.attackerSize = attackerSize;
        this.minSkip = minSkip;
        this.maxSkip = maxSkip;
    }
}
