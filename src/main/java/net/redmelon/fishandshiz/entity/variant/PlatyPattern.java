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

public record PlatyPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<PlatyPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final PlatyPattern FINISH = create("finish", false);
    public static final PlatyPattern TRANSVERSE = create("transverse", false);
    public static final PlatyPattern LATERAL = create("lateral", false);
    public static final PlatyPattern PANTS = create("pants", false);
    public static final PlatyPattern HELMET = create("helmet", false);

    public static final Registry<PlatyPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(PlatyPattern.class, new Identifier(FishAndShiz.MOD_ID, "platy_pattern"), new Identifier(FishAndShiz.MOD_ID, "finish"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "platy_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<PlatyPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static PlatyPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static PlatyPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static PlatyPattern create(String name, boolean empty) {
        PlatyPattern pattern = new PlatyPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/platy/platypa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<PlatyPattern> PATTERNS = of("patterns");

        private static TagKey<PlatyPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<PlatyPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
