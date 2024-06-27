package net.redmelon.fishandshiz.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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

    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> {
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(Items.TROPICAL_FISH_BUCKET, 1),
                    3, 3, 0.05f
            )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.TROPICAL_FISH, 6),
                            new ItemStack(Items.EMERALD, 2),
                            8, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.WATER_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 3),
                            8, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.FANWORT, 20),
                            new ItemStack(Items.EMERALD, 2),
                            10, 1, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.FISHMEAL, 10),
                            new ItemStack(Items.EMERALD, 3),
                            10, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.FISHMEAL, 9),
                            10, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.PRISMARINE_CRYSTALS, 15),
                            new ItemStack(Items.EMERALD, 3),
                            10, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 1,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.PRISMARINE_SHARD, 15),
                            new ItemStack(Items.EMERALD, 3),
                            10, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 2,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 5),
                            new ItemStack(ModItems.FANWORT, 2),
                            6, 2, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 2,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.FISH_FOOD, 6),
                            6, 2, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 2,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 8),
                            new ItemStack(ModItems.ANGELFISH_BUCKET, 1),
                            4, 3, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 2,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 6),
                            new ItemStack(ModItems.NEON_TETRA_BUCKET, 1),
                            4, 3, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 2,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 8),
                            new ItemStack(ModItems.CORYDORAS_BUCKET, 1),
                            4, 3, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 12),
                            new ItemStack(ModItems.AMUR_CARP_BUCKET, 1),
                            4, 3, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 8),
                            new ItemStack(ModItems.RAINBOWFISH_BUCKET, 1),
                            4, 3, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.SALMON_EGG_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 8),
                            6, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.SALMON_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 5),
                            6, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.SALMON_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 6),
                            6, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GRAYLING_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 8),
                            6, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GRAYLING_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 6),
                            6, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GRAYLING_EGG_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 9),
                            6, 2, 0.02f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.SEA_ANEMONE, 1),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 8),
                            new ItemStack(ModItems.VOLCANO_SNAIL_BUCKET, 1),
                            3, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 8),
                            new ItemStack(ModItems.CLOWNFISH_BUCKET, 1),
                            4, 3, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.ARCHERFISH_BUCKET, 1),
                            3, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 20),
                            new ItemStack(ModItems.OSCAR_BUCKET, 1),
                            3, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.GRAYLING_BUCKET, 1),
                            4, 3, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.ANGELFISH_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 4),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.ANGELFISH_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 3),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.ANGELFISH_EGG_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 6),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.NEON_TETRA_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 3),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.NEON_TETRA_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 2),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.NEON_TETRA_EGG_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 4),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.AURATUS_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 5),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.AURATUS_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 4),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.CORYDORAS_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 3),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.CORYDORAS_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 2),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.CORYDORAS_EGG_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 4),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAINBOWFISH_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 4),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAINBOWFISH_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 3),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAINBOWFISH_EGG_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 5),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.CLOWNFISH_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 6),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.CLOWNFISH_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 5),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.CLOWNFISH_EGG_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 7),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.OSCAR_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 8),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.OSCAR_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 7),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.OSCAR_EGG_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 9),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.AMUR_CARP_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 7),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.AMUR_CARP_FRY_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 6),
                            6, 4, 0.05f
                    )));
                });

        TradeOfferHelper.registerVillagerOffers(AQUARIST, 4,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.AMUR_CARP_EGG_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 8),
                            6, 4, 0.05f
                    )));
                });
    }
}
