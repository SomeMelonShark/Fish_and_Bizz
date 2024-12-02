package net.redmelon.fishandshiz.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.ModBlocks;

public class ModItemGroup {
    /**Test Group**/
    public static ItemGroup FISH_AND_BIZZ = Registry.register(Registries.ITEM_GROUP, new Identifier(FishAndShiz.MOD_ID, "fishandshiz"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.fishandshiz"))
                    .icon(() -> new ItemStack(ModItems.FISH_FOOD)).entries((displayContext, entries) -> {

                    }).build());

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(ModItems.CULTURE_FEED_BUCKET);
            entries.add(ModItems.ANGELFISH_BUCKET);
            entries.add(ModItems.ANGELFISH_FRY_BUCKET);
            entries.add(ModItems.ANGELFISH_EGG_BUCKET);
            entries.add(ModItems.ARCHERFISH_BUCKET);
            entries.add(ModItems.MILKFISH_BUCKET);
            entries.add(ModItems.MILKFISH_FRY_BUCKET);
            entries.add(ModItems.MILKFISH_EGG_BUCKET);
            entries.add(ModItems.NEON_TETRA_BUCKET);
            entries.add(ModItems.NEON_TETRA_FRY_BUCKET);
            entries.add(ModItems.NEON_TETRA_EGG_BUCKET);
            entries.add(ModItems.CORYDORAS_BUCKET);
            entries.add(ModItems.CORYDORAS_FRY_BUCKET);
            entries.add(ModItems.CORYDORAS_EGG_BUCKET);
            entries.add(ModItems.OSCAR_BUCKET);
            entries.add(ModItems.OSCAR_FRY_BUCKET);
            entries.add(ModItems.OSCAR_EGG_BUCKET);
            entries.add(ModItems.GRAYLING_FRY_BUCKET);
            entries.add(ModItems.GRAYLING_EGG_BUCKET);
            entries.add(ModItems.GOATFISH_BUCKET);
            entries.add(ModItems.SALMON_FRY_BUCKET);
            entries.add(ModItems.SALMON_EGG_BUCKET);
            entries.add(ModItems.MUD_CRAB_BUCKET);
            entries.add(ModItems.MUD_CRAB_LARVA_BUCKET);
            entries.add(ModItems.CRAYFISH_BUCKET);
            entries.add(ModItems.CRAYFISH_LARVA_BUCKET);
            entries.add(ModItems.RAINBOWFISH_BUCKET);
            entries.add(ModItems.RAINBOWFISH_FRY_BUCKET);
            entries.add(ModItems.RAINBOWFISH_EGG_BUCKET);
            entries.add(ModItems.AURATUS_BUCKET);
            entries.add(ModItems.AURATUS_FRY_BUCKET);
            entries.add(ModItems.AMUR_CARP_BUCKET);
            entries.add(ModItems.AMUR_CARP_FRY_BUCKET);
            entries.add(ModItems.AMUR_CARP_EGG_BUCKET);
            entries.add(ModItems.BETTA_BUCKET);
            entries.add(ModItems.BETTA_FRY_BUCKET);
            entries.add(ModItems.BETTA_EGG_BUCKET);
            entries.add(ModItems.PLATY_BUCKET);
            entries.add(ModItems.PLATY_FRY_BUCKET);
            entries.add(ModItems.CLOWNFISH_BUCKET);
            entries.add(ModItems.CLOWNFISH_FRY_BUCKET);
            entries.add(ModItems.CLOWNFISH_EGG_BUCKET);
            entries.add(ModItems.GRAYLING_BUCKET);
            entries.add(ModItems.TANG_BUCKET);
            entries.add(ModItems.TANG_FRY_BUCKET);
            entries.add(ModItems.TANG_EGG_BUCKET);
            entries.add(ModItems.DOTTYBACK_BUCKET);
            entries.add(ModItems.DOTTYBACK_FRY_BUCKET);
            entries.add(ModItems.DOTTYBACK_EGG_BUCKET);
            entries.add(ModItems.MARINE_ANGELFISH_BUCKET);
            entries.add(ModItems.PARROTFISH_BUCKET);
            entries.add(ModItems.BUTTERFLYFISH_BUCKET);
            entries.add(ModItems.TRIGGERFISH_BUCKET);
            entries.add(ModItems.VOLCANO_SNAIL_BUCKET);
            entries.add(ModItems.VOLCANO_SNAIL_EGG_BUCKET);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(ModItems.ARCHERFISH_GUN);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(ModItems.ARCHERFISH_SPAWN_EGG);
            entries.add(ModItems.CAPYBARA_SPAWN_EGG);
            entries.add(ModItems.MILKFISH_SPAWN_EGG);
            entries.add(ModItems.LION_MANE_SPAWN_EGG);
            entries.add(ModItems.MUD_CRAB_SPAWN_EGG);
            entries.add(ModItems.CRAYFISH_SPAWN_EGG);
            entries.add(ModItems.VOLCANO_SNAIL_SPAWN_EGG);
            entries.add(ModItems.GRAYLING_SPAWN_EGG);
            entries.add(ModItems.RED_TAIL_CATFISH_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(ModItems.MILKFISH);
            entries.add(ModItems.COOKED_MILKFISH);
            entries.add(ModItems.MUD_CRAB);
            entries.add(ModItems.COOKED_MUD_CRAB);
            entries.add(ModItems.SUSHI);
            entries.add(ModItems.CORN);
            entries.add(ModItems.CORNBREAD);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ModItems.DRIED_CULTURE_FEED);
            entries.add(ModItems.FISH_FOOD);
            entries.add(ModItems.FISHMEAL);
            entries.add(ModBlocks.FISHMEAL_BLOCK);
            entries.add(ModItems.DRIED_MULM);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(ModBlocks.MULM);
            entries.add(ModItems.FANWORT);
            entries.add(ModItems.VALLISNERIA);
            entries.add(ModItems.MONTE_CARLO);
            entries.add(ModItems.AMAZON_SWORD);
            entries.add(ModItems.POTHOS);
            entries.add(ModBlocks.ANUBIAS);
            entries.add(ModItems.CORN_KERNELS);
            entries.add(ModItems.SEA_ANEMONE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.POWERED_PRISMARINE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(ModItems.NITROGEN_DETECTOR);
        });
    }
}
