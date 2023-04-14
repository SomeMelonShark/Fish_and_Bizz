package net.redmelon.fishandshiz.cclass.cutil;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModSoundEvents extends SoundEvents {
    public static final SoundEvent ENTITY_CAPYBARA_AMBIENT = ModSoundEvents.register("entity.zombie_villager.step");
    public static final SoundEvent ENTITY_CAPYBARA_HURT = ModSoundEvents.register("entity.zombie_villager.step");
    public static final SoundEvent ENTITY_CAPYBARA_DEATH = ModSoundEvents.register("entity.zombie_villager.step");

    private static SoundEvent register(String id) {
        return ModSoundEvents.register(new Identifier(id));
    }

    private static SoundEvent register(Identifier id) {
        return ModSoundEvents.register(id, id);
    }

    private static SoundEvent register(Identifier id, Identifier soundId) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }
}
