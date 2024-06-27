package net.redmelon.fishandshiz.cclass.cmethods.goals;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.redmelon.fishandshiz.cclass.EggboundEntity;
import net.redmelon.fishandshiz.cclass.cmethods.CustomCriteria;

public class EggboundMateGoal extends BreedWaterAnimalMateGoal{
    private final EggboundEntity entity;
    public EggboundMateGoal(EggboundEntity animal, double speed) {
        super(animal, speed);
        this.entity = animal;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !this.entity.hasEgg();
    }

    @Override
    protected void breed() {
        ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
        if (serverPlayerEntity == null && this.mate.getLovingPlayer() != null) {
            serverPlayerEntity = this.mate.getLovingPlayer();
        }
        if (serverPlayerEntity != null) {
            serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
            CustomCriteria.BRED_ANIMALS.trigger2(serverPlayerEntity, this.animal, this.mate, null);
        }
        this.entity.setHasEgg(true);
        this.animal.setBreedingAge(6000);
        this.mate.setBreedingAge(6000);
        this.animal.resetLoveTicks();
        this.mate.resetLoveTicks();
        Random random = this.animal.getRandom();
        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
        }
    }
}
