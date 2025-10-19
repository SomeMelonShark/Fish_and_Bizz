package net.redmelon.fishandshiz.cclass;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class PassiveWaterEntity extends WaterCreatureEntity {
    protected static final TrackedData<Boolean> CHILD = DataTracker.registerData(PassiveWaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> NEAR_CONDUIT = DataTracker.registerData(PassiveWaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final int BABY_AGE = -24000;
    private static final int HAPPY_TICKS = 40;
    protected int breedingAge;
    protected int forcedAge;
    protected int happyTicksRemaining;
    private boolean nearConduit;
    private boolean conduitChecked = false;

    protected PassiveWaterEntity(EntityType<? extends PassiveWaterEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            this.nearConduit = checkForActiveConduit(serverWorld, this.getBlockPos());
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        PassiveWaterData passiveWaterData;
        this.nearConduit = checkForActiveConduit(world.toServerWorld(), this.getBlockPos());
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
        this.dataTracker.startTracking(NEAR_CONDUIT, false);
    }

    public boolean isReadyToBreed() {
        return false;
    }

    public boolean isNearConduit() {
        return nearConduit;
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
        nbt.putBoolean("NearConduit", this.nearConduit);
        nbt.putBoolean("ConduitChecked", this.conduitChecked);

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setBreedingAge(nbt.getInt("Age"));
        this.forcedAge = nbt.getInt("ForcedAge");
        this.nearConduit = nbt.getBoolean("NearConduit");
        this.conduitChecked = nbt.getBoolean("ConduitChecked");
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
        if (!this.getWorld().isClient && !conduitChecked) {
            this.nearConduit = checkForActiveConduit((ServerWorld) this.getWorld(), this.getBlockPos());
            this.conduitChecked = true;
        }
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

    // Wait let's take a look :eyes:
    private boolean checkForActiveConduit(ServerWorld world, BlockPos origin) {
        double range = 96.0;
        double rangeSq = range * range;

        int minChunkX = (origin.getX() - (int) range) >> 4;
        int maxChunkX = (origin.getX() + (int) range) >> 4;
        int minChunkZ = (origin.getZ() - (int) range) >> 4;
        int maxChunkZ = (origin.getZ() + (int) range) >> 4;

        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                if (!world.isChunkLoaded(cx, cz)) continue;

                WorldChunk chunk = world.getChunk(cx, cz);

                Map<BlockPos, BlockEntity> blockEntityMap = chunk.getBlockEntities();

                for (Map.Entry<BlockPos, BlockEntity> entry : blockEntityMap.entrySet()) {
                    BlockPos pos = entry.getKey();
                    BlockEntity be = entry.getValue();

                    if (be instanceof ConduitBlockEntity conduit) {
                        if (conduit.isActive() && conduit.isEyeOpen()) {
                            if (pos.getSquaredDistance(origin) <= rangeSq) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
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
