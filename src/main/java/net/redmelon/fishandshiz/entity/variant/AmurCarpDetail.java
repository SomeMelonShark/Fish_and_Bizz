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

public record AmurCarpDetail (@Nullable Identifier texture) implements GenericTextureProvider{

    private static final Map<AmurCarpDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final AmurCarpDetail NONE = create("none", true);
    public static final AmurCarpDetail FRECKLES = create("freckles", false);
    public static final AmurCarpDetail SPOTS = create("spots", false);
    public static final AmurCarpDetail MARKS = create("marks", false);
    public static final AmurCarpDetail BLOTCHES = create("blotches", false);

    public static final Registry<AmurCarpDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(AmurCarpDetail.class, new Identifier(FishAndShiz.MOD_ID, "amur_carp_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "amur_carp_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<AmurCarpDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static AmurCarpDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static AmurCarpDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static AmurCarpDetail create(String name, boolean empty) {
        AmurCarpDetail detail = new AmurCarpDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/amur_carp/amur_carpde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<AmurCarpDetail> DETAILS = of("details");

        private static TagKey<AmurCarpDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<AmurCarpDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
