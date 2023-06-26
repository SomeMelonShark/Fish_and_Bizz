package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class BreedAnimalMateGoal
        extends Goal {
    private static final TargetPredicate VALID_MATE_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility();
    protected final AnimalFishEntity animal;
    private final Class<? extends AnimalFishEntity> entityClass;
    protected final World world;
    protected @Nullable AnimalFishEntity mate;
    private int timer;
    private final double speed;

    public BreedAnimalMateGoal(AnimalFishEntity animal, double speed) {
        this(animal, speed, animal.getClass());
    }

    public BreedAnimalMateGoal(AnimalFishEntity animal, double speed, Class<? extends AnimalFishEntity> entityClass) {
        this.animal = animal;
        this.world = animal.getWorld();
        this.entityClass = entityClass;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (!this.animal.isInLove()) {
            return false;
        }
        this.mate = this.findMate();
        return this.mate != null;
    }

    @Override
    public boolean shouldContinue() {
        return this.mate.isAlive() && this.mate.isInLove() && this.timer < 60;
    }

    @Override
    public void stop() {
        this.mate = null;
        this.timer = 0;
    }

    @Override
    public void tick() {
        this.animal.getLookControl().lookAt(this.mate, 10.0f, this.animal.getMaxLookPitchChange());
        this.animal.getNavigation().startMovingTo(this.mate, this.speed);
        ++this.timer;
        if (this.timer >= this.getTickCount(60) && this.animal.squaredDistanceTo(this.mate) < 9.0) {
            this.breed();
        }
    }

    @Nullable
    private AnimalFishEntity findMate() {
        List<? extends AnimalFishEntity> list = this.world.getTargets(this.entityClass, VALID_MATE_PREDICATE, this.animal, this.animal.getBoundingBox().expand(8.0));
        double d = Double.MAX_VALUE;
        AnimalFishEntity animalFishEntity = null;
        for (AnimalFishEntity animalFishEntity2 : list) {
            if (!this.animal.canBreedWith(animalFishEntity2) || !(this.animal.squaredDistanceTo(animalFishEntity2) < d)) continue;
            animalFishEntity = animalFishEntity2;
            d = this.animal.squaredDistanceTo(animalFishEntity2);
        }
        return animalFishEntity;
    }

    protected void breed() {
        this.animal.breed((ServerWorld)this.world, this.mate);
    }
}
