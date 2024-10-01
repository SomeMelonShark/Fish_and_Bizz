package net.redmelon.fishandshiz;

import net.fabricmc.api.ClientModInitializer;
import net.redmelon.fishandshiz.entity.client.ModRenderer;

public class FishAndShizClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModRenderer.renderBlocks();
        ModRenderer.renderGeckolibBlocks();
        ModRenderer.renderEntities();
        ModRenderer.renderGeckolibEntities();
    }
}
