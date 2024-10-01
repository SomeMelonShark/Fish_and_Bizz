package net.redmelon.fishandshiz.cclass.cmethods.goals;

import com.mojang.datafixers.DataFixUtils;
import net.minecraft.entity.ai.goal.Goal;
import net.redmelon.fishandshiz.cclass.AltSchoolingBreedEntity;
import net.redmelon.fishandshiz.cclass.SchoolingBreedEntity;

import java.util.List;
import java.util.function.Predicate;

public class BreedFollowGroupLeaderGoal
        extends Goal {
    private static final int MIN_SEARCH_DELAY = 200;
    private final SchoolingBreedEntity fish;
    private int moveDelay;
    private int checkSurroundingDelay;

    public BreedFollowGroupLeaderGoal(SchoolingBreedEntity fish) {
        this.fish = fish;
        this.checkSurroundingDelay = this.getSurroundingSearchDelay(fish);
    }

    protected int getSurroundingSearchDelay(SchoolingBreedEntity fish) {
        return toGoalTicks(200 + fish.getRandom().nextInt(200) % 20);
    }

    @Override
    public boolean canStart() {
        if (this.fish.hasOtherFishInGroup()) {
            return false;
        }
        if (this.fish.hasLeader()) {
            return true;
        }
        if (this.checkSurroundingDelay > 0) {
            --this.checkSurroundingDelay;
            return false;
        }
        this.checkSurroundingDelay = this.getSurroundingSearchDelay(this.fish);
        Predicate<SchoolingBreedEntity> predicate = fish -> fish.canHaveMoreFishInGroup() || !fish.hasLeader();
        List<SchoolingBreedEntity> list = (List<SchoolingBreedEntity>) this.fish.getWorld().getEntitiesByClass(this.fish.getClass(), this.fish.getBoundingBox().expand(8.0, 8.0, 8.0), predicate);
        SchoolingBreedEntity schoolingBreedEntity = DataFixUtils.orElse(list.stream().filter(SchoolingBreedEntity::canHaveMoreFishInGroup).findAny(), this.fish);
        schoolingBreedEntity.pullInOtherFish(list.stream().filter(fish -> !fish.hasLeader()));
        return this.fish.hasLeader();
    }

    @Override
    public boolean shouldContinue() {
        return this.fish.hasLeader() && this.fish.isCloseEnoughToLeader();
    }

    @Override
    public void start() {
        this.moveDelay = 0;
    }

    @Override
    public void stop() {
        this.fish.leaveGroup();
    }

    @Override
    public void tick() {
        if (--this.moveDelay > 0) {
            return;
        }
        this.moveDelay = this.getTickCount(10);
        this.fish.moveTowardLeader();
    }
}
