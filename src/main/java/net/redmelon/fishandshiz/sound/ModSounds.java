package net.redmelon.fishandshiz.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;

public class ModSounds {

    public static final SoundEvent SNAIL_AMBIENT = registerSoundEvents("snail_ambient");
    public static final SoundEvent SNAIL_HURT = registerSoundEvents("snail_hurt");
    public static final SoundEvent SNAIL_DEATH = registerSoundEvents("snail_death");
    public static final SoundEvent CRAB_AMBIENT = registerSoundEvents("crab_ambient");
    public static final SoundEvent CRAB_HURT = registerSoundEvents("crab_hurt");
    public static final SoundEvent CRAB_DEATH = registerSoundEvents("crab_death");
    public static final SoundEvent CAPYBARA_AMBIENT = registerSoundEvents("capybara_ambient");
    public static final SoundEvent CAPYBARA_HURT = registerSoundEvents("capybara_hurt");
    public static final SoundEvent CAPYBARA_DEATH = registerSoundEvents("capybara_death");
    public static final SoundEvent JELLYFISH_AMBIENT = registerSoundEvents("jellyfish_ambient");
    public static final SoundEvent JELLYFISH_HURT = registerSoundEvents("jellyfish_hurt");
    public static final SoundEvent JELLYFISH_DEATH = registerSoundEvents("jellyfish_death");
    public static final SoundEvent NITROGEN_DETECTOR_WARNING = registerSoundEvents("nitrogen_detector_warning");
    public static final SoundEvent FILTER_AMBIENT = registerSoundEvents("filter_ambient");
    public static final SoundEvent FILTER_FULL = registerSoundEvents("filter_full");

    private static SoundEvent registerSoundEvents(String name) {
        Identifier id = new Identifier(FishAndShiz.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        FishAndShiz.LOGGER.info("Registering Sounds for " + FishAndShiz.MOD_ID);
    }
}
