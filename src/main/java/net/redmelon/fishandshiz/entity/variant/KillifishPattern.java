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

public record KillifishPattern (@Nullable Identifier texture) implements GenericTextureProvider{

    private static final Map<KillifishPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final KillifishPattern GUNTY = create("gunty", false);
    public static final KillifishPattern LYRE = create("lyre", false);
    public static final KillifishPattern CLOWN = create("clown", false);
    public static final KillifishPattern LONG = create("long", false);
    public static final KillifishPattern HAT = create("hat", false);

    public static final Registry<KillifishPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(KillifishPattern.class, new Identifier(FishAndShiz.MOD_ID, "killifish_pattern"), new Identifier(FishAndShiz.MOD_ID, "gunty"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "killifish_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<KillifishPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static KillifishPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static KillifishPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static KillifishPattern create(String name, boolean empty) {
        KillifishPattern pattern = new KillifishPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/killifish/killifishpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<KillifishPattern> PATTERNS = of("patterns");

        private static TagKey<KillifishPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<KillifishPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
