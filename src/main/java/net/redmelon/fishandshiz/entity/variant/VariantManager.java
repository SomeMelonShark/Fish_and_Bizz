package net.redmelon.fishandshiz.entity.variant;

import net.minecraft.util.math.random.Random;

public class VariantManager {
    private final int variantCount;

    public VariantManager(int variantCount) {
        this.variantCount = Math.max(1, variantCount);
    }

    public int normalizeId(int id) {
        return Math.floorMod(id, variantCount);
    }

    public int getRandomVariant(Random random) {
        return random.nextInt(variantCount);
    }

    public int getVariantCount() {
        return variantCount;
    }
}
