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

public record CorydorasDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<CorydorasDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final CorydorasDetail NONE = create("none", true);
    public static final CorydorasDetail LATERAL = create("lateral", false);
    public static final CorydorasDetail SPIRACLE = create("spiracle", false);

    public static final Registry<CorydorasDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(CorydorasDetail.class, new Identifier(FishAndShiz.MOD_ID, "corydoras_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "corydoras_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<CorydorasDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static CorydorasDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static CorydorasDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static CorydorasDetail create(String name, boolean empty) {
        CorydorasDetail detail = new CorydorasDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/corydoras/corydorasde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<CorydorasDetail> DETAILS = of("details");

        private static TagKey<CorydorasDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<CorydorasDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
