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

public record GoldfishDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<GoldfishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final GoldfishDetail NONE = create("none", true);
    public static final GoldfishDetail BELLIED = create("bellied", false);
    public static final GoldfishDetail TWINTAILED1 = create("twintailed1", false);
    public static final GoldfishDetail TWINTAILED2 = create("twintailed2", false);
    public static final GoldfishDetail ASTRAL = create("astral", false);

    public static final Registry<GoldfishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(GoldfishDetail.class, new Identifier(FishAndShiz.MOD_ID, "goldfish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "goldfish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<GoldfishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static GoldfishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static GoldfishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static GoldfishDetail create(String name, boolean empty) {
        GoldfishDetail detail = new GoldfishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/goldfish/goldfishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<GoldfishDetail> DETAILS = of("details");

        private static TagKey<GoldfishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<GoldfishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
