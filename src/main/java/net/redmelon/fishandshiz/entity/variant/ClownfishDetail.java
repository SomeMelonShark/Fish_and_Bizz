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

public record ClownfishDetail (@Nullable Identifier texture) implements GenericTextureProvider {
    private static final Map<ClownfishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final ClownfishDetail NONE = create("none", true);
    public static final ClownfishDetail LOBED = create("lobed", false);
    public static final ClownfishDetail ACCENT = create("accent", false);

    public static final Registry<ClownfishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(ClownfishDetail.class, new Identifier(FishAndShiz.MOD_ID, "clownfish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "clownfish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<ClownfishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static ClownfishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static ClownfishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static ClownfishDetail create(String name, boolean empty) {
        ClownfishDetail detail = new ClownfishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/clownfish/clownfishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<ClownfishDetail> DETAILS = of("details");

        private static TagKey<ClownfishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<ClownfishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
