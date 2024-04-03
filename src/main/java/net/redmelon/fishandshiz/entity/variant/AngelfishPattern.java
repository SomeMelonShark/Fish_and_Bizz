package net.redmelon.fishandshiz.entity.variant;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public record AngelfishPattern(@Nullable Identifier texture) {

    private static final Map<AngelfishPattern, Identifier> PATTERNS = new LinkedHashMap<>();

    public static final AngelfishPattern NONE = create("none", true);
    public static final AngelfishPattern WILD = create("wild", false);
    public static final AngelfishPattern MARBLE = create("marble", false);

    public static final AngelfishPattern STRIPES = create("stripes", false);

    public static final Registry<AngelfishPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(AngelfishPattern.class, new Identifier(FishAndShiz.MOD_ID, "angelfish_pattern"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "angelfish_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<AngelfishPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static AngelfishPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static AngelfishPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static AngelfishPattern create(String name, boolean empty) {
        AngelfishPattern pattern = new AngelfishPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {

        public static final TagKey<AngelfishPattern> NATURAL_PATTERNS = of("natural_patterns");

        private static TagKey<AngelfishPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<AngelfishPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}