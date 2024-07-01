package net.redmelon.fishandshiz.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class StoredRangedWeaponItem extends Item {
    protected static int LOAD_COUNT = Math.abs(-5);
    protected boolean loaded;
    protected int loadCount;
    public StoredRangedWeaponItem(Settings settings) {
        super(settings);
    }
    public boolean isLoaded() {
        return this.loaded;
    }

    protected int getLoadCount() {
        return this.loadCount;
    }

    public void setLoaded(boolean loaded) {
        if (loadCount >= LOAD_COUNT) {
            this.loaded = loaded;
        } else{
            this.loaded = false;
        }
    }

    protected void setLoadCount(int loadCount){
        this.loadCount = loadCount;
    }

    public abstract int getRange();
}
