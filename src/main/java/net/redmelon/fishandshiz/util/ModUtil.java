package net.redmelon.fishandshiz.util;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class ModUtil {

    public static <T> List<T> getTagValues(World world, TagKey<T> tagKey) {
        return world.getRegistryManager().get(tagKey.registry()).getEntryList(tagKey).map(entries -> entries.stream().map(RegistryEntry::value).toList()).orElse(List.of());
    }

    public static <T> T getRandomTagValue(World world, TagKey<T> tagKey, Random random) {
        return Util.getRandom(getTagValues(world, tagKey), random);
    }
}
