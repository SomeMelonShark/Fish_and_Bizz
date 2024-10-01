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

public record ParrotfishPattern (@Nullable Identifier texture) implements GenericTextureProvider {
    private static final Map<ParrotfishPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final ParrotfishPattern GLITTER = create("glitter", false);
    public static final ParrotfishPattern TAILGATE = create("tailgate", false);
    public static final ParrotfishPattern MASKED = create("masked", false);
    public static final ParrotfishPattern ECLIPSE = create("eclipse", false);

    public static final Registry<ParrotfishPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(ParrotfishPattern.class, new Identifier(FishAndShiz.MOD_ID, "parrotfish_pattern"), new Identifier(FishAndShiz.MOD_ID, "glitter"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "parrotfish_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<ParrotfishPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static ParrotfishPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static ParrotfishPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static ParrotfishPattern create(String name, boolean empty) {
        ParrotfishPattern pattern = new ParrotfishPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/parrotfish/parrotfishpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<ParrotfishPattern> PATTERNS = of("patterns");

        private static TagKey<ParrotfishPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<ParrotfishPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
