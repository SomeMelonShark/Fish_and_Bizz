package net.redmelon.fishandshiz.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public abstract class StoredRangedWeaponItem extends Item {
    protected static int LOAD_COUNT = 20;
    protected boolean loaded;
    protected int loadCount;
    public StoredRangedWeaponItem(Settings settings) {
        super(settings);
    }
    public boolean isLoaded(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        return tag.getBoolean("Loaded");
    }

    protected int getLoadCount(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        return tag.getInt("LoadCount");
    }

    public void setLoaded(ItemStack stack, boolean loaded) {
        NbtCompound tag = stack.getOrCreateNbt();
        int loadCount = getLoadCount(stack);
        if (loadCount <= LOAD_COUNT && loadCount > 0) {
            tag.putBoolean("Loaded", loaded);
        } else {
            tag.putBoolean("Loaded", false);
        }
        stack.setNbt(tag);
    }

    protected void setLoadCount(ItemStack stack, int loadCount) {
        NbtCompound tag = stack.getOrCreateNbt();
        tag.putInt("LoadCount", loadCount);
        stack.setNbt(tag);
        this.loadCount = loadCount;
    }

    public abstract int getRange();
}
