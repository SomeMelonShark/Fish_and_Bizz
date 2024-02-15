package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ArcherfishEntity extends SchoolingBreedEntity implements GeoEntity, RangedAttackMob {
    boolean spit;
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public ArcherfishEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return SchoolingBreedEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4);
    }
    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.archerfish.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.archerfish.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 2));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(3, new WaterAttackGoal(this, 1.0, 40, 10.0f));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(4, new BreedFollowGroupLeaderGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<SpiderEntity>((MobEntity)this, SpiderEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<CaveSpiderEntity>((MobEntity)this, CaveSpiderEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<SilverfishEntity>((MobEntity)this, SilverfishEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<GuardianEntity>((MobEntity)this, GuardianEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<ElderGuardianEntity>((MobEntity)this, ElderGuardianEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<WitherEntity>((MobEntity)this, WitherEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
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
        return SoundEvents.ENTITY_TROPICAL_FISH_DEATH;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.ARCHERFISH_BUCKET);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    @Override
    public void writeCustomDatatoNbt(NbtCompound nbt) {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
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
    public void attack(LivingEntity target, float pullProgress) {
        ArcherfishSpitEntity archerfishSpitEntity = new ArcherfishSpitEntity(this.getWorld(),this);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - archerfishSpitEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        archerfishSpitEntity.setVelocity(d, e + g * (double)0.2f, f, 1.6f, 14 - this.getWorld().getDifficulty().getId() * 4);
        this.playSound(SoundEvents.AMBIENT_UNDERWATER_ENTER, 0.6f, 2.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.getWorld().spawnEntity(archerfishSpitEntity);
    }

    static class WaterAttackGoal
            extends ProjectileAttackGoal {
        private final ArcherfishEntity archerfishEntity;

        public WaterAttackGoal(RangedAttackMob rangedAttackMob, double d, int i, float f) {
            super(rangedAttackMob, d, i, f);
            this.archerfishEntity = (ArcherfishEntity)rangedAttackMob;
        }

        @Override
        public boolean canStart() {
            return super.canStart();
        }

        @Override
        public void start() {
            super.start();
            this.archerfishEntity.setAttacking(true);
        }

        @Override
        public void stop() {
            super.stop();
            this.archerfishEntity.setAttacking(false);
        }
    }
}
