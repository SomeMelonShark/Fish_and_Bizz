package net.redmelon.fishandshiz.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.redmelon.fishandshiz.block.custom.FilterBlock;
import net.redmelon.fishandshiz.block.custom.NitrogenDetectorBlock;
import net.redmelon.fishandshiz.sound.ModSounds;

public class FilterBlockEntity extends BlockEntity {
    private int soundCooldown = 0;
    private int soundTimer = 0;
    private int nitrogenStored = 0;
    public static final int MAX_NITROGEN = 100000;

    public FilterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FILTER_ENTITY, pos, state);
    }

    public int getNitrogenStored() {
        return nitrogenStored;
    }
    public void setNitrogenStored(int amount) {
        this.nitrogenStored = MathHelper.clamp(amount, 0, MAX_NITROGEN);
        markDirty();
    }

    public void addNitrogen(int amount) {
        this.nitrogenStored = Math.min(nitrogenStored + amount, MAX_NITROGEN);
    }

    public boolean isFull() {
        return nitrogenStored >= MAX_NITROGEN;
    }

    public void resetNitrogen() {
        this.nitrogenStored = 0;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putInt("NitrogenStored", nitrogenStored);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        nitrogenStored = tag.getInt("NitrogenStored");
    }

    public void tick() {
        if (world == null || world.isClient) return;
        impellerSound();

        BlockState state = getCachedState();
        if (!(state.getBlock() instanceof FilterBlock)) return;
        warningSound();

        if (state.getBlock() instanceof FilterBlock) {
            ((FilterBlock) state.getBlock()).influenceNearbyEntities(world, pos, getCachedState());
        }
    }

    private void warningSound(){
        if (isFull()) {
            if (soundCooldown <= 0) {
                world.playSound(null, pos, ModSounds.FILTER_FULL, SoundCategory.BLOCKS, 0.6f, 0.5f);
                soundCooldown = 100;
            } else {
                soundCooldown--;
            }
        } else {
            soundCooldown = 0;
        }
    }

    private void impellerSound(){
        if (soundTimer <= 0) {
            world.playSound(null, pos, ModSounds.FILTER_AMBIENT, SoundCategory.BLOCKS, 0.1f, 2.0f);
            soundTimer = 70;
        } else {
            soundTimer--;
        }
    }
}
