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

public record DottybackDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<DottybackDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final DottybackDetail NONE = create("none", true);
    public static final DottybackDetail EDGE = create("edge", false);
    public static final DottybackDetail EYESPOT = create("eyespot", false);

    public static final Registry<DottybackDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(DottybackDetail.class, new Identifier(FishAndShiz.MOD_ID, "dottyback_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "dottyback_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<DottybackDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static DottybackDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static DottybackDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static DottybackDetail create(String name, boolean empty) {
        DottybackDetail detail = new DottybackDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/dottyback/dottybackde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<DottybackDetail> DETAILS = of("details");

        private static TagKey<DottybackDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<DottybackDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
