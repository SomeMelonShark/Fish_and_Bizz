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

public record ButterflyfishPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<ButterflyfishPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final ButterflyfishPattern BANDS = create("bands", false);
    public static final ButterflyfishPattern SLASHES = create("slashes", false);
    public static final ButterflyfishPattern ANNULAR = create("annular", false);
    public static final ButterflyfishPattern BRASS = create("brass", false);

    public static final Registry<ButterflyfishPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(ButterflyfishPattern.class, new Identifier(FishAndShiz.MOD_ID, "butterflyfish_pattern"), new Identifier(FishAndShiz.MOD_ID, "bands"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "butterflyfish_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<ButterflyfishPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static ButterflyfishPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static ButterflyfishPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static ButterflyfishPattern create(String name, boolean empty) {
        ButterflyfishPattern pattern = new ButterflyfishPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/butterflyfish/butterflyfishpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<ButterflyfishPattern> PATTERNS = of("patterns");

        private static TagKey<ButterflyfishPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<ButterflyfishPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
