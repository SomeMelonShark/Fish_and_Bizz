package net.redmelon.fishandshiz.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.block.custom.NitrogenDetectorBlock;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import net.redmelon.fishandshiz.cclass.FishNitrogenAccessor;
import net.redmelon.fishandshiz.cclass.HolometabolousAquaticEntity;
import net.redmelon.fishandshiz.sound.ModSounds;

import java.util.List;

public class NitrogenDetectorBlockEntity extends BlockEntity{
    private int soundTimer = 0;
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
        int searchRadius = 6;
        Box area = new Box(this.pos.add(-searchRadius, -searchRadius, -searchRadius),
                this.pos.add(searchRadius, searchRadius, searchRadius));

        this.nitrogenLevel = 0;
        int totalNitrogen = 0;
        int entityCount = 0;

        assert this.world != null;
        List<Entity> nearbyEntities = this.world.getEntitiesByClass(Entity.class, area, entity ->
                entity instanceof HolometabolousAquaticEntity ||
                        entity instanceof FishEntity);

        for (Entity entity : nearbyEntities) {
            int nitrogenInfluence = getNitrogenInfluence(entity);

            totalNitrogen += nitrogenInfluence;
            entityCount++;
        }

        if (entityCount > 0) {
            this.nitrogenLevel = totalNitrogen / entityCount;
        }
    }

    private static int getNitrogenInfluence(Entity entity) {
        int nitrogenInfluence = 0;

        if (entity instanceof HolometabolousAquaticEntity aquaticEntity) {
            nitrogenInfluence = aquaticEntity.getNitrogenLevel();
        } else if (entity instanceof FishEntity fishEntity) {
            nitrogenInfluence = ((FishNitrogenAccessor) fishEntity).getNitrogenLevel();
        }
        return nitrogenInfluence;
    }

    public static void tick(World world, BlockPos pos, BlockState state, NitrogenDetectorBlockEntity blockEntity) {
        if (!world.isClient) {
            assert blockEntity.world != null;
            if (state.get(NitrogenDetectorBlock.UNSTABLE)) {
                if (blockEntity.soundTimer <= 0) {
                    blockEntity.world.playSound(null, pos, ModSounds.NITROGEN_DETECTOR_WARNING, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    blockEntity.soundTimer = 30;
                } else {
                    blockEntity.soundTimer--;
                }
            } else {
                blockEntity.soundTimer = 0;
            }
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
