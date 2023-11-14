package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class BreedWaterAnimalMateGoal extends Goal {
    private static final TargetPredicate VALID_MATE_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility();
    protected final AnimalWaterEntity animal;
    private final Class<? extends AnimalWaterEntity> entityClass;
    protected final World world;
    protected @Nullable AnimalWaterEntity mate;
    private int timer;
    private final double speed;

    public BreedWaterAnimalMateGoal(AnimalWaterEntity animal, double speed) {
        this(animal, speed, animal.getClass());
    }

    public BreedWaterAnimalMateGoal(AnimalWaterEntity animal, double speed, Class<? extends AnimalWaterEntity> entityClass) {
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
    private AnimalWaterEntity findMate() {
        List<? extends AnimalWaterEntity> list = this.world.getTargets(this.entityClass, VALID_MATE_PREDICATE, this.animal, this.animal.getBoundingBox().expand(8.0));
        double d = Double.MAX_VALUE;
        AnimalWaterEntity animalWaterEntity = null;
        for (AnimalWaterEntity animalWaterEntity2 : list) {
            if (!this.animal.canBreedWith(animalWaterEntity2) || !(this.animal.squaredDistanceTo(animalWaterEntity2) < d)) continue;
            animalWaterEntity = animalWaterEntity2;
            d = this.animal.squaredDistanceTo(animalWaterEntity2);
        }
        return animalWaterEntity;
    }

    protected void breed() {
        this.animal.breed((ServerWorld)this.world, this.mate);
    }
}
