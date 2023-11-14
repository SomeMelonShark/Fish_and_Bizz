package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.sound.ModSounds;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ManeJellyfishEntity extends FishEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ManeJellyfishEntity(EntityType<? extends FishEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(2, new SwimAroundGoal(this, 1.0, 10));
    }

    private void sting(MobEntity mob) {
        if (mob.damage(this.getDamageSources().mobAttack(this), (float)(1))) {
            mob.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 1), this);
            this.playSound(SoundEvents.ENCHANT_THORNS_HIT, 1.0F, 1.0F);
        }

    }

    public void onPlayerCollision(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity && player.damage(this.getDamageSources().mobAttack(this), (float)(1))) {
            if (!this.isSilent()) {
                ((ServerPlayerEntity)player).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PUFFERFISH_STING, 0.0F));
            }

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 1), this);
        }

    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_SLIME_JUMP;
    }

    @Override
    public void tickMovement() {
        if(!this.isTouchingWater() && !this.isSubmergedInWater() && this.verticalCollision) {
            this.verticalCollision = false;
        }
        super.tickMovement();
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return FishEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 35)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0);
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.lion_mane.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.lion_mane.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.JELLYFISH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.JELLYFISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.JELLYFISH_DEATH;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public ItemStack getBucketItem() {
        return null;
    }
}
