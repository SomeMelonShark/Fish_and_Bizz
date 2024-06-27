package net.redmelon.fishandshiz.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LilyPadBlock.class)
public class LilyPadRootMixin extends PlantBlock {
    public LilyPadRootMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            FluidState fluidState = world.getFluidState(pos.down());
            if (fluidState.getFluid() == Fluids.WATER || state.getBlock() instanceof IceBlock) {
                BlockPos adjacentPos = pos.down();
                BlockState adjacentBlockState = ModBlocks.POTHOS_ROOT_CAP.getDefaultState();
                world.setBlockState(adjacentPos, adjacentBlockState, 3);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        double growthChance = 1;
        if (random.nextDouble() < growthChance) {
            BlockPos adjacentPos = pos.down();
            FluidState fluidState = world.getFluidState(adjacentPos);
            BlockState adjacentBlockState1 = world.getBlockState(adjacentPos);

            if (fluidState.isOf(Fluids.WATER) && !adjacentBlockState1.isOf(ModBlocks.POTHOS_ROOTS)) {
                BlockState adjacentBlockState2 = ModBlocks.POTHOS_ROOT_CAP.getDefaultState();
                world.setBlockState(adjacentPos, adjacentBlockState2, 3);
            }
        }
    }
}
