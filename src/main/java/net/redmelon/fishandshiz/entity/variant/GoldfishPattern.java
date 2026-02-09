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

public record GoldfishPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<GoldfishPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final GoldfishPattern SHADE = create("shade", false);
    public static final GoldfishPattern SPOTTY = create("spotty", false);
    public static final GoldfishPattern VORTEX = create("vortex", false);
    public static final GoldfishPattern COMET = create("comet", false);
    public static final GoldfishPattern WEN = create("wen", false);

    public static final Registry<GoldfishPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(GoldfishPattern.class, new Identifier(FishAndShiz.MOD_ID, "goldfish_pattern"), new Identifier(FishAndShiz.MOD_ID, "shade"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "goldfish_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<GoldfishPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static GoldfishPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static GoldfishPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static GoldfishPattern create(String name, boolean empty) {
        GoldfishPattern pattern = new GoldfishPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/goldfish/goldfishpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<GoldfishPattern> PATTERNS = of("patterns");

        private static TagKey<GoldfishPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<GoldfishPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
