package net.redmelon.fishandshiz.entity.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.EntityType;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.block.entity.ModBlockEntities;
import net.redmelon.fishandshiz.block.entity.client.SeaAnemoneRenderer;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.client.fish.model.RedTailCatfishModel;
import net.redmelon.fishandshiz.entity.client.fish.renderer.*;
import net.redmelon.fishandshiz.entity.client.renderer.BasicMiscRenderer;
import net.redmelon.fishandshiz.entity.client.renderer.BasicSmallMiscRenderer;
import net.redmelon.fishandshiz.entity.client.renderer.ReplacedSquidRenderer;

public class ModRenderer {
    public static void renderBlocks() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FANWORT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FANWORT_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AMAZON_SWORD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VALLISNERIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TALL_VALLISNERIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MONTE_CARLO, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTHOS_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTHOS_ROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTHOS_ROOT_CAP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ANUBIAS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LILY_PAD_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CULTURE_FEED, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.WATER, RenderLayer.getCutout());
    }
    public static void renderGeckolibBlocks() {
        BlockEntityRendererFactories.register(ModBlockEntities.SEA_ANEMONE_ENTITY, SeaAnemoneRenderer::new);
    }
    public static void renderEntities() {
        EntityRendererRegistry.register(ModEntities.RED_TAIL_CATFISH, RedTailCatfishRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.RED_TAIL_CATFISH, RedTailCatfishModel::getTexturedModelData);
    }
    public static void renderGeckolibEntities() {
        EntityRendererRegistry.register(ModEntities.ANGELFISH, BasicVariableFishRenderer.create("angelfish", "angelfish"));
        EntityRendererRegistry.register(ModEntities.ANGELFISH_FRY, BasicImmatureRenderer.fryCreate("angelfish", "angelfish"));
        EntityRendererRegistry.register(ModEntities.ANGELFISH_EGG, BasicImmatureRenderer.eggCreate("angelfish", "angelfish"));
        EntityRendererRegistry.register(ModEntities.ARCHERFISH, ArcherfishRenderer::new);
        EntityRendererRegistry.register(ModEntities.MILKFISH, BasicFishRenderer.create("milkfish", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.MILKFISH_FRY, BasicImmatureRenderer.fryCreate("milkfish", "milkfish"));
        EntityRendererRegistry.register(ModEntities.MILKFISH_EGG, BasicImmatureRenderer.eggCreate("milkfish", "milkfish"));
        EntityRendererRegistry.register(ModEntities.NEON_TETRA, BasicFishRenderer.create("neon_tetra", "fry"));
        EntityRendererRegistry.register(ModEntities.NEON_TETRA_FRY, BasicImmatureRenderer.fryCreate("neon_tetra", "neon_tetra"));
        EntityRendererRegistry.register(ModEntities.NEON_TETRA_EGG, BasicImmatureRenderer.eggCreate("neon_tetra", "neon_tetra"));
        EntityRendererRegistry.register(ModEntities.CORYDORAS, BasicVariableFishRenderer.create("corydoras", "corydoras"));
        EntityRendererRegistry.register(ModEntities.CORYDORAS_FRY, BasicImmatureRenderer.fryCreate("corydoras", "corydoras"));
        EntityRendererRegistry.register(ModEntities.CORYDORAS_EGG, BasicImmatureRenderer.eggCreate("corydoras", "corydoras"));
        EntityRendererRegistry.register(ModEntities.OSCAR, BasicFishRenderer.create("oscar", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.OSCAR_FRY, BasicImmatureRenderer.fryCreate("oscar", "oscar"));
        EntityRendererRegistry.register(ModEntities.OSCAR_EGG, BasicImmatureRenderer.eggCreate("angelfish", "oscar"));
        EntityRendererRegistry.register(ModEntities.RAINBOWFISH, BasicVariableFishRenderer.create("rainbowfish", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.RAINBOWFISH_FRY, BasicImmatureRenderer.fryCreate("rainbowfish", "rainbowfish"));
        EntityRendererRegistry.register(ModEntities.RAINBOWFISH_EGG, BasicImmatureRenderer.eggCreate("corydoras", "rainbowfish"));
        EntityRendererRegistry.register(ModEntities.AURATUS, BasicFishRenderer.create("auratus", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.AURATUS_FRY, BasicImmatureRenderer.fryCreate("auratus", "auratus"));
        EntityRendererRegistry.register(ModEntities.GRAYLING, BasicFishRenderer.create("grayling", "grayling"));
        EntityRendererRegistry.register(ModEntities.GRAYLING_FRY, BasicImmatureRenderer.fryCreate("grayling", "grayling"));
        EntityRendererRegistry.register(ModEntities.GRAYLING_EGG, BasicImmatureRenderer.eggCreate("salmon", "grayling"));
        EntityRendererRegistry.register(ModEntities.AMUR_CARP, BasicVariableFishRenderer.create("amur_carp", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.AMUR_CARP_FRY, BasicImmatureRenderer.fryCreate("amur_carp", "amur_carp"));
        EntityRendererRegistry.register(ModEntities.AMUR_CARP_EGG, BasicImmatureRenderer.eggCreate("salmon", "amur_carp"));
        EntityRendererRegistry.register(ModEntities.BETTA, BasicVariableFishRenderer.create("betta", "betta"));
        EntityRendererRegistry.register(ModEntities.BETTA_FRY, BasicImmatureRenderer.fryCreate("betta", "betta"));
        EntityRendererRegistry.register(ModEntities.BETTA_EGG, BasicImmatureRenderer.eggCreate("betta", "betta"));
        EntityRendererRegistry.register(ModEntities.PLATY, BasicVariableFishRenderer.create("platy", "fry"));
        EntityRendererRegistry.register(ModEntities.PLATY_FRY, BasicImmatureRenderer.fryCreate("platy", "platy"));
        EntityRendererRegistry.register(ModEntities.CLOWNFISH, BasicVariableFishRenderer.create("clownfish", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.CLOWNFISH_FRY, BasicImmatureRenderer.fryCreate("platy", "platy"));
        EntityRendererRegistry.register(ModEntities.CLOWNFISH_EGG, BasicImmatureRenderer.eggCreate("clownfish", "clownfish"));
        EntityRendererRegistry.register(ModEntities.TANG, BasicVariableFishRenderer.create("tang", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.TANG_FRY, BasicImmatureRenderer.fryCreate("angelfish", "tang"));
        EntityRendererRegistry.register(ModEntities.TANG_EGG, BasicImmatureRenderer.eggCreate("clownfish", "tang"));
        EntityRendererRegistry.register(ModEntities.GOATFISH, BasicVariableFishRenderer.create("goatfish", "bottom_feeder"));
        EntityRendererRegistry.register(ModEntities.DOTTYBACK, BasicVariableFishRenderer.create("dottyback", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.DOTTYBACK_FRY, BasicImmatureRenderer.fryCreate("corydoras", "dottyback"));
        EntityRendererRegistry.register(ModEntities.DOTTYBACK_EGG, BasicImmatureRenderer.eggCreate("neon_tetra", "dottyback"));
        EntityRendererRegistry.register(ModEntities.MARINE_ANGELFISH, BasicVariableFishRenderer.create("marine_angelfish", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.PARROTFISH, BasicVariableFishRenderer.create("parrotfish", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.BUTTERFLYFISH, BasicVariableFishRenderer.create("butterflyfish", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.TRIGGERFISH, BasicVariableFishRenderer.create("triggerfish", "mediumfish"));
        EntityRendererRegistry.register(ModEntities.SALMON_EGG, BasicImmatureRenderer.eggCreate("salmon", "salmon"));
        EntityRendererRegistry.register(ModEntities.SALMON_FRY, BasicImmatureRenderer.fryCreate("salmon", "salmon"));
        EntityRendererRegistry.register(ModEntities.MUD_CRAB, BasicSmallMiscRenderer.create("mud_crab"));
        EntityRendererRegistry.register(ModEntities.MUD_CRAB_LARVA, BasicSmallMiscRenderer.create("mud_crab_larva"));
        EntityRendererRegistry.register(ModEntities.MUD_CRAB_LARVA, BasicSmallMiscRenderer.create("mud_crab_larva"));
        EntityRendererRegistry.register(ModEntities.CRAYFISH, BasicMiscRenderer.create("crayfish"));
        EntityRendererRegistry.register(ModEntities.CRAYFISH_LARVA, BasicSmallMiscRenderer.create("crayfish_larva"));
        EntityRendererRegistry.register(ModEntities.LION_MANE_JELLYFISH, BasicMiscRenderer.create("lion_mane"));
        EntityRendererRegistry.register(ModEntities.VOLCANO_SNAIL, BasicSmallMiscRenderer.create("volcano_snail"));
        EntityRendererRegistry.register(ModEntities.VOLCANO_SNAIL_EGG, BasicSmallMiscRenderer.create("volcano_snail_egg"));
        EntityRendererRegistry.register(ModEntities.CAPYBARA, BasicMiscRenderer.create("capybara"));
        EntityRendererRegistry.register(ModEntities.ARCHERFISH_SPIT, ArcherfishSpitRenderer::new);

        EntityRendererRegistry.register(EntityType.SQUID, ReplacedSquidRenderer::new);
    }
}
