package net.redmelon.fishandshiz.cclass;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.*;
import net.redmelon.fishandshiz.cclass.cmethods.CustomCriteria;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.SizeCategory;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public abstract class HolometabolousAquaticEntity extends PassiveWaterEntity implements Bucketable, FishNitrogenAccessor, EntitySize {
    private static final TrackedData<Integer> NITROGEN_LEVEL = DataTracker.registerData(HolometabolousAquaticEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> AIR_RESISTANT = DataTracker.registerData(HolometabolousAquaticEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final int NITROGEN_THRESHOLD = 1200;
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(HolometabolousAquaticEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
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
        this.dataTracker.startTracking(AIR_RESISTANT, false);
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

    public boolean isAirResistant() {
        return this.dataTracker.get(AIR_RESISTANT);
    }

    public void setAirResistant(boolean airResistant) {
        this.dataTracker.set(AIR_RESISTANT, airResistant);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (this.isAirResistant()) {
            this.setAir(2400);
        }
        return entityData;
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

    protected boolean hasSelfControl() {
        return true;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void tickWaterBreathingAir(int air) {
        if (this.isAlive() && !this.isInsideWaterOrBubbleColumn() && this.isAirResistant()) {
            this.setAir(air - 1);
            if (this.getAir() == -60) {
                this.setAir(0);
                this.damage(this.getDamageSources().drown(), 1.0F);
            }
        } else if (this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.isAirResistant()) {
            this.setAir(2400);
        } else {
            super.tickWaterBreathingAir(air);
        }
    }

    static class FishMoveControl
            extends MoveControl {
        protected final HolometabolousAquaticEntity fish;

        FishMoveControl(HolometabolousAquaticEntity owner) {
            super(owner);
            this.fish = owner;
        }
    }

    @Nullable
    private UUID lovingPlayer;

    protected HolometabolousAquaticEntity(EntityType<? extends HolometabolousAquaticEntity> entityType, World world) {
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

    protected void influenceNearbyEntities() {
        List<Entity> nearbyEntities = this.getWorld().getEntitiesByClass(Entity.class, this.getBoundingBox().expand(8), entity -> entity != this);

        for (Entity entity : nearbyEntities) {
            if (floodFill(this, entity, 251, 5)) {
                if (entity instanceof FishEntity || entity instanceof HolometabolousAquaticEntity) {
                    this.setNitrogenLevel(this.getNitrogenLevel() + getNitrogenIncreaseAmount() / 2);
                }
            }
        }
    }

    protected abstract int getNitrogenIncreaseAmount();

    private boolean floodFill(Entity entity1, Entity entity2, int maxDepth, int maxRange) {
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

                if (!start.isWithinDistance(neighbor, maxRange)) continue;

                if (!visited.contains(neighbor) && entity1.getWorld().getFluidState(neighbor).isIn((FluidTags.WATER))) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return false;
    }

    protected void checkNitrogenLevelForDamage() {
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
        nbt.putBoolean("AirResistant", this.isAirResistant());
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
        this.setAirResistant(nbt.getBoolean("AirResistant"));
        this.setNitrogenLevel(nbt.getInt("NitrogenLevel"));
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == ModItems.FISH_FOOD;
    }

    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
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

    public boolean canBreedWith(HolometabolousAquaticEntity other) {
        if (other == this) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return this.isInLove() && other.isInLove();
    }

    public void breed(ServerWorld world, HolometabolousAquaticEntity other) {
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
