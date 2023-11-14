package net.redmelon.fishandshiz.entity.tags;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class TropicalSpawn {

    public static final TagKey<Biome> SPAWNS_TROPICAL = of("spawns_tropical");

    private static TagKey<Biome> of(String id) {
        return TagKey.of(RegistryKeys.BIOME, new Identifier(id));
    }
}
