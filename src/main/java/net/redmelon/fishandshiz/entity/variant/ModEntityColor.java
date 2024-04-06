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

public record ModEntityColor(int color) {
    private static final Map<ModEntityColor, Identifier> COLORS = new LinkedHashMap<>();

    public static final ModEntityColor SILVER = create("silver", 0xffeae5);
    public static final ModEntityColor MATTE_BLACK = create("matte_black", 0x241e1e);
    public static final ModEntityColor EARTH_BLACK = create("earth_black", 0x1a1111);
    public static final ModEntityColor MUDDY = create("muddy", 0xb4956e);
    public static final ModEntityColor BRED = create("bred", 0x592213);
    public static final ModEntityColor BROWN = create("brown", 0x421e00);
    public static final ModEntityColor LIGHT_BLUE = create("light_blue", 0x00aae5);
    public static final ModEntityColor NEON_BLUE = create("neon_blue", 0x84ab1);
    public static final ModEntityColor ORANGE = create("orange", 0xf47500);
    public static final ModEntityColor RERANGE = create("rerange", 0xf43700);
    public static final ModEntityColor DEEP_RED = create("deep_red", 0xf40000);
    public static final ModEntityColor YELLOW = create("yellow", 0xf1c91f);
    public static final ModEntityColor OFF_YELLOW = create("off_yellow", 0xeee4a6);
    public static final ModEntityColor GOLD = create("gold", 0xc58921);
    public static final ModEntityColor CREAM = create("cream", 0xdfd5b6);
    public static final ModEntityColor MATTE_ORANGE = create("matte_orange", 0x7f3d00);
    public static final ModEntityColor GREEN = create("green", 0x426b1b);
    public static final ModEntityColor BILE = create("bile", 0x006700);
    public static final ModEntityColor SICKLY = create("sickly", 0x638672);
    public static final ModEntityColor PURPLE = create("purple", 0xb559a1);

    public static final Registry<ModEntityColor> REGISTRY = FabricRegistryBuilder
            .createDefaulted(ModEntityColor.class, new Identifier(FishAndShiz.MOD_ID, "entity_color"), new Identifier(FishAndShiz.MOD_ID, "silver"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static final TrackedDataHandler<ModEntityColor> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public String getTranslationKey() {
        return "entity_color." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static ModEntityColor fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static ModEntityColor fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static ModEntityColor create(String name, int color) {
        ModEntityColor modEntityColor = new ModEntityColor(color);
        COLORS.put(modEntityColor, new Identifier(FishAndShiz.MOD_ID, name));
        return modEntityColor;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        COLORS.keySet().forEach(color -> Registry.register(REGISTRY, COLORS.get(color), color));
    }

    public static class Tag {

        public static final TagKey<ModEntityColor> BASE_COLORS = of("base_colors");
        public static final TagKey<ModEntityColor> PATTERN_COLORS = of("pattern_colors");
        public static final TagKey<ModEntityColor> DETAIL_COLORS = of("detail_colors");

        private static TagKey<ModEntityColor> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<ModEntityColor> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
