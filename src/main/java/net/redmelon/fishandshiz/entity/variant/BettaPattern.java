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

public record BettaPattern (@Nullable Identifier texture) {

    private static final Map<BettaPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final BettaPattern WILD1 = create("wild1", false);
    public static final BettaPattern WILD2 = create("wild2", false);
    public static final BettaPattern PANTS = create("pants", false);
    public static final BettaPattern VEIL = create("veil", false);

    public static final Registry<BettaPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(BettaPattern.class, new Identifier(FishAndShiz.MOD_ID, "betta_pattern"), new Identifier(FishAndShiz.MOD_ID, "wild1"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "betta_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<BettaPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static BettaPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static BettaPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static BettaPattern create(String name, boolean empty) {
        BettaPattern pattern = new BettaPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/betta/bettapa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<BettaPattern> PATTERNS = of("patterns");

        private static TagKey<BettaPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<BettaPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
