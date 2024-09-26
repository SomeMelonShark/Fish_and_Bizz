package net.redmelon.fishandshiz.cclass;

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
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.redmelon.fishandshiz.cclass.cmethods.CustomCriteria;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AnimalWaterEntity extends PassiveWaterEntity implements Bucketable {
    private static final TrackedData<Integer> NITROGEN_LEVEL = DataTracker.registerData(AnimalWaterEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final int NITROGEN_THRESHOLD = 1200;
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(AnimalFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final int BREEDING_COOLDOWN = 6000;
    protected int loveTicks;
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.65f;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
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

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt("NitrogenLevel", getNitrogenLevel());
        Bucketable.copyDataToStack(this, stack);
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
    }

    @Override
    public SoundEvent getBucketFillSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    public boolean hasSelfControl() {
        return true;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    static class FishMoveControl
            extends MoveControl {
        protected final AnimalWaterEntity fish;

        FishMoveControl(AnimalWaterEntity owner) {
            super(owner);
            this.fish = owner;
        }
    }
    /** merge with AnimalEntity**/
    @Nullable
    private UUID lovingPlayer;

    protected AnimalWaterEntity(EntityType<? extends AnimalWaterEntity> entityType, World world) {
        super((EntityType<? extends PassiveWaterEntity>) entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
        this.moveControl = new AnimalWaterEntity.FishMoveControl(this);
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
        if (this.age % 100 == 0) {
            int decrease = 10;
            int increase = getNitrogenIncreaseAmount();

            this.setNitrogenLevel(this.getNitrogenLevel() + increase - decrease);
            this.influenceNearbyEntities();

            checkNitrogenLevelForDamage();
        }
    }

    protected void influenceNearbyEntities() {
        List<Entity> nearbyEntities = this.getWorld().getEntitiesByClass(Entity.class, this.getBoundingBox().expand(10), entity -> entity != this);

        for (Entity entity : nearbyEntities) {
            if (areEntitiesInSameWaterBody(this, entity, 100)) {
                if (entity instanceof AnimalWaterEntity animalWaterEntity) {
                    animalWaterEntity.setNitrogenLevel(animalWaterEntity.getNitrogenLevel() + getNitrogenIncreaseAmount() / 2);
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
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBucket", this.isFromBucket());
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
        super.readCustomDataFromNbt(nbt);
        this.setFromBucket(nbt.getBoolean("FromBucket"));
        this.setNitrogenLevel(nbt.getInt("NitrogenLevel"));
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalWaterEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) && isLightLevelValidForNaturalSpawn(world, pos);
    }
    protected static boolean isLightLevelValidForNaturalSpawn(BlockRenderView world, BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) > 0;
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
        return stack.isOf(Items.WHEAT);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isBreedingItem(itemStack)) {
            int i = this.getBreedingAge();
            if (!this.getWorld().isClient && i == 0 && this.canEat()) {
                this.eat(player, hand, itemStack);
                this.lovePlayer(player);
                return ActionResult.SUCCESS;
            }
            if (this.isBaby()) {
                this.eat(player, hand, itemStack);
                this.growUp(AnimalFishEntity.toGrowUpAge(-i), true);
                return ActionResult.success(this.getWorld().isClient);
            }
            if (this.getWorld().isClient) {
                return ActionResult.CONSUME;
            }
        }
        return Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
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

    public boolean canBreedWith(AnimalWaterEntity other) {
        if (other == this) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return this.isInLove() && other.isInLove();
    }

    public void breed(ServerWorld world, AnimalWaterEntity other) {
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
            CustomCriteria.BRED_ANIMALS.trigger2(serverPlayerEntity, this, other, passiveWaterEntity);
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
