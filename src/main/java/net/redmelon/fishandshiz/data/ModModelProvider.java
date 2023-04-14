package net.redmelon.fishandshiz.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.FISHMEAL_BLOCK);

        blockStateModelGenerator.registerPlantPart(ModBlocks.FANWORT, ModBlocks.FANWORT_PLANT,
                BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerParentedItemModel(ModItems.CAPYBARA_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.MILKFISH_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.ARCHERFISH_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.FISHMEAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.FISH_FOOD, Models.GENERATED);
        itemModelGenerator.register(ModItems.FANWORT, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANGELFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANGELFISH_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANGELFISH_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ARCHERFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MILKFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MILKFISH_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MILKFISH_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MILKFISH, Models.GENERATED);
        itemModelGenerator.register(ModItems.COOKED_MILKFISH, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORN, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORN_KERNELS, Models.GENERATED);
    }
}
