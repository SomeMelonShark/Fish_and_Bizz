package net.redmelon.fishandshiz.cclass;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.cmethods.goals.AltBreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.cclass.cmethods.goals.BreedFollowGroupLeaderGoal;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

/**Just a temporary solution for AnimalWaterEntities that need schooling behavior,
 * until AnimalFishEntity can be merged with AnimalWaterEntity**/
public abstract class AltSchoolingBreedEntity extends AnimalWaterEntity {
    @Nullable
    private AltSchoolingBreedEntity leader;
    private int groupSize = 1;

    protected AltSchoolingBreedEntity(EntityType<? extends AnimalWaterEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new AltBreedFollowGroupLeaderGoal(this));
    }

    @Override
    public int getLimitPerChunk() {
        return this.getMaxGroupSize();
    }

    public int getMaxGroupSize() {
        return super.getLimitPerChunk();
    }

    @Override
    public boolean hasSelfControl() {
        return !this.hasLeader();
    }

    protected SoundEvent getFlopSound() {
        return null;
    }

    public boolean hasLeader() {
        return this.leader != null && this.leader.isAlive();
    }

    public AltSchoolingBreedEntity joinGroupOf(AltSchoolingBreedEntity groupLeader) {
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

    @Override
    public void tick() {
        List<?> list;
        super.tick();
        if (this.hasOtherFishInGroup() && this.getWorld().random.nextInt(200) == 1 && (list = this.getWorld().getNonSpectatingEntities(this.getClass(), this.getBoundingBox().expand(8.0, 8.0, 8.0))).size() <= 1) {
            this.groupSize = 1;
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

    public void pullInOtherFish(Stream<? extends AltSchoolingBreedEntity> fish2) {
        fish2.limit(this.getMaxGroupSize() - this.groupSize).filter(fish -> fish != this).forEach(fish -> fish.joinGroupOf(this));
    }

    @Override
    public ItemStack getBucketItem() {
        return null;
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
    }
}
