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

public record TriggerfishPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<TriggerfishPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final TriggerfishPattern SUNSPOT = create("sunspot", false);
    public static final TriggerfishPattern GARTER = create("garter", false);
    public static final TriggerfishPattern TRENCH = create("trench", false);
    public static final TriggerfishPattern BACKDROP = create("backdrop", false);

    public static final Registry<TriggerfishPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(TriggerfishPattern.class, new Identifier(FishAndShiz.MOD_ID, "triggerfish_pattern"), new Identifier(FishAndShiz.MOD_ID, "sunspot"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "triggerfish_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<TriggerfishPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static TriggerfishPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static TriggerfishPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static TriggerfishPattern create(String name, boolean empty) {
        TriggerfishPattern pattern = new TriggerfishPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/triggerfish/triggerfishpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<TriggerfishPattern> PATTERNS = of("patterns");

        private static TagKey<TriggerfishPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<TriggerfishPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
