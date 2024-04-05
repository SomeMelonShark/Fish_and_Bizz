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

public record AngelfishDetail (@Nullable Identifier texture){

    private static final Map<AngelfishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final AngelfishDetail NONE = create("none", true);
    public static final AngelfishDetail CROWN = create("crown", false);
    public static final AngelfishDetail ACCENT = create("accent", false);

    public static final Registry<AngelfishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(AngelfishDetail.class, new Identifier(FishAndShiz.MOD_ID, "angelfish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "angelfish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<AngelfishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static AngelfishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static AngelfishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static AngelfishDetail create(String name, boolean empty) {
        AngelfishDetail detail = new AngelfishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/angelfishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<AngelfishDetail> DETAILS = of("details");

        private static TagKey<AngelfishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<AngelfishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
