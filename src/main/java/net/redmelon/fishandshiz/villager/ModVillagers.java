package net.redmelon.fishandshiz.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.item.ModItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModVillagers {
    public static final PointOfInterestType HOPPER_POI = registerPOI("hopper_poi", Blocks.HOPPER);
    public static final VillagerProfession AQUARIST = registerProfession("aquarist",
            RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), new Identifier(FishAndShiz.MOD_ID, "hopper_poi")));

    public static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(FishAndShiz.MOD_ID, name),
                VillagerProfessionBuilder.create().id(new Identifier(FishAndShiz.MOD_ID, name)).workstation(type)
                        .workSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH).build());
    }

    public static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(FishAndShiz.MOD_ID, name),
                1, 1, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }

    public static void registerVillagers() {
        FishAndShiz.LOGGER.debug("Registering Villagers for " + FishAndShiz.MOD_ID);
    }

    private static final Map<Item, Integer> BUCKETABLES = new HashMap<>();
    static {
        // Vanilla
        BUCKETABLES.put(Items.TROPICAL_FISH_BUCKET, 5);

        // Adult buckets
        BUCKETABLES.put(ModItems.ANGELFISH_BUCKET, 5);
        BUCKETABLES.put(ModItems.NEON_TETRA_BUCKET, 3);
        BUCKETABLES.put(ModItems.AURATUS_BUCKET, 5);
        BUCKETABLES.put(ModItems.CORYDORAS_BUCKET, 3);
        BUCKETABLES.put(ModItems.RAINBOWFISH_BUCKET, 10);
        BUCKETABLES.put(ModItems.OSCAR_BUCKET, 12);
        BUCKETABLES.put(ModItems.AMUR_CARP_BUCKET, 7);
        BUCKETABLES.put(ModItems.GRAYLING_BUCKET, 8);
        BUCKETABLES.put(ModItems.ARCHERFISH_BUCKET, 10);
        BUCKETABLES.put(Items.SALMON_BUCKET, 6);
        BUCKETABLES.put(ModItems.MILKFISH_BUCKET, 7);
        BUCKETABLES.put(ModItems.MUD_CRAB, 12);
        BUCKETABLES.put(ModItems.GOATFISH_BUCKET, 10);
        BUCKETABLES.put(ModItems.CRAYFISH_BUCKET, 7);
        BUCKETABLES.put(ModItems.BETTA_BUCKET, 9);
        BUCKETABLES.put(ModItems.PLATY_BUCKET, 5);
        BUCKETABLES.put(ModItems.KILLIFISH_BUCKET, 9);
        BUCKETABLES.put(ModItems.CLOWNFISH_BUCKET, 5);
        BUCKETABLES.put(ModItems.TANG_BUCKET, 8);
        BUCKETABLES.put(ModItems.DOTTYBACK_BUCKET, 8);
        BUCKETABLES.put(ModItems.MARINE_ANGELFISH_BUCKET, 12);
        BUCKETABLES.put(ModItems.PARROTFISH_BUCKET, 12);
        BUCKETABLES.put(ModItems.BUTTERFLYFISH_BUCKET, 9);
        BUCKETABLES.put(ModItems.TRIGGERFISH_BUCKET, 11);
        BUCKETABLES.put(ModItems.MUDSKIPPER_BUCKET, 10);
        BUCKETABLES.put(ModItems.VOLCANO_SNAIL_BUCKET, 10);
        BUCKETABLES.put(ModItems.RAMSHORN_SNAIL_BUCKET, 3);

        // Fry buckets
        BUCKETABLES.put(ModItems.ANGELFISH_FRY_BUCKET, 2);
        BUCKETABLES.put(ModItems.NEON_TETRA_FRY_BUCKET, 1);
        BUCKETABLES.put(ModItems.AURATUS_FRY_BUCKET, 3);
        BUCKETABLES.put(ModItems.CORYDORAS_FRY_BUCKET, 1);
        BUCKETABLES.put(ModItems.RAINBOWFISH_FRY_BUCKET, 3);
        BUCKETABLES.put(ModItems.OSCAR_FRY_BUCKET, 5);
        BUCKETABLES.put(ModItems.AMUR_CARP_FRY_BUCKET, 3);
        BUCKETABLES.put(ModItems.GRAYLING_FRY_BUCKET, 3);
        BUCKETABLES.put(ModItems.SALMON_FRY_BUCKET, 5);
        BUCKETABLES.put(ModItems.MILKFISH_FRY_BUCKET, 3);
        BUCKETABLES.put(ModItems.MUD_CRAB_LARVA_BUCKET, 4);
        BUCKETABLES.put(ModItems.CRAYFISH_LARVA_BUCKET, 2);
        BUCKETABLES.put(ModItems.BETTA_FRY_BUCKET, 4);
        BUCKETABLES.put(ModItems.PLATY_FRY_BUCKET, 2);
        BUCKETABLES.put(ModItems.KILLIFISH_FRY_BUCKET, 4);
        BUCKETABLES.put(ModItems.CLOWNFISH_FRY_BUCKET, 3);
        BUCKETABLES.put(ModItems.TANG_FRY_BUCKET, 4);
        BUCKETABLES.put(ModItems.DOTTYBACK_FRY_BUCKET, 4);

        // Egg buckets
        BUCKETABLES.put(ModItems.ANGELFISH_EGG_BUCKET, 2);
        BUCKETABLES.put(ModItems.NEON_TETRA_EGG_BUCKET, 1);
        BUCKETABLES.put(ModItems.CORYDORAS_EGG_BUCKET, 1);
        BUCKETABLES.put(ModItems.RAINBOWFISH_EGG_BUCKET, 3);
        BUCKETABLES.put(ModItems.OSCAR_EGG_BUCKET, 5);
        BUCKETABLES.put(ModItems.AMUR_CARP_EGG_BUCKET, 3);
        BUCKETABLES.put(ModItems.GRAYLING_EGG_BUCKET, 3);
        BUCKETABLES.put(ModItems.SALMON_EGG_BUCKET, 5);
        BUCKETABLES.put(ModItems.MILKFISH_EGG_BUCKET, 3);
        BUCKETABLES.put(ModItems.BETTA_EGG_BUCKET, 4);
        BUCKETABLES.put(ModItems.KILLIFISH_EGG_BUCKET, 4);
        BUCKETABLES.put(ModItems.CLOWNFISH_EGG_BUCKET, 3);
        BUCKETABLES.put(ModItems.TANG_EGG_BUCKET, 4);
        BUCKETABLES.put(ModItems.DOTTYBACK_EGG_BUCKET, 4);
    }

    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.TROPICAL_FISH, 6),
                        new ItemStack(Items.EMERALD, 2),
                        8, 2, 0.02f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.WATER_BUCKET, 1),
                        new ItemStack(Items.EMERALD, 3),
                        8, 2, 0.02f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(ModItems.FANWORT, 20),
                        new ItemStack(Items.EMERALD, 2),
                        10, 1, 0.02f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(ModItems.FISHMEAL, 10),
                        new ItemStack(Items.EMERALD, 3),
                        10, 2, 0.02f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 3),
                        new ItemStack(ModItems.FISHMEAL, 9),
                        10, 2, 0.02f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.PRISMARINE_CRYSTALS, 15),
                        new ItemStack(Items.EMERALD, 3),
                        10, 2, 0.02f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.PRISMARINE_SHARD, 15),
                        new ItemStack(Items.EMERALD, 3),
                        10, 2, 0.02f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 2,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 5),
                        new ItemStack(ModItems.FANWORT, 2),
                        6, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 2,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 7),
                        new ItemStack(ModItems.VALLISNERIA, 2),
                        6, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 2,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 8),
                        new ItemStack(ModItems.AMAZON_SWORD, 1),
                        6, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 2,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 2),
                        new ItemStack(ModItems.FISH_FOOD, 6),
                        8, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 10),
                        new ItemStack(ModItems.ANUBIAS, 1),
                        4, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 4),
                        new ItemStack(ModItems.MONTE_CARLO, 2),
                        6, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 6),
                        new ItemStack(ModItems.DRIED_CULTURE_FEED, 3),
                        8, 2,0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 15),
                        new ItemStack(ModItems.SEA_ANEMONE, 1),
                        3, 4, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 8),
                        new ItemStack(Items.SEAGRASS, 2),
                        3, 4, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 10),
                        new ItemStack(Items.KELP, 2),
                        3, 4, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 5,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(ModItems.FANWORT, 32),
                        new ItemStack(Items.EMERALD, 6),
                        12, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 5,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(ModItems.AMAZON_SWORD, 6),
                        new ItemStack(Items.EMERALD, 4),
                        8, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 5,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(ModItems.ANUBIAS, 6),
                        new ItemStack(Items.EMERALD, 6),
                        6, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 5,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(ModItems.MONTE_CARLO, 16),
                        new ItemStack(Items.EMERALD, 6),
                        10, 2, 0.05f
                ))));

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 5,
                factories -> factories.add(((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 20),
                        new ItemStack(ModItems.FILTER, 1),
                        4, 5, 0.05f
                ))));

        // Villager selling bucketables
        for (int level = 2; level <= 3; level++) {
            int finalLevel = level;
            TradeOfferHelper.registerVillagerOffers(AQUARIST, level,
                    factories -> factories.add(((entity, random) -> {
                        List<Item> bucketList = new ArrayList<>(BUCKETABLES.keySet());
                        Item randomBucket = bucketList.get(random.nextInt(bucketList.size()));
                        int price = BUCKETABLES.get(randomBucket);
                        return new TradeOffer(
                                new ItemStack(Items.EMERALD, price),
                                new ItemStack(randomBucket, 1),
                                4, 3, 0.05f
                        );
                    })));
        }

        // Villager buying bucketables
        for (int level = 3; level <= 4; level++) {
            int finalLevel = level;
            TradeOfferHelper.registerVillagerOffers(AQUARIST, level,
                    factories -> factories.add(((entity, random) -> {
                        List<Item> bucketList = new ArrayList<>(BUCKETABLES.keySet());
                        Item randomBucket = bucketList.get(random.nextInt(bucketList.size()));
                        int price = BUCKETABLES.get(randomBucket);
                        return new TradeOffer(
                                new ItemStack(randomBucket, 1),
                                new ItemStack(Items.EMERALD, price),
                                6, 4, 0.05f
                        );
                    })));
        }
    }
}
