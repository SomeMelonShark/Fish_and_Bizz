package net.redmelon.fishandshiz.cclass;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.redmelon.fishandshiz.cclass.cmethods.CustomCriteria;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AnimalFishEntity extends HolometabolousAquaticEntity implements EntitySize {
    private static final TrackedData<Boolean> IS_FRY = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_MICRO = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_MATURE = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected int stageAge;
    private boolean stuck = false;
    private BlockPos stuckTo = null;

    protected AnimalFishEntity(EntityType<? extends HolometabolousAquaticEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FishMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder createFishAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0);
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
        this.dataTracker.startTracking(IS_FRY, false);
        this.dataTracker.startTracking(IS_MICRO, false);
        this.dataTracker.startTracking(IS_MATURE, false);
    }

    public boolean isFry() {
        return this.dataTracker.get(IS_FRY);
    }
    public boolean isMicro() {
        return this.dataTracker.get(IS_FRY);
    }
    public boolean isMature() {
        return this.dataTracker.get(IS_MATURE);
    }
    public void setFry(boolean fry) {
        this.dataTracker.set(IS_FRY, fry);
    }
    public void setMicro(boolean micro) {
        this.dataTracker.set(IS_MICRO, micro);
    }
    public void setMature(boolean mature) {
        this.dataTracker.set(IS_MATURE, mature);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(4, new SwimToRandomPlaceGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(0.01f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, 0, 0.0));
            }
        } else {
            super.travel(movementInput);
        }
    }

    protected abstract SoundEvent getFlopSound();

    static class FishMoveControl
            extends MoveControl {
        protected final AnimalFishEntity fish;

        FishMoveControl(AnimalFishEntity owner) {
            super(owner);
            this.fish = owner;
        }

        @Override
        public void tick() {
            if (this.fish.isMature() || this.fish.isFry()) {
                if (this.fish.isSubmergedIn(FluidTags.WATER)) {
                    this.fish.setVelocity(this.fish.getVelocity().add(0.0, 0, 0.0));
                }
                if (this.state != MoveControl.State.MOVE_TO || this.fish.getNavigation().isIdle()) {
                    this.fish.setMovementSpeed(0.0f);
                    return;
                }
                float f = (float)(this.speed * this.fish.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                this.fish.setMovementSpeed(MathHelper.lerp(0.125f, this.fish.getMovementSpeed(), f));
                double d = this.targetX - this.fish.getX();
                double e = this.targetY - this.fish.getY();
                double g = this.targetZ - this.fish.getZ();
                if (e != 0.0) {
                    double h = Math.sqrt(d * d + e * e + g * g);
                    this.fish.setVelocity(this.fish.getVelocity().add(0.0, (double)this.fish.getMovementSpeed() * (e / h) * 0.1, 0.0));
                }
                if (d != 0.0 || g != 0.0) {
                    float i = (float)(MathHelper.atan2(g, d) * 57.2957763671875) - 90.0f;
                    this.fish.setYaw(this.wrapDegrees(this.fish.getYaw(), i, 90.0f));
                    this.fish.bodyYaw = this.fish.getYaw();
                }
            }
        }
    }

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
        if (!this.isTouchingWater() && this.isOnGround() && this.verticalCollision) {
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

            if (!isFry() && !isMature()) {
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
        if (!this.isMature() && !this.isFry()) {
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
        nbt.putBoolean("IsFry", this.isFry());
        nbt.putBoolean("IsMicro", this.isMicro());
        nbt.putBoolean("IsMature", this.isMature());
        nbt.putBoolean("Stuck", stuck);
        if (stuckTo != null) {
            nbt.putLong("StuckTo", stuckTo.asLong());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFry(nbt.getBoolean("IsFry"));
        this.setFry(nbt.getBoolean("IsMicro"));
        this.setMature(nbt.getBoolean("IsMature"));
        this.stuck = nbt.getBoolean("Stuck");
        if (nbt.contains("StuckTo")) {
            this.stuckTo = BlockPos.fromLong(nbt.getLong("StuckTo"));
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setFry(false);
        this.setMicro(false);
        this.setMature(true);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putBoolean("IsMature", isMature());
        nbtCompound.putBoolean("IsFry", isFry());
        nbtCompound.putBoolean("IsMicro", isMicro());
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
}
