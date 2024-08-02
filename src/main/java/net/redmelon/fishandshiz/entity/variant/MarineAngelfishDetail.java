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

public record MarineAngelfishDetail (@Nullable Identifier texture) implements GenericTextureProvider {
    private static final Map<MarineAngelfishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final MarineAngelfishDetail NONE = create("none", true);
    public static final MarineAngelfishDetail TRAIL = create("trail", false);
    public static final MarineAngelfishDetail TAIL = create("tail", false);
    public static final MarineAngelfishDetail HELMET = create("helmet", false);

    public static final Registry<MarineAngelfishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(MarineAngelfishDetail.class, new Identifier(FishAndShiz.MOD_ID, "marine_angelfish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "marine_angelfish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<MarineAngelfishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static MarineAngelfishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static MarineAngelfishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static MarineAngelfishDetail create(String name, boolean empty) {
        MarineAngelfishDetail detail = new MarineAngelfishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/marine_angelfish/marine_angelfishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<MarineAngelfishDetail> DETAILS = of("details");

        private static TagKey<MarineAngelfishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<MarineAngelfishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
