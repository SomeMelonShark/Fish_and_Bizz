package net.redmelon.fishandshiz.cclass;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public abstract class PassiveWaterEntity extends WaterCreatureEntity {
    protected static final TrackedData<Boolean> CHILD = DataTracker.registerData(PassiveWaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int BABY_AGE = -24000;
    private static final int HAPPY_TICKS = 40;
    protected int breedingAge;
    protected int forcedAge;
    protected int happyTicksRemaining;

    protected PassiveWaterEntity(EntityType<? extends PassiveWaterEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        PassiveWaterData passiveWaterData;
        if (entityData == null) {
            entityData = new PassiveWaterData(true);
        }
        if (spawnReason == SpawnReason.CONVERSION) {
            this.setBaby(true);
        }
        if ((passiveWaterData = (PassiveWaterData)entityData).canSpawnBaby() && passiveWaterData.getSpawnedCount() > 0 && world.getRandom().nextFloat() <= passiveWaterData.getBabyChance()) {
            this.setBreedingAge(0);
        }
        passiveWaterData.countSpawned();
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Nullable
    public abstract PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2);

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHILD, false);
    }

    public boolean isReadyToBreed() {
        return false;
    }

    public int getBreedingAge() {
        if (this.getWorld().isClient) {
            return this.dataTracker.get(CHILD) != false ? -1 : 1;
        }
        return this.breedingAge;
    }

    public void growUp(int age, boolean overGrow) {
        int i;
        int j = i = this.getBreedingAge();
        if ((i += age * 20) > 0) {
            i = 0;
        }
        int k = i - j;
        this.setBreedingAge(i);
        if (overGrow) {
            this.forcedAge += k;
            if (this.happyTicksRemaining == 0) {
                this.happyTicksRemaining = 40;
            }
        }
        if (this.getBreedingAge() == 0) {
            this.setBreedingAge(this.forcedAge);
        }
    }

    public void growUp(int age) {
        this.growUp(age, false);
    }

    public void setBreedingAge(int age) {
        int i = this.getBreedingAge();
        this.breedingAge = age;
        if (i < 0 && age >= 0 || i >= 0 && age < 0) {
            this.dataTracker.set(CHILD, age < 0);
            this.onGrowUp();
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.getBreedingAge());
        nbt.putInt("ForcedAge", this.forcedAge);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setBreedingAge(nbt.getInt("Age"));
        this.forcedAge = nbt.getInt("ForcedAge");
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (CHILD.equals(data)) {
            this.calculateDimensions();
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.getWorld().isClient) {
            if (this.happyTicksRemaining > 0) {
                if (this.happyTicksRemaining % 4 == 0) {
                    this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
                }
                --this.happyTicksRemaining;
            }
        } else if (this.isAlive()) {
            int i = this.getBreedingAge();
            if (i < 0) {
                this.setBreedingAge(++i);
            } else if (i > 0) {
                this.setBreedingAge(--i);
            }
        }
    }

    protected void onGrowUp() {
    }

    @Override
    public boolean isBaby() {
        return this.getBreedingAge() < 0;
    }

    @Override
    public void setBaby(boolean baby) {
        this.setBreedingAge(baby ? -24000 : 0);
    }

    /**
     * Calculates the age to pass to {@link #growUp(int)} in seconds.
     *
     * @apiNote When passing the value from {@link #getBreedingAge()}, make sure to
     * negate the value; otherwise, the entity's age will decrease.
     *
     * @param breedingAge the current, negated breeding age (in ticks)
     */
    public static int toGrowUpAge(int breedingAge) {
        return (int)((float)(breedingAge / 20) * 0.1f);
    }

    public static class PassiveWaterData
            implements EntityData {
        private int spawnCount;
        private final boolean babyAllowed;
        private final float babyChance;

        private PassiveWaterData(boolean babyAllowed, float babyChance) {
            this.babyAllowed = babyAllowed;
            this.babyChance = babyChance;
        }

        public PassiveWaterData(boolean babyAllowed) {
            this(babyAllowed, 0.05f);
        }

        public int getSpawnedCount() {
            return this.spawnCount;
        }

        public void countSpawned() {
            ++this.spawnCount;
        }

        public boolean canSpawnBaby() {
            return this.babyAllowed;
        }

        public float getBabyChance() {
            return this.babyChance;
        }
    }
    public static boolean canSpawn(EntityType<? extends WaterCreatureEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int i = world.getSeaLevel();
        int j = i - 13;
        return pos.getY() >= j && pos.getY() <= i && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }
}
