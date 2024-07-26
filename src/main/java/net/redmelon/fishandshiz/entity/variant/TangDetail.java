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

public record TangDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<TangDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final TangDetail NONE = create("none", true);
    public static final TangDetail MASK = create("mask", false);
    public static final TangDetail SPOT = create("spot", false);
    public static final TangDetail MOORED = create("moored", false);

    public static final Registry<TangDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(TangDetail.class, new Identifier(FishAndShiz.MOD_ID, "tang_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "tang_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<TangDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static TangDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static TangDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static TangDetail create(String name, boolean empty) {
        TangDetail detail = new TangDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/tang/tangde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<TangDetail> DETAILS = of("details");

        private static TagKey<TangDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<TangDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
