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

public record RainbowfishDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<RainbowfishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final RainbowfishDetail NONE = create("none", true);
    public static final RainbowfishDetail BREASTY = create("breasty", false);
    public static final RainbowfishDetail LATERAL = create("lateral", false);

    public static final Registry<RainbowfishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(RainbowfishDetail.class, new Identifier(FishAndShiz.MOD_ID, "rainbowfish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "rainbowfish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<RainbowfishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static RainbowfishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static RainbowfishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static RainbowfishDetail create(String name, boolean empty) {
        RainbowfishDetail detail = new RainbowfishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/rainbowfish/rainbowfishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<RainbowfishDetail> DETAILS = of("details");

        private static TagKey<RainbowfishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<RainbowfishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
