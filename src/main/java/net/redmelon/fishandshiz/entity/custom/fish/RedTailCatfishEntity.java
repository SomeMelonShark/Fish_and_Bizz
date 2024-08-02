package net.redmelon.fishandshiz.entity.custom.fish;

import com.google.common.collect.Maps;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.entity.custom.CrayfishEntity;
import net.redmelon.fishandshiz.entity.custom.MudCrabEntity;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Map;

public class RedTailCatfishEntity extends AnimalFishEntity implements AngledModelEntity {
    private final Map<String, Vector3f> modelAngles = Maps.newHashMap();
    public RedTailCatfishEntity(EntityType<? extends RedTailCatfishEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FishMoveControl(this, 85, 10, 0.06f, 0.1f, true);
        this.lookControl = new YawAdjustingLookControl(this, 10);
    }

    public class FishMoveControl
            extends MoveControl {
        private static final float field_40123 = 10.0f;
        private static final float field_40124 = 60.0f;
        private final int pitchChange;
        private final int yawChange;
        private final float speedInWater;
        private final float speedInAir;
        private final boolean buoyant;

        public FishMoveControl(RedTailCatfishEntity entity, int pitchChange, int yawChange, float speedInWater, float speedInAir, boolean buoyant) {
            super(entity);
            this.pitchChange = pitchChange;
            this.yawChange = yawChange;
            this.speedInWater = speedInWater;
            this.speedInAir = speedInAir;
            this.buoyant = buoyant;
        }

        @Override
        public void tick() {
            double f;
            double e;
            if (this.buoyant && this.entity.isTouchingWater()) {
                this.entity.setVelocity(this.entity.getVelocity().add(0.0, 0.005, 0.0));
            }
            if (this.state != MoveControl.State.MOVE_TO || this.entity.getNavigation().isIdle()) {
                this.entity.setMovementSpeed(0.0f);
                this.entity.setSidewaysSpeed(0.0f);
                this.entity.setUpwardSpeed(0.0f);
                this.entity.setForwardSpeed(0.0f);
                return;
            }
            double d = this.targetX - this.entity.getX();
            double g = d * d + (e = this.targetY - this.entity.getY()) * e + (f = this.targetZ - this.entity.getZ()) * f;
            if (g < 2.500000277905201E-7) {
                this.entity.setForwardSpeed(0.0f);
                return;
            }
            float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875) - 90.0f;
            this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), h, this.yawChange));
            this.entity.bodyYaw = this.entity.getYaw();
            this.entity.headYaw = this.entity.getYaw();
            float i = (float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            if (this.entity.isTouchingWater()) {
                float k;
                this.entity.setMovementSpeed(i * this.speedInWater);
                double j = Math.sqrt(d * d + f * f);
                if (Math.abs(e) > (double)1.0E-5f || Math.abs(j) > (double)1.0E-5f) {
                    k = -((float)(MathHelper.atan2(e, j) * 57.2957763671875));
                    k = MathHelper.clamp(MathHelper.wrapDegrees(k), (float)(-this.pitchChange), (float)this.pitchChange);
                    this.entity.setPitch(this.wrapDegrees(this.entity.getPitch(), k, 5.0f));
                }
                k = MathHelper.cos(this.entity.getPitch() * ((float)Math.PI / 180));
                float l = MathHelper.sin(this.entity.getPitch() * ((float)Math.PI / 180));
                this.entity.forwardSpeed = k * i;
                this.entity.upwardSpeed = -l * i;
            } else {
                float m = Math.abs(MathHelper.wrapDegrees(this.entity.getYaw() - h));
                float n = FishMoveControl.method_45335(m);
                this.entity.setMovementSpeed(i * this.speedInAir * n);
            }
        }

        private static float method_45335(float f) {
            return 1.0f - MathHelper.clamp((f - 10.0f) / 50.0f, 0.0f, 1.0f);
        }
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 17)
                .add(EntityAttributes.GENERIC_ARMOR, 4)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 4)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.5f)
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
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.2f, true));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));

        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, AmurCarpEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, MudCrabEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>((MobEntity)this, ArcherfishEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AngelfishEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, AuratusEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, TropicalFishEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, RainbowfishEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>((MobEntity)this, CrayfishEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, CorydorasEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, BettaEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, NeonTetraEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, ClownfishEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, TangEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>((MobEntity)this, DottybackEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>((MobEntity)this, DrownedEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>((MobEntity)this, GuardianEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>((MobEntity)this, ElderGuardianEntity.class, true));
        this.targetSelector.add(1, new RevengeGoal(this));
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int i = random.nextInt(3);
        if (spawnReason == SpawnReason.NATURAL && i == 0) {
            this.setBaby(true);
        }
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }

    @Override
    public Map<String, Vector3f> getModelAngles() {
        return this.modelAngles;
    }
}
