package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.HolometabolousAquaticEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class BreedAnimalMateGoal
        extends Goal {
    private static final TargetPredicate VALID_MATE_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility();
    protected final HolometabolousAquaticEntity animal;
    private final Class<? extends HolometabolousAquaticEntity> entityClass;
    protected final World world;
    protected @Nullable HolometabolousAquaticEntity mate;
    private int timer;
    private final double speed;

    public BreedAnimalMateGoal(HolometabolousAquaticEntity animal, double speed) {
        this(animal, speed, animal.getClass());
    }

    public BreedAnimalMateGoal(HolometabolousAquaticEntity animal, double speed, Class<? extends HolometabolousAquaticEntity> entityClass) {
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
    private HolometabolousAquaticEntity findMate() {
        List<? extends HolometabolousAquaticEntity> list = this.world.getTargets(this.entityClass, VALID_MATE_PREDICATE, this.animal, this.animal.getBoundingBox().expand(8.0));
        double d = Double.MAX_VALUE;
        HolometabolousAquaticEntity aquaticEntity = null;
        for (HolometabolousAquaticEntity aquaticEntity2 : list) {
            if (!this.animal.canBreedWith(aquaticEntity2) || !(this.animal.squaredDistanceTo(aquaticEntity2) < d)) continue;
            aquaticEntity = aquaticEntity2;
            d = this.animal.squaredDistanceTo(aquaticEntity2);
        }
        return aquaticEntity;
    }

    protected void breed() {
        this.animal.breed((ServerWorld)this.world, this.mate);
    }
}
