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

public record ButterflyfishDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<ButterflyfishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final ButterflyfishDetail NONE = create("none", true);
    public static final ButterflyfishDetail EYEGASH = create("eyegash", false);
    public static final ButterflyfishDetail EYESPOT = create("eyespot", false);

    public static final Registry<ButterflyfishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(ButterflyfishDetail.class, new Identifier(FishAndShiz.MOD_ID, "butterflyfish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "butterflyfish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<ButterflyfishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static ButterflyfishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static ButterflyfishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static ButterflyfishDetail create(String name, boolean empty) {
        ButterflyfishDetail detail = new ButterflyfishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/butterflyfish/butterflyfishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<ButterflyfishDetail> DETAILS = of("details");

        private static TagKey<ButterflyfishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<ButterflyfishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
