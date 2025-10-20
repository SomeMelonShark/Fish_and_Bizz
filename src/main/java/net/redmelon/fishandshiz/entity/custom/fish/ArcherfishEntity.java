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
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.SizeCategory;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.entity.variant.VariantManager;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;

public class ArcherfishEntity extends SchoolingBreedEntity implements GeoEntity, RangedAttackMob, EntitySize {
    protected static final TrackedData<Integer> VARIANT = DataTracker.registerData(ArcherfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final String BUCKET_VARIANT_TAG_KEY = "BucketVariantTag";
    private static final VariantManager VARIANT_MANAGER = new VariantManager(2);
    public ArcherfishEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public SizeCategory getSizeCategory() {
        return SizeCategory.MEDIUM;
    }

    @Override
    protected int getNitrogenIncreaseAmount() {
        if (!isFry() && !isMature()) {
            return 0;
        } else if (isFry()) {
            return 1;
        }
        return 3;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return SchoolingBreedEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 2));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(3, new WaterAttackGoal(this, 1.0, 40, 10.0f));
        this.goalSelector.add(3, new BreedFollowGroupLeaderGoal(this));

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
        this.setVariant(nbt.getInt("Variant"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
    }

    public int getVariantId() {
        return this.dataTracker.get(VARIANT);
    }

    public int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, VARIANT_MANAGER.normalizeId(variant));
    }
    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(BUCKET_VARIANT_TAG_KEY, this.getTypeVariant());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int i = random.nextInt(3);
        if (spawnReason == SpawnReason.BUCKET && entityNbt != null && entityNbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE)) {
            this.setVariant(entityNbt.getInt(BUCKET_VARIANT_TAG_KEY));
            return entityData;
        }
        if (spawnReason == SpawnReason.NATURAL && i == 0) {
            this.setBaby(true);
        }
        int variant = VARIANT_MANAGER.getRandomVariant(this.random);

        if (this.random.nextInt(600) == 0 && VARIANT_MANAGER.getVariantCount() > 1) {
            variant = VARIANT_MANAGER.normalizeId(1);
            this.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.5f, 1.5f);
        }

        this.setVariant(variant);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        ArcherfishSpitEntity archerfishSpitEntity = new ArcherfishSpitEntity(this.getWorld(),this);
        Vec3d direction = target.getEyePos().subtract(this.getPos());
        float velocity = 1.6f;
        float inaccuracy = 1 - this.getWorld().getDifficulty().getId();
        archerfishSpitEntity.setVelocity(direction.x, direction.y, direction.z, velocity, inaccuracy);
        this.playSound(SoundEvents.AMBIENT_UNDERWATER_ENTER, 0.6f,
                2.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
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

        @Override
        public void tick() {
            super.tick();
            if (this.archerfishEntity.getTarget() == null) return;

            LivingEntity target = this.archerfishEntity.getTarget();

            BlockPos surfacePos = this.archerfishEntity.getBlockPos();
            while (this.archerfishEntity.getWorld().getBlockState(surfacePos.up()).getFluidState().isStill()) {
                surfacePos = surfacePos.up();
            }
            double targetY = surfacePos.getY() - 0.5;

            this.archerfishEntity.getMoveControl().moveTo(
                    this.archerfishEntity.getX(),
                    targetY,
                    this.archerfishEntity.getZ(),
                    1.0
            );

            this.archerfishEntity.getLookControl().lookAt(target, 30.0f, 30.0f);
        }
    }

    public static String getVariantNameKey(int variantId) {
        int normalized = VARIANT_MANAGER.normalizeId(variantId);
        return "entity.fishandshiz.archerfish.type." + normalized;
    }
}
