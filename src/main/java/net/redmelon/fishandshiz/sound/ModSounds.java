package net.redmelon.fishandshiz.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;

public class ModSounds {
    public static SoundEvent CAPYBARA_AMBIENT = registerSoundEvent("capybara_ambient");
    public static SoundEvent CAPYBARA_HURT = registerSoundEvent("capybara_hurt");
    public static SoundEvent CAPYBARA_DEATH = registerSoundEvent("capybara_death");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(FishAndShiz.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
