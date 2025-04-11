package net.redmelon.fishandshiz.world.gen;

import net.redmelon.fishandshiz.world.biome.TerrestriaCompat;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        ModEntitySpawn.addEntitySpawn();
        ModPlantGeneration.generateFlora();
        TerrestriaCompat.injectStuff();
    }
}
