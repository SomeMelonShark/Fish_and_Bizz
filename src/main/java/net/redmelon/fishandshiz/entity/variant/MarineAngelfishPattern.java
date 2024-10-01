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

public record MarineAngelfishPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<MarineAngelfishPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final MarineAngelfishPattern ROYAL = create("royal", false);
    public static final MarineAngelfishPattern PULSED = create("pulsed", false);
    public static final MarineAngelfishPattern REGAL = create("regal", false);
    public static final MarineAngelfishPattern CONQUEROR = create("conqueror", false);

    public static final Registry<MarineAngelfishPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(MarineAngelfishPattern.class, new Identifier(FishAndShiz.MOD_ID, "marine_angelfish_pattern"), new Identifier(FishAndShiz.MOD_ID, "royal"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "marine_angelfish_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<MarineAngelfishPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static MarineAngelfishPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static MarineAngelfishPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static MarineAngelfishPattern create(String name, boolean empty) {
        MarineAngelfishPattern pattern = new MarineAngelfishPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/marine_angelfish/marine_angelfishpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<MarineAngelfishPattern> PATTERNS = of("patterns");

        private static TagKey<MarineAngelfishPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<MarineAngelfishPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
