package net.redmelon.fishandshiz.entity.custom.fish;

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
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.entity.variant.BiVariant;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ArcherfishEntity extends SchoolingBreedEntity implements GeoEntity, RangedAttackMob {
    protected static final TrackedData<Integer> VARIANT = DataTracker.registerData(ArcherfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public ArcherfishEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return SchoolingBreedEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20);
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

        this.targetSelector.add(2, new ActiveTargetGoal<SpiderEntity>((MobEntity)this, SpiderEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<CaveSpiderEntity>((MobEntity)this, CaveSpiderEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<SilverfishEntity>((MobEntity)this, SilverfishEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<DrownedEntity>((MobEntity)this, DrownedEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<GuardianEntity>((MobEntity)this, GuardianEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<ElderGuardianEntity>((MobEntity)this, ElderGuardianEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<WitherEntity>((MobEntity)this, WitherEntity.class, true));
        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
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
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setArcherfishVariant(nbt.getInt("Variant"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
    }

    public static BiVariant getVariety(int variant) {
        return BiVariant.byId(variant);
    }

    public BiVariant getVariant() {
        return BiVariant.byId(this.getTypeVariant() & 255);
    }

    public int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    protected void setVariant(BiVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    protected void setArcherfishVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(BUCKET_VARIANT_TAG_KEY, this.getTypeVariant());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        BiVariant variant;
        int i = random.nextInt(3);
        int j = random.nextInt(600);
        if (spawnReason == SpawnReason.BUCKET && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE)) {
            this.setArcherfishVariant(entityNbt.getInt(BUCKET_VARIANT_TAG_KEY));
            return entityData;
        }
        if (spawnReason == SpawnReason.NATURAL && i == 0) {
            this.setBaby(true);
        }
        if (spawnReason == SpawnReason.NATURAL) {
            if (j == 0){
                variant = BiVariant.SPECIAL;
                this.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f);
            } else {
                variant = BiVariant.NORMAL;
            }
        } else {
            variant = BiVariant.byId(random.nextInt(1));
        }
        setVariant(variant);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.setArcherfishVariant(variant.getId());
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

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = source.getSource() instanceof ArcherfishSpitEntity;
        if (bl) {
            return false;
        }
        return super.damage(source, amount);
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
