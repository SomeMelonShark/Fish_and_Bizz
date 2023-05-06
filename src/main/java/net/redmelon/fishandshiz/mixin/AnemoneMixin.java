package net.redmelon.fishandshiz.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.CoralFeature;
import net.redmelon.fishandshiz.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoralFeature.class)
public abstract class AnemoneMixin {

    @Inject(method = "generateCoralPiece", at = @At("RETURN"))
    protected void generateCoralPiece(WorldAccess world, Random random, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
            if(random.nextFloat() < 0.025f && canPlace(pos.up(), world)) {
                world.setBlockState(pos.up(),
                        (ModBlocks.SEA_ANEMONE)
                                .getDefaultState(), Block.NOTIFY_LISTENERS);
            }

    }

    private boolean canPlace(BlockPos pos, WorldAccess world) {
        BlockState state = world.getBlockState(pos);
        return state.isIn(BlockTags.CORALS) || state.isIn(BlockTags.WALL_CORALS) || state.isOf(Blocks.WATER);
    }
}
