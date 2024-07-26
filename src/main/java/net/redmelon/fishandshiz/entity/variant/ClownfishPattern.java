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

public record ClownfishPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<ClownfishPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final ClownfishPattern SADDLE = create("saddle", false);
    public static final ClownfishPattern SPILLED = create("spilled", false);
    public static final ClownfishPattern BARRED = create("barred", false);
    public static final ClownfishPattern CHEEK = create("cheek", false);

    public static final Registry<ClownfishPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(ClownfishPattern.class, new Identifier(FishAndShiz.MOD_ID, "clownfish_pattern"), new Identifier(FishAndShiz.MOD_ID, "saddle"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "clownfish_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<ClownfishPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static ClownfishPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static ClownfishPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static ClownfishPattern create(String name, boolean empty) {
        ClownfishPattern pattern = new ClownfishPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/clownfish/clownfishpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<ClownfishPattern> PATTERNS = of("patterns");

        private static TagKey<ClownfishPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<ClownfishPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}