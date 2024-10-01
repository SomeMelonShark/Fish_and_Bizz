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

public record PlatyDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<PlatyDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final PlatyDetail NONE = create("none", true);
    public static final PlatyDetail BRACE = create("brace", false);
    public static final PlatyDetail TAIL = create("tail", false);

    public static final Registry<PlatyDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(PlatyDetail.class, new Identifier(FishAndShiz.MOD_ID, "platy_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "platy_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<PlatyDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static PlatyDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static PlatyDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static PlatyDetail create(String name, boolean empty) {
        PlatyDetail detail = new PlatyDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/platy/platyde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<PlatyDetail> DETAILS = of("details");

        private static TagKey<PlatyDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<PlatyDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
