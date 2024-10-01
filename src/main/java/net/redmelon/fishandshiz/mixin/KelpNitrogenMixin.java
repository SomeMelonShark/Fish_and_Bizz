package net.redmelon.fishandshiz.mixin;

import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.KelpBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.cclass.FishNitrogenAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(KelpBlock.class)
public class KelpNitrogenMixin extends Block {
    public KelpNitrogenMixin(Settings settings) {
        super(settings);
    }

    @Unique
    public int getNitrogenDecreaseAmount() {
        return 3;
    }

    @Unique
    public void influenceNearbyEntities(World world, BlockPos pos) {
        int searchRadius = 3;
        Box area = new Box(pos.add(-searchRadius, -searchRadius, -searchRadius),
                pos.add(searchRadius, searchRadius, searchRadius));

        List<Entity> nearbyEntities = world.getEntitiesByClass(Entity.class, area,
                entity -> entity instanceof FishNitrogenAccessor);

        for (Entity entity : nearbyEntities) {
            int nitrogenInfluence = getNitrogenInfluence(entity);

            if (entity instanceof FishNitrogenAccessor nitrogenEntity) {
                nitrogenEntity.setNitrogenLevel(nitrogenInfluence - getNitrogenDecreaseAmount());
            }
        }
    }

    @Unique
    private static int getNitrogenInfluence(Entity entity) {
        if (entity instanceof FishNitrogenAccessor nitrogenEntity) {
            return nitrogenEntity.getNitrogenLevel();
        }
        return 0;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        influenceNearbyEntities(world, pos);
    }
}
