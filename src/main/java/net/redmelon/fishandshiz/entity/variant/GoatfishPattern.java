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

public record GoatfishPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<GoatfishPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final GoatfishPattern SUSPENDERS = create("suspenders", false);
    public static final GoatfishPattern SPLIT = create("split", false);
    public static final GoatfishPattern UNDERBELLY = create("underbelly", false);
    public static final GoatfishPattern BICOLOR = create("bicolor", false);

    public static final Registry<GoatfishPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(GoatfishPattern.class, new Identifier(FishAndShiz.MOD_ID, "goatfish_pattern"), new Identifier(FishAndShiz.MOD_ID, "suspenders"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "goatfish_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<GoatfishPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static GoatfishPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static GoatfishPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static GoatfishPattern create(String name, boolean empty) {
        GoatfishPattern pattern = new GoatfishPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/goatfish/goatfishpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<GoatfishPattern> PATTERNS = of("patterns");

        private static TagKey<GoatfishPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<GoatfishPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
