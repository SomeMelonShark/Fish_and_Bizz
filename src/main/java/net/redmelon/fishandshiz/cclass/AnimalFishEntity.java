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
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AnimalFishEntity extends PassiveWaterEntity implements Bucketable {
    private static final TrackedData<Integer> NITROGEN_LEVEL = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final int NITROGEN_THRESHOLD = 100;
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_FRY = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_MICRO = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_MATURE = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final int BREEDING_COOLDOWN = 6000;
    protected int loveTicks;
    protected int stageAge;
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.65f;
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
        this.dataTracker.startTracking(FROM_BUCKET, false);
        this.dataTracker.startTracking(IS_FRY, false);
        this.dataTracker.startTracking(IS_MICRO, false);
        this.dataTracker.startTracking(IS_MATURE, false);
        this.dataTracker.startTracking(NITROGEN_LEVEL, 0);
    }


    public int getNitrogenLevel() {
        return this.dataTracker.get(NITROGEN_LEVEL);
    }

    public void setNitrogenLevel(int level) {
        this.dataTracker.set(NITROGEN_LEVEL, Math.max(0, level));
    }

    @Override
    public boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
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

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
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

    @Override
    public SoundEvent getBucketFillSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    protected boolean hasSelfControl() {
        return true;
    }

    protected abstract SoundEvent getFlopSound();

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

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
    /** merge with AnimalEntity**/
    @Nullable
    private UUID lovingPlayer;

    protected AnimalFishEntity(EntityType<? extends AnimalFishEntity> entityType, World world) {
        super((EntityType<? extends PassiveWaterEntity>) entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
        this.moveControl = new FishMoveControl(this);
    }

    @Override
    protected void mobTick() {
        if (this.getBreedingAge() != 0) {
            this.loveTicks = 0;
        }
        super.mobTick();
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
    }

    protected void influenceNearbyEntities() {
        List<Entity> nearbyEntities = this.getWorld().getEntitiesByClass(Entity.class, this.getBoundingBox().expand(10), entity -> entity != this);

        for (Entity entity : nearbyEntities) {
            if (areEntitiesInSameWaterBody(this, entity, 100)) {
                if (entity instanceof AnimalFishEntity animalFishEntity) {
                    animalFishEntity.setNitrogenLevel(animalFishEntity.getNitrogenLevel() + getNitrogenIncreaseAmount() / 2);
                }
            }
        }
    }

    protected abstract int getNitrogenIncreaseAmount();

    private boolean areEntitiesInSameWaterBody(Entity entity1, Entity entity2, int maxDepth) {
        BlockPos start = entity1.getBlockPos();
        BlockPos target = entity2.getBlockPos();
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty() && maxDepth-- > 0) {
            BlockPos current = queue.poll();

            if (current.equals(target)) {
                return true;
            }

            for (Direction direction : Direction.values()) {
                BlockPos neighbor = current.offset(direction);

                if (!visited.contains(neighbor) && entity1.getWorld().getFluidState(neighbor).isIn((FluidTags.WATER))) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return false;
    }

    private void checkNitrogenLevelForDamage() {
        int nitrogenLevel = this.getNitrogenLevel();
        if (nitrogenLevel > NITROGEN_THRESHOLD) {
            int excessNitrogen = nitrogenLevel - NITROGEN_THRESHOLD;
            float damageAmount = excessNitrogen / 4.0F;
            this.damage(this.getDamageSources().generic(), damageAmount);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        this.loveTicks = 0;
        return super.damage(source, amount);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        if (world.getBlockState(pos.down()).isOf(Blocks.WATER)) {
            return 10.0f;
        }
        return world.getPhototaxisFavor(pos);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("InLove", this.loveTicks);
        if (this.lovingPlayer != null) {
            nbt.putUuid("LoveCause", this.lovingPlayer);
        }
        nbt.putBoolean("FromBucket", this.isFromBucket());
        nbt.putBoolean("IsFry", this.isFry());
        nbt.putBoolean("IsMicro", this.isMicro());
        nbt.putBoolean("IsMature", this.isMature());
        nbt.putInt("NitrogenLevel", this.getNitrogenLevel());
    }

    @Override
    public double getHeightOffset() {
        return 0.14;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.loveTicks = nbt.getInt("InLove");
        this.lovingPlayer = nbt.containsUuid("LoveCause") ? nbt.getUuid("LoveCause") : null;
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        this.setFry(nbt.getBoolean("IsFry"));
        this.setFry(nbt.getBoolean("IsMicro"));
        this.setMature(nbt.getBoolean("IsMature"));
        this.setNitrogenLevel(nbt.getInt("NitrogenLevel"));
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
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        Bucketable.copyDataToStack(this, stack);
        nbtCompound.putBoolean("IsMature", isMature());
        nbtCompound.putBoolean("IsFry", isFry());
        nbtCompound.putBoolean("IsMicro", isMicro());
        nbtCompound.putInt("NitrogenLevel", getNitrogenLevel());
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 120;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    @Override
    public int getXpToDrop() {
        return 1 + this.getWorld().random.nextInt(3);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == ModItems.FISH_FOOD;
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

    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
    }

    public boolean canEat() {
        return this.loveTicks <= 0;
    }

    public void lovePlayer(@Nullable PlayerEntity player) {
        this.loveTicks = 600;
        if (player != null) {
            this.lovingPlayer = player.getUuid();
        }
        this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_BREEDING_PARTICLES);
    }

    public void setLoveTicks(int loveTicks) {
        this.loveTicks = loveTicks;
    }

    public int getLoveTicks() {
        return this.loveTicks;
    }

    @Nullable
    public ServerPlayerEntity getLovingPlayer() {
        if (this.lovingPlayer == null) {
            return null;
        }
        PlayerEntity playerEntity = this.getWorld().getPlayerByUuid(this.lovingPlayer);
        if (playerEntity instanceof ServerPlayerEntity) {
            return (ServerPlayerEntity) playerEntity;
        }
        return null;
    }

    public boolean isInLove() {
        return this.loveTicks > 0;
    }

    public void resetLoveTicks() {
        this.loveTicks = 0;
    }

    public boolean canBreedWith(AnimalFishEntity other) {
        if (other == this) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return this.isInLove() && other.isInLove();
    }

    public void breed(ServerWorld world, AnimalFishEntity other) {
        PassiveWaterEntity passiveWaterEntity = this.createChild(world, other);
        if (passiveWaterEntity == null) {
            return;
        }
        ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();
        if (serverPlayerEntity == null && other.getLovingPlayer() != null) {
            serverPlayerEntity = other.getLovingPlayer();
        }
        if (serverPlayerEntity != null) {
            serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
            CustomCriteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, other, passiveWaterEntity);
        }
        this.setBreedingAge(6000);
        other.setBreedingAge(6000);
        this.resetLoveTicks();
        other.resetLoveTicks();
        passiveWaterEntity.setBaby(true);
        passiveWaterEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0f, 0.0f);
        world.spawnEntityAndPassengers(passiveWaterEntity);
        world.sendEntityStatus(this, EntityStatuses.ADD_BREEDING_PARTICLES);
        if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            world.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.ADD_BREEDING_PARTICLES) {
            for (int i = 0; i < 7; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                this.getWorld().addParticle(ParticleTypes.HEART, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
            }
        } else {
            super.handleStatus(status);
        }
    }
}
