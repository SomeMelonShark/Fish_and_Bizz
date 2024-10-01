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

public record ParrotfishDetail (@Nullable Identifier texture) implements GenericTextureProvider {

    private static final Map<ParrotfishDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final ParrotfishDetail NONE = create("none", true);
    public static final ParrotfishDetail NOSETRIP = create("nosetrip", false);
    public static final ParrotfishDetail CHEEKY = create("cheeky", false);

    public static final Registry<ParrotfishDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(ParrotfishDetail.class, new Identifier(FishAndShiz.MOD_ID, "parrotfish_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "parrotfish_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<ParrotfishDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static ParrotfishDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static ParrotfishDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static ParrotfishDetail create(String name, boolean empty) {
        ParrotfishDetail detail = new ParrotfishDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/parrotfish/parrotfishde_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<ParrotfishDetail> DETAILS = of("details");

        private static TagKey<ParrotfishDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<ParrotfishDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
