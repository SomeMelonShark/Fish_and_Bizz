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

public record DottybackPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<DottybackPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final DottybackPattern BICOLOR = create("bicolor", false);
    public static final DottybackPattern HEADACHE = create("headache", false);
    public static final DottybackPattern LATERAL = create("lateral", false);

    public static final Registry<DottybackPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(DottybackPattern.class, new Identifier(FishAndShiz.MOD_ID, "dottyback_pattern"), new Identifier(FishAndShiz.MOD_ID, "bicolor"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "dottyback_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<DottybackPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static DottybackPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static DottybackPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static DottybackPattern create(String name, boolean empty) {
        DottybackPattern pattern = new DottybackPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/dottyback/dottybackpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<DottybackPattern> PATTERNS = of("patterns");

        private static TagKey<DottybackPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<DottybackPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
