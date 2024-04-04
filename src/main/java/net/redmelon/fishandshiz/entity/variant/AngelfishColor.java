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

    public static final AngelfishColor SILVER = create("silver", 0xffeae5);
    public static final AngelfishColor MATTE_BLACK = create("matte_black", 0x241e1e);
    public static final AngelfishColor MUDDY = create("muddy", 0x8d7b65);
    public static final AngelfishColor BRED = create("bred", 0x592213);
    public static final AngelfishColor BROWN = create("brown", 0x421e00);
    public static final AngelfishColor LIGHT_BLUE = create("light_blue", 0x00aae5);
    public static final AngelfishColor NEON_BLUE = create("neon_blue", 0x84ab1);
    public static final AngelfishColor ORANGE = create("orange", 0xf47500);
    public static final AngelfishColor RERANGE = create("rerange", 0xf43700);
    public static final AngelfishColor DEEP_RED = create("deep_red", 0xf40000);
    public static final AngelfishColor YELLOW = create("yellow", 0xfde005);
    public static final AngelfishColor GOLD = create("gold", 0xc58921);
    public static final AngelfishColor MATTE_ORANGE = create("matte_orange", 0x7f3d00);
    public static final AngelfishColor BILE = create("bile", 0x006700);
    public static final AngelfishColor SICKLY = create("sickly", 0x638672);
    public static final AngelfishColor PURPLE = create("purple", 0xb559a1);

    public static final Registry<AngelfishColor> REGISTRY = FabricRegistryBuilder
            .createDefaulted(AngelfishColor.class, new Identifier(FishAndShiz.MOD_ID, "angelfish_color"), new Identifier(FishAndShiz.MOD_ID, "silver"))
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
