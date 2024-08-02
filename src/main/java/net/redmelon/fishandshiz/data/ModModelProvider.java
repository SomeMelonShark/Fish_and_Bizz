package net.redmelon.fishandshiz.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
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

        blockStateModelGenerator.registerDoubleBlock(ModBlocks.TALL_VALLISNERIA, new Identifier(FishAndShiz.MOD_ID,"block/vallisneria"), new Identifier(FishAndShiz.MOD_ID, "block/tall_vallisneria"));

        blockStateModelGenerator.registerTintableCross(ModBlocks.POTHOS_ROOTS,BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.POTHOS_ROOT_CAP,BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerTintableCross(ModBlocks.LILY_PAD_STEM,BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerParentedItemModel(ModItems.CAPYBARA_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.MILKFISH_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.ARCHERFISH_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.GRAYLING_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.RED_TAIL_CATFISH_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.MUD_CRAB_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.CRAYFISH_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.LION_MANE_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.VOLCANO_SNAIL_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CULTURE_FEED_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRIED_CULTURE_FEED, Models.GENERATED);
        itemModelGenerator.register(ModItems.FISHMEAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.FISH_FOOD, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRIED_MULM, Models.GENERATED);
        itemModelGenerator.register(ModItems.FANWORT, Models.GENERATED);
        itemModelGenerator.register(ModItems.VALLISNERIA, Models.GENERATED);
        itemModelGenerator.register(ModItems.MONTE_CARLO, Models.GENERATED);
        itemModelGenerator.register(ModItems.AMAZON_SWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.POTHOS, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANGELFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANGELFISH_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANGELFISH_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.NEON_TETRA_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.NEON_TETRA_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.NEON_TETRA_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ARCHERFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MILKFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MILKFISH_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MILKFISH_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORYDORAS_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORYDORAS_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORYDORAS_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.OSCAR_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.OSCAR_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.OSCAR_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAINBOWFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAINBOWFISH_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAINBOWFISH_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.AURATUS_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.AURATUS_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.GRAYLING_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.GRAYLING_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.GRAYLING_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.AMUR_CARP_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.AMUR_CARP_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.AMUR_CARP_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.BETTA_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.BETTA_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.BETTA_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.PLATY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.PLATY_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CLOWNFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CLOWNFISH_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CLOWNFISH_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOATFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.TANG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.TANG_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.TANG_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.DOTTYBACK_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.DOTTYBACK_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.DOTTYBACK_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MARINE_ANGELFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.PARROTFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.BUTTERFLYFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.TRIGGERFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.SALMON_FRY_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.SALMON_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MUD_CRAB_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MUD_CRAB_LARVA_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRAYFISH_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRAYFISH_LARVA_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.VOLCANO_SNAIL_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.VOLCANO_SNAIL_EGG_BUCKET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MILKFISH, Models.GENERATED);
        itemModelGenerator.register(ModItems.COOKED_MILKFISH, Models.GENERATED);
        itemModelGenerator.register(ModItems.MUD_CRAB, Models.GENERATED);
        itemModelGenerator.register(ModItems.COOKED_MUD_CRAB, Models.GENERATED);
        itemModelGenerator.register(ModItems.SUSHI, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORN, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORNBREAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORN_KERNELS, Models.GENERATED);
        itemModelGenerator.register(ModItems.SEA_ANEMONE, Models.GENERATED);
    }
}
