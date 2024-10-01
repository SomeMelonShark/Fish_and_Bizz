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

public record TriggerfishDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<TriggerfishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final TriggerfishDetail NONE = create("none", true);
    public static final TriggerfishDetail BRUX = create("brux", false);
    public static final TriggerfishDetail BEARD = create("beard", false);

    public static final Registry<TriggerfishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(TriggerfishDetail.class, new Identifier(FishAndShiz.MOD_ID, "triggerfish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "triggerfish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<TriggerfishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static TriggerfishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static TriggerfishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static TriggerfishDetail create(String name, boolean empty) {
        TriggerfishDetail detail = new TriggerfishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/triggerfish/triggerfishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<TriggerfishDetail> DETAILS = of("details");

        private static TagKey<TriggerfishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<TriggerfishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
