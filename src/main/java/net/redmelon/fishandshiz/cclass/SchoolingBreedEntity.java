package net.redmelon.fishandshiz.cclass;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.redmelon.fishandshiz.cclass.cmethods.EntitySize;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedFollowGroupLeaderGoal;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public abstract class SchoolingBreedEntity extends AnimalFishEntity implements EntitySize {
    @Nullable
    private SchoolingBreedEntity leader;
    private int groupSize = 1;

    public SchoolingBreedEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getLimitPerChunk() {
        return this.getMaxGroupSize();
    }

    public int getMaxGroupSize() {
        return super.getLimitPerChunk();
    }

    @Override
    protected boolean hasSelfControl() {
        return !this.hasLeader();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    public boolean hasLeader() {
        return this.leader != null && this.leader.isAlive();
    }

    public SchoolingBreedEntity joinGroupOf(SchoolingBreedEntity groupLeader) {
        this.leader = groupLeader;
        groupLeader.increaseGroupSize();
        return groupLeader;
    }

    public void leaveGroup() {
        this.leader.decreaseGroupSize();
        this.leader = null;
    }

    private void increaseGroupSize() {
        ++this.groupSize;
    }

    private void decreaseGroupSize() {
        --this.groupSize;
    }

    public boolean canHaveMoreFishInGroup() {
        return this.hasOtherFishInGroup() && this.groupSize < this.getMaxGroupSize();
    }

    public void tick() {
        super.tick();
        if (this.hasOtherFishInGroup() && this.getWorld().random.nextInt(200) == 1) {
            List<? extends AnimalFishEntity> list = this.getWorld().getNonSpectatingEntities(this.getClass(), this.getBoundingBox().expand(8.0, 8.0, 8.0));
            if (list.size() <= 1) {
                this.groupSize = 1;
            }
        }

    }

    public boolean hasOtherFishInGroup() {
        return this.groupSize > 1;
    }

    public boolean isCloseEnoughToLeader() {
        return this.squaredDistanceTo(this.leader) <= 121.0;
    }

    public void moveTowardLeader() {
        if (this.hasLeader()) {
            this.getNavigation().startMovingTo(this.leader, 1.0);
        }
    }

    public void pullInOtherFish(Stream<? extends SchoolingBreedEntity> fish) {
        fish.limit((long)(this.getMaxGroupSize() - this.groupSize)).filter((fishx) -> {
            return fishx != this;
        }).forEach((fishx) -> {
            fishx.joinGroupOf(this);
        });
    }


    @Override
    public ItemStack getBucketItem() {
        return null;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        super.initialize(world, difficulty, spawnReason, (EntityData)entityData, entityNbt);
        if (entityData == null) {
            entityData = new BreedFishData(this);
        } else {
            this.joinGroupOf(((BreedFishData)entityData).leader);
        }

        return (EntityData)entityData;
    }

    public static class BreedFishData
            implements EntityData {
        public final SchoolingBreedEntity leader;

        public BreedFishData(SchoolingBreedEntity leader) {
            this.leader = leader;
        }
    }

    public static boolean canSpawnHigh(EntityType<? extends WaterCreatureEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        RegistryEntry<Biome> registryEntry = world.getBiome(pos);
        int i = world.getSeaLevel();
        int j = i - 4;
        return pos.getY() >= j && pos.getY() <= i + 90 && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }

    public static boolean canSpawnLow(EntityType<? extends WaterCreatureEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        RegistryEntry<Biome> registryEntry = world.getBiome(pos);
        int i = world.getSeaLevel();
        int j = i - 30;
        return pos.getY() >= j && pos.getY() <= i - 8 && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }
}
