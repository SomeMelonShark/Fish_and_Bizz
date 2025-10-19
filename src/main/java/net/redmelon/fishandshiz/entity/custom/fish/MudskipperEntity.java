package net.redmelon.fishandshiz.entity.custom.fish;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.SizeCategory;
import net.redmelon.fishandshiz.cclass.cmethods.goals.*;
import net.redmelon.fishandshiz.entity.custom.CrayfishEntity;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class MudskipperEntity extends AnimalFishEntity implements GeoEntity, EntitySize {
    private static final TrackedData<Boolean> BURROWED = DataTracker.registerData(MudskipperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public MudskipperEntity(EntityType<? extends AnimalFishEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new AmphibiousSwimNavigation(this, world);
    }

    @Override
    public SizeCategory getSizeCategory() {
        return SizeCategory.SMALL;
    }

    @Override
    protected int getNitrogenIncreaseAmount() {
        if (!isFry() && !isMature()) {
            return 0;
        } else if (isFry()) {
            return 1;
        }
        return 2;
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            if (this.getTarget() != null || this.isAttacking()) {
                animationState.getController().setAnimationSpeed(3.0f).setAnimation(RawAnimation.begin()
                        .then("animation.mudskipper.swim", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            } else if (!animationState.isMoving()) {
                animationState.getController().setAnimationSpeed(0.3f).setAnimation(RawAnimation.begin()
                        .then("animation.mudskipper.swim", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            } else {
                animationState.getController().setAnimationSpeed(1.0f).setAnimation(RawAnimation.begin()
                        .then("animation.mudskipper.swim", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
        } else if (animationState.isMoving()){
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mudskipper.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mudskipper.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isBurrowed()) {
            this.setVelocity(Vec3d.ZERO);
            return;
        }
        super.travel(movementInput);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 0.9));
        this.goalSelector.add(1, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 7.0f, 0.8, 0.9, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(1, new EscapeSuffocationGoal(this, 0.9, 20));
        this.goalSelector.add(1, new BurrowInMudGoal(this, 4));
        this.goalSelector.add(2, new BreedAnimalMateGoal(this, 1));
        this.goalSelector.add(2, new LeaveWaterGoal(this, 0.7, 20, 40));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.7));
        this.goalSelector.add(6, new ShortRangeAttackGoal(this, 1.0f, true, 1.0f));

        this.targetSelector.add(1, new SizedTargetGoal<>(this, LivingEntity.class, true, SizeCategory.LARGE, 2, 5));
        this.targetSelector.add(2, new ActiveTargetGoal<CrayfishEntity>((MobEntity)this, CrayfishEntity.class, true));


    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BURROWED, false);
    }

    public boolean isBurrowed() {
        return this.dataTracker.get(BURROWED);
    }

    public void setBurrowed(boolean burrowed) {
        this.dataTracker.set(BURROWED, burrowed);
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setAmphibious(true);
        this.setAirResistant(true);
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }

    @Override
    public boolean isInsideWall() {
        if (this.isBurrowed()) {
            return false;
        }
        return super.isInsideWall();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isBurrowed()) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_DEATH;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.MUDSKIPPER_BUCKET);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
    }

    public class BurrowInMudGoal extends Goal {
        private int burrowCooldown = 0;
        private final MudskipperEntity mob;
        private final double triggerRange;
        public BurrowInMudGoal(MudskipperEntity mob, double triggerRange) {
            this.mob = mob;
            this.triggerRange = triggerRange;
        }

        @Override
        public boolean canStart() {
            PlayerEntity player = this.mob.getWorld().getClosestPlayer(mob, triggerRange);
            if (player != null && !mob.isBurrowed()) {
                return true;
            } else if (mob.isBurrowed() && (player == null || player.squaredDistanceTo(mob) > triggerRange * triggerRange)) {
                return true;
            }
            return false;
        }

        @Override
        public void start() {
            PlayerEntity player = this.mob.getWorld().getClosestPlayer(mob, triggerRange);
            if (player != null && !mob.isBurrowed()) {
                if (mob.getWorld().getBlockState(mob.getBlockPos()).isOf(Blocks.MUD)) {
                    burrow();
                }
            } else if (mob.isBurrowed() && (player == null || player.squaredDistanceTo(mob) > triggerRange * triggerRange)
                    && (mob.getWorld().getBlockState(mob.getBlockPos().up()).isOf(Blocks.AIR) || mob.getWorld().getBlockState(mob.getBlockPos().up()).isOf(Blocks.WATER))) {
                unburrow();
            }
        }

        private void burrow() {
            if (mob.isBurrowed()) return;
            BlockPos pos = mob.getBlockPos();
            mob.setBurrowed(true);
            mob.setPosition(mob.getBlockX() + 0.5, mob.getBlockY() + 0.2, mob.getBlockZ() + 0.5);
            mob.setInvisible(true);
            ((ServerWorld) getWorld()).spawnParticles(
                    new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.MUD.getDefaultState()),
                    mob.getX(), mob.getY(), mob.getZ(),
                    20,
                    0.4, 1, 0.4,
                    0.05
            );
            getWorld().playSound(
                    null, pos,
                    SoundEvents.BLOCK_MUD_BREAK,
                    SoundCategory.NEUTRAL,
                    0.5F,
                    0.9F + getWorld().random.nextFloat() * 0.2F
            );
            this.resetBurrowCooldown(2);
        }

        private void unburrow() {
            BlockPos pos = mob.getBlockPos();
            mob.setPosition(mob.getX(), mob.getBlockY() + 1.7, mob.getZ());
            mob.setBurrowed(false);
            mob.setInvisible(false);
            ((ServerWorld) getWorld()).spawnParticles(
                    new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.MUD.getDefaultState()),
                    mob.getX(), mob.getY(), mob.getZ(),
                    15,
                    0.3, 1.0, 0.3,
                    0.07
            );
            getWorld().playSound(
                    null, pos,
                    SoundEvents.BLOCK_MUD_HIT,
                    SoundCategory.NEUTRAL,
                    0.5F,
                    0.9F + getWorld().random.nextFloat() * 0.2F
            );
            this.resetBurrowCooldown(2);
        }

        @Override
        public void tick() {
            if (burrowCooldown > 0) {
                burrowCooldown--;
            }
            if (mob.isBurrowed()) {
                BlockState state = mob.getWorld().getBlockState(mob.getBlockPos());
                if (!state.isOf(Blocks.MUD)) {
                    unburrow();
                    return;
                }
            }
            super.tick();
        }

        public void resetBurrowCooldown(int ticks) {
            this.burrowCooldown = ticks;
        }

        @Override
        public boolean shouldContinue() {
            PlayerEntity player = this.mob.getWorld().getClosestPlayer(mob, triggerRange);
            return mob.isBurrowed() && (player != null && player.squaredDistanceTo(mob) <= triggerRange * triggerRange);
        }
    }

}
