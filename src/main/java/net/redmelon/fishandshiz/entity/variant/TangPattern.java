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

public record TangPattern (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<TangPattern, Identifier> PATTERNS = new LinkedHashMap<>();
    public static final TangPattern GIBBOUS = create("gibbous", false);
    public static final TangPattern RIPPLE = create("ripple", false);
    public static final TangPattern WAVE = create("wave", false);
    public static final TangPattern BACKSPLASH = create("backsplash", false);

    public static final Registry<TangPattern> REGISTRY = FabricRegistryBuilder
            .createDefaulted(TangPattern.class, new Identifier(FishAndShiz.MOD_ID, "tang_pattern"), new Identifier(FishAndShiz.MOD_ID, "gibbous"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "tang_pattern." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<TangPattern> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static TangPattern fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static TangPattern fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static TangPattern create(String name, boolean empty) {
        TangPattern pattern = new TangPattern(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/tang/tangpa_" + name + ".png"));
        PATTERNS.put(pattern, new Identifier(FishAndShiz.MOD_ID, name));
        return pattern;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        PATTERNS.keySet().forEach(variant -> Registry.register(REGISTRY, PATTERNS.get(variant), variant));
    }

    public static class Tag {
        public static final TagKey<TangPattern> PATTERNS = of("patterns");

        private static TagKey<TangPattern> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<TangPattern> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
