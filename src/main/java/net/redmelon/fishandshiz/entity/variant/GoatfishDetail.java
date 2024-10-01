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

public record GoatfishDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<GoatfishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final GoatfishDetail NONE = create("none", true);
    public static final GoatfishDetail STACCATO = create("staccato", false);
    public static final GoatfishDetail DRIBBLES = create("dribbles", false);

    public static final Registry<GoatfishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(GoatfishDetail.class, new Identifier(FishAndShiz.MOD_ID, "goatfish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "goatfish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<GoatfishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static GoatfishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static GoatfishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static GoatfishDetail create(String name, boolean empty) {
        GoatfishDetail detail = new GoatfishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/goatfish/goatfishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<GoatfishDetail> DETAILS = of("details");

        private static TagKey<GoatfishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<GoatfishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
