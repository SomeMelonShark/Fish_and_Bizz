package net.redmelon.fishandshiz.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.entity.ModEntities;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.fabricmc.fabric.api.registry.VillagerInteractionRegistries.registerCompostable;

public class ModItems {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Item FISHMEAL = registerItem("fishmeal", new Item(new FabricItemSettings()));
    public static final Item FISH_FOOD = registerItem("fish_food", new Item(new FabricItemSettings()));
    public static final Item ARCHERFISH_SPAWN_EGG = registerItem("archerfish_spawn_egg",
            new SpawnEggItem(ModEntities.ARCHERFISH, 0xcdcc9d, 0x504848, new FabricItemSettings()));
    public static final Item MILKFISH_SPAWN_EGG = registerItem("milkfish_spawn_egg",
            new SpawnEggItem(ModEntities.MILKFISH, 0xc2d1e0, 0x79828b, new FabricItemSettings()));
    public static final Item GRAYLING_SPAWN_EGG = registerItem("grayling_spawn_egg",
            new SpawnEggItem(ModEntities.GRAYLING, 0x617558, 0x1e2d27, new FabricItemSettings()));
    public static final Item MUD_CRAB_SPAWN_EGG = registerItem("mud_crab_spawn_egg",
            new SpawnEggItem(ModEntities.MUD_CRAB, 0x2f5553, 0xab5315, new FabricItemSettings()));
    public static final Item LION_MANE_SPAWN_EGG = registerItem("lion_mane_spawn_egg",
            new SpawnEggItem(ModEntities.LION_MANE_JELLYFISH, 0xfc4b26, 0xeea663, new FabricItemSettings()));
    public static final Item VOLCANO_SNAIL_SPAWN_EGG = registerItem("volcano_snail_spawn_egg",
            new SpawnEggItem(ModEntities.VOLCANO_SNAIL, 0xd98fb0, 0x1a1614, new FabricItemSettings()));
    public static final Item CAPYBARA_SPAWN_EGG = registerItem("capybara_spawn_egg",
            new SpawnEggItem(ModEntities.CAPYBARA, 0x745a3e, 0xbd8244, new FabricItemSettings()));
    public static final Item FANWORT = registerItem("fanwort",
            new AliasedBlockItem(ModBlocks.FANWORT,new FabricItemSettings()));
    public static final Item ANGELFISH_BUCKET = registerItem("angelfish_bucket",
            new EntityBucketItem(ModEntities.ANGELFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item ANGELFISH_FRY_BUCKET = registerItem("angelfish_fry_bucket",
            new EntityBucketItem(ModEntities.ANGELFISH_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item ANGELFISH_EGG_BUCKET = registerItem("angelfish_egg_bucket",
            new EntityBucketItem(ModEntities.ANGELFISH_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item NEON_TETRA_BUCKET = registerItem("neon_tetra_bucket",
            new EntityBucketItem(ModEntities.NEON_TETRA, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item NEON_TETRA_FRY_BUCKET = registerItem("neon_tetra_fry_bucket",
            new EntityBucketItem(ModEntities.NEON_TETRA_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item NEON_TETRA_EGG_BUCKET = registerItem("neon_tetra_egg_bucket",
            new EntityBucketItem(ModEntities.NEON_TETRA_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item ARCHERFISH_BUCKET = registerItem("archerfish_bucket",
            new EntityBucketItem(ModEntities.ARCHERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item MILKFISH_BUCKET = registerItem("milkfish_bucket",
            new EntityBucketItem(ModEntities.MILKFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item MILKFISH_FRY_BUCKET = registerItem("milkfish_fry_bucket",
            new EntityBucketItem(ModEntities.MILKFISH_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item MILKFISH_EGG_BUCKET = registerItem("milkfish_egg_bucket",
            new EntityBucketItem(ModEntities.MILKFISH_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item CLOWNFISH_BUCKET = registerItem("clownfish_bucket",
            new EntityBucketItem(ModEntities.CLOWNFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item CLOWNFISH_FRY_BUCKET = registerItem("clownfish_fry_bucket",
            new EntityBucketItem(ModEntities.CLOWNFISH_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item CLOWNFISH_EGG_BUCKET = registerItem("clownfish_egg_bucket",
            new EntityBucketItem(ModEntities.CLOWNFISH_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item CORYDORAS_BUCKET = registerItem("corydoras_bucket",
            new EntityBucketItem(ModEntities.CORYDORAS, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item CORYDORAS_FRY_BUCKET = registerItem("corydoras_fry_bucket",
            new EntityBucketItem(ModEntities.CORYDORAS_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item CORYDORAS_EGG_BUCKET = registerItem("corydoras_egg_bucket",
            new EntityBucketItem(ModEntities.CORYDORAS_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item OSCAR_BUCKET = registerItem("oscar_bucket",
            new EntityBucketItem(ModEntities.OSCAR, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item OSCAR_FRY_BUCKET = registerItem("oscar_fry_bucket",
            new EntityBucketItem(ModEntities.OSCAR_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item OSCAR_EGG_BUCKET = registerItem("oscar_egg_bucket",
            new EntityBucketItem(ModEntities.OSCAR_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item RAINBOWFISH_BUCKET = registerItem("rainbowfish_bucket",
            new EntityBucketItem(ModEntities.RAINBOWFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item RAINBOWFISH_FRY_BUCKET = registerItem("rainbowfish_fry_bucket",
            new EntityBucketItem(ModEntities.RAINBOWFISH_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item RAINBOWFISH_EGG_BUCKET = registerItem("rainbowfish_egg_bucket",
            new EntityBucketItem(ModEntities.RAINBOWFISH_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item AURATUS_BUCKET = registerItem("auratus_bucket",
            new EntityBucketItem(ModEntities.AURATUS, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item AURATUS_FRY_BUCKET = registerItem("auratus_fry_bucket",
            new EntityBucketItem(ModEntities.AURATUS_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item AURATUS_EGG_BUCKET = registerItem("auratus_egg_bucket",
            new EntityBucketItem(ModEntities.AURATUS_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item GRAYLING_BUCKET = registerItem("grayling_bucket",
            new EntityBucketItem(ModEntities.GRAYLING, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item GRAYLING_FRY_BUCKET = registerItem("grayling_fry_bucket",
            new EntityBucketItem(ModEntities.GRAYLING_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item GRAYLING_EGG_BUCKET = registerItem("grayling_egg_bucket",
            new EntityBucketItem(ModEntities.GRAYLING_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item SALMON_FRY_BUCKET = registerItem("salmon_fry_bucket",
            new EntityBucketItem(ModEntities.SALMON_FRY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item SALMON_EGG_BUCKET = registerItem("salmon_egg_bucket",
            new EntityBucketItem(ModEntities.SALMON_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item MUD_CRAB_BUCKET = registerItem("mud_crab_bucket",
            new EntityBucketItem(ModEntities.MUD_CRAB, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, new FabricItemSettings().maxCount(1)));
    public static final Item MUD_CRAB_LARVA_BUCKET = registerItem("mud_crab_larva_bucket",
            new EntityBucketItem(ModEntities.MUD_CRAB_LARVA, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item MUD_CRAB_EGG_BUCKET = registerItem("mud_crab_egg_bucket",
            new EntityBucketItem(ModEntities.MUD_CRAB_EGG, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));
    public static final Item VOLCANO_SNAIL_BUCKET = registerItem("volcano_snail_bucket",
            new EntityBucketItem(ModEntities.VOLCANO_SNAIL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY, new FabricItemSettings().maxCount(1)));
    public static final Item MILKFISH = registerItem("milkfish",
            new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(2f).build())));
    public static final Item COOKED_MILKFISH = registerItem("cooked_milkfish",
            new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(5f).build())));
    public static final Item MUD_CRAB = registerItem("mud_crab",
            new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(1f).build())));
    public static final Item COOKED_MUD_CRAB = registerItem("cooked_mud_crab",
            new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(5).saturationModifier(5f).build())));
    public static final Item SUSHI = registerItem("sushi",
            new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(7).saturationModifier(5f).build())));
    public static final Item CORN_KERNELS = registerItem("corn_kernels",
            new AliasedBlockItem(ModBlocks.CORN_CROP, new FabricItemSettings()));
    public static final Item CORN = registerItem("corn",
            new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(3f).build())));
    public static final Item CORNBREAD = registerItem("cornbread",
            new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(8).saturationModifier(3f).build())));
    public static final Item SEA_ANEMONE = registerItem("sea_anemone",
            new AliasedBlockItem(ModBlocks.SEA_ANEMONE,new FabricItemSettings()));

    public static void init() {

        ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
        registerCompostable(CORN, 0.5f);
        registerCompostable(CORN_KERNELS, 0.3f);
        registerCompostable(FANWORT, 0.3f);

    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(FishAndShiz.MOD_ID, name), item);
    }
    public static void registerModitems(){
        FishAndShiz.LOGGER.debug("Registering Mod Items for "+ FishAndShiz.MOD_ID);
    }

    private static <T extends Item> void registerCompostable(T item, float chance){
        CompostingChanceRegistry.INSTANCE.add(item, chance);
    }
}
