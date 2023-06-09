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
import net.redmelon.fishandshiz.entity.client.renderer.*;

public class FishAndShizClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FANWORT_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FANWORT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.WATER, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.ANGELFISH, AngelfishRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANGELFISH_FRY, AngelfishFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANGELFISH_EGG, AngelfishEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARCHERFISH, ArcherfishRenderer::new);
        EntityRendererRegistry.register(ModEntities.MILKFISH, MilkfishRenderer::new);
        EntityRendererRegistry.register(ModEntities.MILKFISH_FRY, MilkfishFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.MILKFISH_EGG, MilkfishEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.NEON_TETRA, NeonTetraRenderer::new);
        EntityRendererRegistry.register(ModEntities.NEON_TETRA_FRY, NeonTetraFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.NEON_TETRA_EGG, NeonTetraEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.CLOWNFISH, ClownfishRenderer::new);
        EntityRendererRegistry.register(ModEntities.CLOWNFISH_FRY, ClownfishFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.CLOWNFISH_EGG, ClownfishEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.CORYDORAS, CorydorasRenderer::new);
        EntityRendererRegistry.register(ModEntities.CORYDORAS_FRY, CorydorasFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.CORYDORAS_EGG, CorydorasEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.OSCAR, OscarRenderer::new);
        EntityRendererRegistry.register(ModEntities.OSCAR_FRY, OscarFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.OSCAR_EGG, OscarEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.RAINBOWFISH, RainbowfishRenderer::new);
        EntityRendererRegistry.register(ModEntities.RAINBOWFISH_FRY, RainbowfishFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.RAINBOWFISH_EGG, RainbowfishEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.AURATUS, AuratusRenderer::new);
        EntityRendererRegistry.register(ModEntities.AURATUS_FRY, AuratusFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.AURATUS_EGG, AuratusEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRAYLING, GraylingRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRAYLING_FRY, GraylingFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRAYLING_EGG, GraylingEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.SALMON_EGG, SalmonEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.SALMON_FRY, SalmonFryRenderer::new);
        EntityRendererRegistry.register(ModEntities.MUD_CRAB, MudCrabRenderer::new);
        EntityRendererRegistry.register(ModEntities.MUD_CRAB_LARVA, MudCrabLarvaRenderer::new);
        EntityRendererRegistry.register(ModEntities.MUD_CRAB_EGG, MudCrabEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.LION_MANE_JELLYFISH, ManeJellyfishRenderer::new);
        EntityRendererRegistry.register(ModEntities.VOLCANO_SNAIL, VolcanoSnailRenderer::new);
        EntityRendererRegistry.register(ModEntities.VOLCANO_SNAIL_EGG, VolcanoSnailEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAPYBARA, CapybaraRenderer::new);

        BlockEntityRendererFactories.register(ModBlockEntities.SEA_ANEMONE_ENTITY, SeaAnemoneRenderer::new);
    }
}
