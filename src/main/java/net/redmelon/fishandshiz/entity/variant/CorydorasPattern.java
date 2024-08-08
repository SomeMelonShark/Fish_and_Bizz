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

public record CorydorasPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<CorydorasPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final CorydorasPattern DORSAL = create("dorsal", false);
    public static final CorydorasPattern GOTH = create("goth", false);
    public static final CorydorasPattern STERN = create("stern", false);
    public static final CorydorasPattern EYE = create("eye", false);

    public static final Registry<CorydorasPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(CorydorasPattern.class, new Identifier(FishAndShiz.MOD_ID, "corydoras_pattern"), new Identifier(FishAndShiz.MOD_ID, "dorsal"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "corydoras_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<CorydorasPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static CorydorasPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static CorydorasPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static CorydorasPattern create(String name, boolean empty) {
        CorydorasPattern pattern = new CorydorasPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras/corydoraspa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<CorydorasPattern> PATTERNS = of("patterns");

        private static TagKey<CorydorasPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<CorydorasPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
