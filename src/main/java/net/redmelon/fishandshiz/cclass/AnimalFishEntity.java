package net.redmelon.fishandshiz.cclass;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AnimalFishEntity extends HolometabolousAquaticEntity implements GeoEntity, EntitySize {
    protected final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private static final TrackedData<Boolean> IS_AMPHIBIOUS = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_EGG = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_FRY = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_MICRO = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_MACRO = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_MATURE = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected int stageAge;
    private boolean stuck = false;
    private BlockPos stuckTo = null;

    protected AnimalFishEntity(EntityType<? extends HolometabolousAquaticEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FishMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder createFishAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f);
    }

    private PlayState genericFlopController(AnimationState animationState) {
        if (this.isTouchingWater()) {
            if (this.isFry()) {
                if (this.getTarget() != null || this.isAttacking()) {
                    animationState.getController().setAnimationSpeed(3.0f).setAnimation(RawAnimation.begin()
                            .then("animation.fry.swim", Animation.LoopType.LOOP));
                    return PlayState.CONTINUE;
                } else if (!animationState.isMoving()) {
                    animationState.getController().setAnimationSpeed(0.3f).setAnimation(RawAnimation.begin()
                            .then("animation.fry.swim", Animation.LoopType.LOOP));
                    return PlayState.CONTINUE;
                } else {
                    animationState.getController().setAnimationSpeed(1.0f).setAnimation(RawAnimation.begin()
                            .then("animation.fry.swim", Animation.LoopType.LOOP));
                    return PlayState.CONTINUE;
                }
            }
            if (this.getTarget() != null || this.isAttacking()) {
                animationState.getController().setAnimationSpeed(3.0f).setAnimation(RawAnimation.begin()
                        .then("animation.mediumfish.swim", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            } else if (!animationState.isMoving()) {
                animationState.getController().setAnimationSpeed(0.3f).setAnimation(RawAnimation.begin()
                        .then("animation.mediumfish.swim", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            } else {
            animationState.getController().setAnimationSpeed(1.0f).setAnimation(RawAnimation.begin()
                    .then("animation.mediumfish.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
            }
        } else if (this.isFry() && !this.isTouchingWater()) {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.fry.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {
            animationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.mediumfish.flop", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    @Override
    public int getLimitPerChunk() {
        return 8;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_AMPHIBIOUS, false);
        this.dataTracker.startTracking(IS_EGG, false);
        this.dataTracker.startTracking(IS_FRY, false);
        this.dataTracker.startTracking(IS_MICRO, false);
        this.dataTracker.startTracking(IS_MACRO, false);
        this.dataTracker.startTracking(IS_MATURE, false);
    }

    public boolean isAmphibious() {
        return this.dataTracker.get(IS_AMPHIBIOUS);
    }
    public boolean isEgg() {
        return this.dataTracker.get(IS_EGG);
    }
    public boolean isFry() {
        return this.dataTracker.get(IS_FRY);
    }
    public boolean isMicro() {
        return this.dataTracker.get(IS_MICRO);
    }
    public boolean isMacro() {
        return this.dataTracker.get(IS_MACRO);
    }
    public boolean isMature() {
        return this.dataTracker.get(IS_MATURE);
    }
    public void setAmphibious(boolean amphibious) {
        this.dataTracker.set(IS_AMPHIBIOUS, amphibious);
    }
    public void setEgg(boolean egg) {
        this.dataTracker.set(IS_EGG, egg);
    }
    public void setFry(boolean fry) {
        this.dataTracker.set(IS_FRY, fry);
    }
    public void setMicro(boolean micro) {
        this.dataTracker.set(IS_MICRO, micro);
    }
    public void setMacro(boolean macro) {
        this.dataTracker.set(IS_MACRO, macro);
    }
    public void setMature(boolean mature) {
        this.dataTracker.set(IS_MATURE, mature);
    }

    @Override
    protected void tickWaterBreathingAir(int air) {
        if (this.isAlive() && !this.isInsideWaterOrBubbleColumn() && !this.isMature() && !this.isFry() && !isAirResistant()) {
            this.setAir(air - 5);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.damage(this.getDamageSources().drown(), 1.0F);
            }
        } else {
            super.tickWaterBreathingAir(air);
        }
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(4, new SwimToRandomPlaceGoal(this));
    }

    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(0.01F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, 0, 0.0));
            }
        } else {
            super.travel(movementInput);
        }
    }

    protected static class FishMoveControl
            extends MoveControl {
        protected final AnimalFishEntity fish;

        FishMoveControl(AnimalFishEntity owner) {
            super(owner);
            this.fish = owner;
        }

        @Override
        public void tick() {
            if (!this.fish.isEgg()) {
                if (this.fish.isTouchingWater()) {
                    if (this.fish.isSubmergedIn(FluidTags.WATER)) {
                        this.fish.setVelocity(this.fish.getVelocity().add(0.0, 0.0, 0.0));
                    }
                    if (this.state != MoveControl.State.MOVE_TO || this.fish.getNavigation().isIdle()) {
                        this.fish.setMovementSpeed(0.0f);
                        return;
                    }
                    float f = (float) (this.speed * this.fish.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    this.fish.setMovementSpeed(MathHelper.lerp(0.125f, this.fish.getMovementSpeed(), f));
                    double d = this.targetX - this.fish.getX();
                    double e = this.targetY - this.fish.getY();
                    double g = this.targetZ - this.fish.getZ();
                    double h = Math.sqrt(d * d + e * e + g * g);
                    if (h > 1.0E-6) {
                        d /= h;
                        e /= h;
                        g /= h;

                        this.fish.setVelocity(
                                this.fish.getVelocity().add(
                                        d * this.fish.getMovementSpeed() * 0.05,
                                        e * this.fish.getMovementSpeed() * 0.1,
                                        g * this.fish.getMovementSpeed() * 0.05
                                )
                        );
                    }

                    if (d != 0.0 || g != 0.0) {
                        float i = (float) (MathHelper.atan2(g, d) * 57.2957763671875) - 90.0f;
                        this.fish.setYaw(this.wrapDegrees(this.fish.getYaw(), i, 90.0f));
                        this.fish.bodyYaw = this.fish.getYaw();
                    }
                } else if (this.fish.isAmphibious() && !this.fish.isTouchingWater()){
                    if (this.state == MoveControl.State.MOVE_TO && !this.fish.getNavigation().isIdle()) {
                        double dx = this.targetX - this.fish.getX();
                        double dz = this.targetZ - this.fish.getZ();
                        float targetYaw = (float) (MathHelper.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
                        this.fish.setYaw(this.wrapDegrees(this.fish.getYaw(), targetYaw, 90.0F));
                        this.fish.bodyYaw = this.fish.getYaw();
                    }
                    super.tick();
                }
            }
        }
    }

    // Only seems to work for the original Red Tail Catfish but not others
    public class CoolMoveControl
            extends MoveControl {
        private static final float field_40123 = 10.0f;
        private static final float field_40124 = 60.0f;
        private final int pitchChange;
        private final int yawChange;
        private final float speedInWater;
        private final float speedInAir;
        private final boolean buoyant;

        public CoolMoveControl(AnimalFishEntity entity, int pitchChange, int yawChange, float speedInWater, float speedInAir, boolean buoyant) {
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
                float pitchRadians = this.entity.getPitch() * ((float)Math.PI / 180);
                float yawRadians = this.entity.getYaw() * ((float)Math.PI / 180);

                float speed = i;

                double dx = -MathHelper.sin(yawRadians) * MathHelper.cos(pitchRadians);
                double dy = -MathHelper.sin(pitchRadians);
                double dz = MathHelper.cos(yawRadians) * MathHelper.cos(pitchRadians);

                Vec3d movementVec = new Vec3d(dx, dy, dz).normalize().multiply(speed);

                this.entity.setVelocity(movementVec);
                this.entity.forwardSpeed = (float) movementVec.horizontalLength();
                this.entity.upwardSpeed = (float) movementVec.y;
            } else {
                float m = Math.abs(MathHelper.wrapDegrees(this.entity.getYaw() - h));
                float n = CoolMoveControl.method_45335(m);
                this.entity.setMovementSpeed(i * this.speedInAir * n);
            }
        }

        private static float method_45335(float f) {
            return 1.0f - MathHelper.clamp((f - 10.0f) / 50.0f, 0.0f, 1.0f);
        }
    }

    protected abstract SoundEvent getFlopSound();

    static class SwimToRandomPlaceGoal
            extends SwimAroundGoal {
        private final AnimalFishEntity fish;

        public SwimToRandomPlaceGoal(AnimalFishEntity fish) {
            super(fish, 1.0, 40);
            this.fish = fish;
        }

        @Override
        public boolean canStart() {
            return this.fish.hasSelfControl() && super.canStart();
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.isTouchingWater() && this.isOnGround() && this.verticalCollision && !this.isAmphibious()) {
            this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0f - 1.0f) * 0.05f, 0.4f, (this.random.nextFloat() * 2.0f - 1.0f) * 0.05f));
            this.setOnGround(false);
            this.velocityDirty = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }
        if (this.getBreedingAge() != 0) {
            this.loveTicks = 0;
        }
        if (this.loveTicks > 0) {
            --this.loveTicks;
            if (this.loveTicks % 10 == 0) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                this.getWorld().addParticle(ParticleTypes.HEART, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
            }
        }
        if (!this.getWorld().isClient && !this.isMature()) {
            this.setStageAge(this.stageAge + 1);
        }
        if (this.age % 100 == 0) {
            int decrease;
            int increase = getNitrogenIncreaseAmount();

            if (!isFry() && !isMature() && isEgg()) {
                decrease = 8;
            } else if (isFry()) {
                decrease = 10;
            } else {
                decrease = 12;
            }

            this.setNitrogenLevel(this.getNitrogenLevel() + increase - decrease);
            this.influenceNearbyEntities();

            checkNitrogenLevelForDamage();
        }
        if (!this.isMature() && !this.isFry() && this.isEgg()) {
            if (this.isTouchingWater()) {
                this.setVelocity(this.getVelocity().x * (this.random.nextFloat() * 0.5f - 0.25f), -0.02, this.getVelocity().z * (this.random.nextFloat() * 0.5f - 0.25f));
            }
            if (!this.stuck) {
                tryStick();
            } else {
                BlockState stuckState = getWorld().getBlockState(stuckTo.down());
                if (stuckState.isAir() || stuckState.getFluidState().isStill() || !stuckState.isSolidBlock(getWorld(), stuckTo.down())) {
                    this.stuck = false;
                    this.setNoGravity(false);
                    this.setVelocity(0, -0.05, 0);
                } else {
                    this.setVelocity(Vec3d.ZERO);
                    this.setNoGravity(true);
                    this.setPosition(
                            stuckTo.getX() + 0.5,
                            stuckTo.getY(),
                            stuckTo.getZ() + 0.5
                    );
                }
            }
        }
    }

    private void tryStick() {
        BlockPos touchingPos = this.getBlockPos().down();
        BlockState touchingState = this.getWorld().getBlockState(touchingPos);

        if (touchingState.isSolidBlock(getWorld(), touchingPos) && !touchingState.getFluidState().isStill()) {
            this.stuck = true;
            this.stuckTo = touchingPos.up();

            this.setNoGravity(true);
            this.setVelocity(Vec3d.ZERO);
            this.setPosition(
                    stuckTo.getX() + 0.5,
                    stuckTo.getY(),
                    stuckTo.getZ() + 0.5
            );
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsAmphibious", this.isAmphibious());
        nbt.putBoolean("IsEgg", this.isEgg());
        nbt.putBoolean("IsFry", this.isFry());
        nbt.putBoolean("IsMicro", this.isMicro());
        nbt.putBoolean("IsMacro", this.isMacro());
        nbt.putBoolean("IsMature", this.isMature());
        nbt.putBoolean("Stuck", stuck);
        if (stuckTo != null) {
            nbt.putLong("StuckTo", stuckTo.asLong());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setAmphibious(nbt.getBoolean("IsAmphibious"));
        this.setEgg(nbt.getBoolean("IsEgg"));
        this.setFry(nbt.getBoolean("IsFry"));
        this.setMicro(nbt.getBoolean("IsMicro"));
        this.setMacro(nbt.getBoolean("IsMacro"));
        this.setMature(nbt.getBoolean("IsMature"));
        this.stuck = nbt.getBoolean("Stuck");
        if (nbt.contains("StuckTo")) {
            this.stuckTo = BlockPos.fromLong(nbt.getLong("StuckTo"));
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setEgg(false);
        this.setFry(false);
        this.setMicro(false);
        this.setMature(true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putBoolean("IsAmphibious", isAmphibious());
        nbtCompound.putBoolean("IsMature", isMature());
        nbtCompound.putBoolean("IsEgg", isEgg());
        nbtCompound.putBoolean("IsFry", isFry());
        nbtCompound.putBoolean("IsMicro", isMicro());
        nbtCompound.putBoolean("IsMacro", isMacro());
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isBreedingItem(itemStack)) {
            int i = this.getBreedingAge();
            if (!this.getWorld().isClient && i == 0 && this.canEat() && this.isMature()) {
                this.eat(player, hand, itemStack);
                this.lovePlayer(player);
                return ActionResult.SUCCESS;
            }
            if (this.isFry()) {
                this.eatFishFood(player, itemStack);
                return ActionResult.success(this.getWorld().isClient);
            }
            if (this.getWorld().isClient) {
                return ActionResult.CONSUME;
            }
        }
        if (this.isCultureFeed(itemStack) && getBreedingAge() > 0) {
            if (this.isMature()) {
                this.eatCultureFeed(player, itemStack);
                return ActionResult.success(this.getWorld().isClient);
            }

        }
        return Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
    }

    private void eatFishFood (PlayerEntity player, ItemStack stack) {
        this.decrementItem(player, stack);
        this.increaseAge(PassiveWaterEntity.toGrowUpAge(this.getTicksUntilGrowth()));
        this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
    }

    protected int getMaxStageAge() {
        return Math.abs(0);
    }

    protected int getStageAge() {
        return this.stageAge;
    }
    protected void increaseAge(int seconds) {
        this.setStageAge(this.stageAge + seconds * 20);
    }
    protected void setStageAge(int stageAge) {
        this.stageAge = stageAge;
        if (this.stageAge >= getMaxStageAge()) {
            this.growUp();
        }
    }

    protected void growUp() {
    }

    private int getTicksUntilGrowth() {
        return Math.max(0, this.getMaxStageAge() - this.stageAge);
    }

    private boolean isCultureFeed(ItemStack stack) {
        return stack.isOf(ModItems.DRIED_CULTURE_FEED);
    }
    private void eatCultureFeed (PlayerEntity player, ItemStack stack) {
        this.decrementItem(player, stack);
        this.cultureAge();
        this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
        this.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.5f);
    }
    private void decrementItem(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
    }

    private void cultureAge() {
        this.setBreedingAge((int) (breedingAge * 0.8));
    }

    public boolean canEat() {
        return this.loveTicks <= 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 5, this::genericFlopController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
