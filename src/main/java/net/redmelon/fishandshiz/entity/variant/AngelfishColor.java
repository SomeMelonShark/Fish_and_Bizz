package net.redmelon.fishandshiz.entity.variant;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;

import java.util.LinkedHashMap;
import java.util.Map;

public record AngelfishColor(int color) {
    private static final Map<AngelfishColor, Identifier> COLORS = new LinkedHashMap<>();

    public static final AngelfishColor IVORY = create("ivory", 0xFFFFF0);
    public static final AngelfishColor ONYX = create("onyx", 0x353839);
    public static final AngelfishColor PERIWINKLE = create("periwinkle", 0xCCCCFF);

    public static final Registry<AngelfishColor> REGISTRY = FabricRegistryBuilder
            .createDefaulted(AngelfishColor.class, new Identifier(FishAndShiz.MOD_ID, "angelfish_color"), new Identifier(FishAndShiz.MOD_ID, "ivory"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static final TrackedDataHandler<AngelfishColor> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public String getTranslationKey() {
        return "angelfish_color." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static AngelfishColor fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static AngelfishColor fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static AngelfishColor create(String name, int color) {
        AngelfishColor angelfishColor = new AngelfishColor(color);
        COLORS.put(angelfishColor, new Identifier(FishAndShiz.MOD_ID, name));
        return angelfishColor;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        COLORS.keySet().forEach(color -> Registry.register(REGISTRY, COLORS.get(color), color));
    }

    public static class Tag {

        public static final TagKey<AngelfishColor> BASE_COLORS = of("base_colors");
        public static final TagKey<AngelfishColor> PATTERN_COLORS = of("pattern_colors");

        private static TagKey<AngelfishColor> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<AngelfishColor> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
