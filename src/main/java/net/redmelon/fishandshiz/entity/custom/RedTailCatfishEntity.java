package net.redmelon.fishandshiz.entity.custom;

import com.google.common.collect.Maps;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Map;

public class RedTailCatfishEntity extends AnimalFishEntity implements GeoEntity, AngledModelEntity {
    private final Map<String, Vector3f> modelAngles = Maps.newHashMap();
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public RedTailCatfishEntity(EntityType<? extends RedTailCatfishEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.06f, 0.1f, true);
        this.lookControl = new YawAdjustingLookControl(this, 10);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 17)
                .add(EntityAttributes.GENERIC_ARMOR, 4)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 8)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6);
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.red_tail_catfish.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isAttacking()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.red_tail_catfish.bite", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.red_tail_catfish.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.2f, true));

        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AmurCarpEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, MudCrabEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, ArcherfishEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AngelfishEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AuratusEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, TropicalFishEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, RainbowfishEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, CrayfishEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, CorydorasEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, ClownfishEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, BettaEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, NeonTetraEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>((MobEntity)this, GuardianEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>((MobEntity)this, ElderGuardianEntity.class, true));
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.3f;
    }

    @Override
    public int getMaxLookPitchChange() {
        return 1;
    }

    @Override
    public int getMaxHeadRotation() {
        return 1;
    }

    @Override
    public ItemStack getBucketItem() {
        return null;
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_SALMON_FLOP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SALMON_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SALMON_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SALMON_HURT;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 2, this::genericFlopController));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int i = random.nextInt(2);
        if (spawnReason == SpawnReason.NATURAL && i == 0) {
            this.setBaby(true);
        }
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public Map<String, Vector3f> getModelAngles() {
        return this.modelAngles;
    }
}
