package net.redmelon.fishandshiz;

import net.fabricmc.api.ClientModInitializer;
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
import net.redmelon.fishandshiz.entity.client.ModModelLayers;
import net.redmelon.fishandshiz.entity.client.ModRenderer;
import net.redmelon.fishandshiz.entity.client.fish.model.RedTailCatfishModel;
import net.redmelon.fishandshiz.entity.client.fish.renderer.*;
import net.redmelon.fishandshiz.entity.client.renderer.BasicMiscRenderer;
import net.redmelon.fishandshiz.entity.client.renderer.BasicSmallMiscRenderer;
import net.redmelon.fishandshiz.entity.client.renderer.ReplacedSquidRenderer;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Mod;

public class FishAndShizClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModRenderer.renderBlocks();
        ModRenderer.renderGeckolibBlocks();
        ModRenderer.renderEntities();
        ModRenderer.renderGeckolibEntities();
    }
}
