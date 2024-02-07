package net.redmelon.fishandshiz;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.block.entity.ModBlockEntities;
import net.redmelon.fishandshiz.block.entity.client.SeaAnemoneRenderer;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.client.fish.renderer.*;
import net.redmelon.fishandshiz.entity.client.renderer.BasicMiscRenderer;
import net.redmelon.fishandshiz.entity.client.renderer.BasicSmallMiscRenderer;

public class FishAndShizClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FANWORT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FANWORT_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AMAZON_SWORD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VALLISNERIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TALL_VALLISNERIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MONTE_CARLO, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.WATER, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.ANGELFISH, AngelfishRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANGELFISH_FRY, BasicFryRenderer.create("angelfish"));
        EntityRendererRegistry.register(ModEntities.ANGELFISH_EGG, BasicEggRenderer.create("angelfish", "angelfish"));
        EntityRendererRegistry.register(ModEntities.ARCHERFISH, BasicFishRenderer.create("archerfish"));
        EntityRendererRegistry.register(ModEntities.MILKFISH, BasicFishRenderer.create("milkfish"));
        EntityRendererRegistry.register(ModEntities.MILKFISH_FRY, BasicFryRenderer.create("milkfish"));
        EntityRendererRegistry.register(ModEntities.MILKFISH_EGG, BasicEggRenderer.create("milkfish", "milkfish"));
        EntityRendererRegistry.register(ModEntities.NEON_TETRA, BasicFishRenderer.create("neon_tetra"));
        EntityRendererRegistry.register(ModEntities.NEON_TETRA_FRY, BasicFryRenderer.create("neon_tetra"));
        EntityRendererRegistry.register(ModEntities.NEON_TETRA_EGG, BasicEggRenderer.create("neon_tetra", "neon_tetra"));
        EntityRendererRegistry.register(ModEntities.CLOWNFISH, BasicFishRenderer.create("clownfish"));
        EntityRendererRegistry.register(ModEntities.CLOWNFISH_FRY, BasicFryRenderer.create("clownfish"));
        EntityRendererRegistry.register(ModEntities.CLOWNFISH_EGG, BasicEggRenderer.create("clownfish", "clownfish"));
        EntityRendererRegistry.register(ModEntities.CORYDORAS, CorydorasRenderer::new);
        EntityRendererRegistry.register(ModEntities.CORYDORAS_FRY, BasicFryRenderer.create("corydoras"));
        EntityRendererRegistry.register(ModEntities.CORYDORAS_EGG, BasicEggRenderer.create("corydoras", "corydoras"));
        EntityRendererRegistry.register(ModEntities.OSCAR, BasicFishRenderer.create("oscar"));
        EntityRendererRegistry.register(ModEntities.OSCAR_FRY, BasicFryRenderer.create("oscar"));
        EntityRendererRegistry.register(ModEntities.OSCAR_EGG, BasicEggRenderer.create("angelfish", "oscar"));
        EntityRendererRegistry.register(ModEntities.RAINBOWFISH, BasicFishRenderer.create("rainbowfish"));
        EntityRendererRegistry.register(ModEntities.RAINBOWFISH_FRY, BasicFryRenderer.create("rainbowfish"));
        EntityRendererRegistry.register(ModEntities.RAINBOWFISH_EGG, BasicEggRenderer.create("corydoras", "rainbowfish"));
        EntityRendererRegistry.register(ModEntities.AURATUS, BasicFishRenderer.create("auratus"));
        EntityRendererRegistry.register(ModEntities.AURATUS_FRY, BasicFryRenderer.create("auratus"));
        EntityRendererRegistry.register(ModEntities.AURATUS_EGG, BasicEggRenderer.create("angelfish", "angelfish"));
        EntityRendererRegistry.register(ModEntities.GRAYLING, BasicFishRenderer.create("grayling"));
        EntityRendererRegistry.register(ModEntities.GRAYLING_FRY, BasicFryRenderer.create("grayling"));
        EntityRendererRegistry.register(ModEntities.GRAYLING_EGG, BasicEggRenderer.create("salmon", "grayling"));
        EntityRendererRegistry.register(ModEntities.AMUR_CARP, AmurCarpRenderer::new);
        EntityRendererRegistry.register(ModEntities.AMUR_CARP_FRY, BasicFryRenderer.create("amur_carp"));
        EntityRendererRegistry.register(ModEntities.AMUR_CARP_EGG, BasicEggRenderer.create("salmon", "amur_carp"));
        EntityRendererRegistry.register(ModEntities.BETTA, BettaRenderer::new);
        EntityRendererRegistry.register(ModEntities.BETTA_FRY, BasicFryRenderer.create("betta"));
        EntityRendererRegistry.register(ModEntities.BETTA_EGG, BasicEggRenderer.create("betta", "betta"));
        EntityRendererRegistry.register(ModEntities.SALMON_EGG, BasicEggRenderer.create("salmon", "salmon"));
        EntityRendererRegistry.register(ModEntities.SALMON_FRY, BasicFryRenderer.create("salmon"));
        EntityRendererRegistry.register(ModEntities.MUD_CRAB, BasicSmallMiscRenderer.create("mud_crab"));
        EntityRendererRegistry.register(ModEntities.MUD_CRAB_LARVA, BasicSmallMiscRenderer.create("mud_crab_larva"));
        EntityRendererRegistry.register(ModEntities.MUD_CRAB_EGG, BasicSmallMiscRenderer.create("mud_crab_egg"));
        EntityRendererRegistry.register(ModEntities.CRAYFISH, BasicMiscRenderer.create("crayfish"));
        EntityRendererRegistry.register(ModEntities.CRAYFISH_LARVA, BasicSmallMiscRenderer.create("crayfish_larva"));
        EntityRendererRegistry.register(ModEntities.CRAYFISH_EGG, BasicSmallMiscRenderer.create("mud_crab_egg"));
        EntityRendererRegistry.register(ModEntities.LION_MANE_JELLYFISH, BasicMiscRenderer.create("lion_mane"));
        EntityRendererRegistry.register(ModEntities.VOLCANO_SNAIL, BasicSmallMiscRenderer.create("volcano_snail"));
        EntityRendererRegistry.register(ModEntities.VOLCANO_SNAIL_EGG, BasicSmallMiscRenderer.create("volcano_snail_egg"));
        EntityRendererRegistry.register(ModEntities.CAPYBARA, BasicMiscRenderer.create("capybara"));

        BlockEntityRendererFactories.register(ModBlockEntities.SEA_ANEMONE_ENTITY, SeaAnemoneRenderer::new);
    }
}
