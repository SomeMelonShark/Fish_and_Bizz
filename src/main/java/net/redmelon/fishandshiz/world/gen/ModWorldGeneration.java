package net.redmelon.fishandshiz.world.gen;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        ModEntitySpawn.addEntitySpawn();
        ModPlantGeneration.generateFlora();
    }
}
