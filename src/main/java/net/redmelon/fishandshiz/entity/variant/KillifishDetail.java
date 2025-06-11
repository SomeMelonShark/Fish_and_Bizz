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

public record KillifishDetail (@Nullable Identifier texture) implements GenericTextureProvider{

    private static final Map<KillifishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final KillifishDetail NONE = create("none", true);
    public static final KillifishDetail FLARE = create("flare", false);
    public static final KillifishDetail AFTERBURN = create("afterburn", false);
    public static final KillifishDetail WAKE = create("wake", false);
    public static final KillifishDetail STARRY = create("starry", false);

    public static final Registry<KillifishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(KillifishDetail.class, new Identifier(FishAndShiz.MOD_ID, "killifish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "killifish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<KillifishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static KillifishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static KillifishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static KillifishDetail create(String name, boolean empty) {
        KillifishDetail detail = new KillifishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/killifish/killifishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<KillifishDetail> DETAILS = of("details");

        private static TagKey<KillifishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<KillifishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
