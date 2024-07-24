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

public record BettaDetail (@Nullable Identifier texture) implements GenericTextureProvider{

    private static final Map<BettaDetail, Identifier> DETAILS = new LinkedHashMap<>();
    public static final BettaDetail NONE = create("none", true);
    public static final BettaDetail SPECKLED = create("speckled", false);
    public static final BettaDetail SCALAR = create("scalar", false);
    public static final BettaDetail DECAL = create("decal", false);

    public static final Registry<BettaDetail> REGISTRY = FabricRegistryBuilder
            .createDefaulted(BettaDetail.class, new Identifier(FishAndShiz.MOD_ID, "betta_detail"), new Identifier(FishAndShiz.MOD_ID, "none"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public String getTranslationKey() {
        return "betta_detail." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public static final TrackedDataHandler<BettaDetail> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static BettaDetail fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static BettaDetail fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static BettaDetail create(String name, boolean empty) {
        BettaDetail detail = new BettaDetail(empty ? null : new Identifier(FishAndShiz.MOD_ID, "textures/entity/fish/betta/bettade_" + name + ".png"));
        DETAILS.put(detail, new Identifier(FishAndShiz.MOD_ID, name));
        return detail;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        DETAILS.keySet().forEach(variant -> Registry.register(REGISTRY, DETAILS.get(variant), variant));

    }
    public static class Tag {
        public static final TagKey<BettaDetail> DETAILS = of("details");

        private static TagKey<BettaDetail> of(String id) {
            return of(new Identifier(FishAndShiz.MOD_ID, id));
        }

        public static TagKey<BettaDetail> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
