package net.redmelon.fishandshiz.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.MultifaceGrowthBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MultifaceGrowthBlock.class)
public abstract class MultifaceGrowthMixin extends Block {
    public MultifaceGrowthMixin(Settings settings) {
        super(settings);
    }
}
