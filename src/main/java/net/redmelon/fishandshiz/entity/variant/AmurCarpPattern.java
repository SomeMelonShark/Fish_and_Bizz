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

public record AmurCarpPattern (@Nullable Identifier texture) {

    private static final Map<AmurCarpPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final AmurCarpPattern WILD = create("wild", false);
    public static final AmurCarpPattern HELMET = create("helmet", false);
    public static final AmurCarpPattern MASK = create("mask", false);
    public static final AmurCarpPattern TRAIL = create("trail", false);

    public static final Registry<AmurCarpPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(AmurCarpPattern.class, new Identifier(FishAndShiz.MOD_ID, "amur_carp_pattern"), new Identifier(FishAndShiz.MOD_ID, "wild"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "amur_carp_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<AmurCarpPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static AmurCarpPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static AmurCarpPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static AmurCarpPattern create(String name, boolean empty) {
        AmurCarpPattern pattern = new AmurCarpPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/amur_carppa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<AmurCarpPattern> PATTERNS = of("patterns");

        private static TagKey<AmurCarpPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<AmurCarpPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
