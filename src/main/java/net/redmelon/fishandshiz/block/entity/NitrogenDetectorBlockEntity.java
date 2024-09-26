package net.redmelon.fishandshiz.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.block.custom.NitrogenDetectorBlock;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;

import java.util.List;

public class NitrogenDetectorBlockEntity extends BlockEntity{
    public int nitrogenLevel = 0;
    private boolean unstable = false;
    public NitrogenDetectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NITROGEN_DETECTOR_ENTITY, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("NitrogenLevel", nitrogenLevel);
        nbt.putBoolean("Unstable", unstable);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.nitrogenLevel = nbt.getInt("NitrogenLevel");
        this.unstable = nbt.getBoolean("Unstable");
    }

    public void influenceFromNearbyEntities() {
        int searchRadius = 5;
        Box area = new Box(this.pos.add(-searchRadius, -searchRadius, -searchRadius),
                this.pos.add(searchRadius, searchRadius, searchRadius));

        this.nitrogenLevel = 0;

        List<Entity> nearbyEntities = this.world.getEntitiesByClass(Entity.class, area, entity -> entity instanceof AnimalFishEntity);

        int totalNitrogen = 0;
        int entityCount = 0;

        for (Entity entity : nearbyEntities) {
            if (entity instanceof AnimalFishEntity animalFishEntity) {
                int nitrogenInfluence = animalFishEntity.getNitrogenLevel();
                totalNitrogen += nitrogenInfluence;
                entityCount++;
            }
        }

        if (entityCount > 0) {
            this.nitrogenLevel = totalNitrogen / entityCount;
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, NitrogenDetectorBlockEntity blockEntity) {
        if (!world.isClient) {
            blockEntity.nitrogenLevel = 0;

            blockEntity.influenceFromNearbyEntities();

            if (blockEntity.nitrogenLevel < 0) {
                blockEntity.nitrogenLevel = 0;
            }

            if (state.getBlock() instanceof NitrogenDetectorBlock) {
                ((NitrogenDetectorBlock) state.getBlock()).reactToNitrogenLevel(world, pos, blockEntity.nitrogenLevel);
            }
        }
    }
}
