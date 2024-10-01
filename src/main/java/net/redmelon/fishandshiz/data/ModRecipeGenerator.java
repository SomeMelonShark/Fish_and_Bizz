package net.redmelon.fishandshiz.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.ModBlocks;
import net.redmelon.fishandshiz.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.FISHMEAL,
                RecipeCategory.MISC, ModBlocks.FISHMEAL_BLOCK);

        offerSmelting(exporter, List.of(ModItems.MILKFISH), RecipeCategory.FOOD, ModItems.COOKED_MILKFISH,
                0.35f, 200, "food");

        offerSmelting(exporter, List.of(ModItems.MUD_CRAB), RecipeCategory.FOOD, ModItems.COOKED_MUD_CRAB,
                0.35f, 200, "food");

        offerSmelting(exporter, List.of(ModBlocks.MULM), RecipeCategory.MISC, ModItems.DRIED_MULM,
                0.5f, 200, "misc");

        offerSmelting(exporter, List.of(ModItems.CULTURE_FEED_BUCKET), RecipeCategory.MISC, ModItems.DRIED_CULTURE_FEED,
                0.20f, 200, "misc");

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CORN_KERNELS, 3)
                .input(ModItems.CORN)
                .group("food")
                .criterion(FabricRecipeProvider.hasItem(ModItems.CORN), FabricRecipeProvider.conditionsFromItem(ModItems.CORN))
                .offerTo(exporter, convertBetween(ModItems.CORN_KERNELS, ModItems.CORN));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISHMEAL, 3)
                .input(Items.BONE_MEAL)
                .input(ModItems.COOKED_MILKFISH)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(Items.BONE_MEAL), FabricRecipeProvider.conditionsFromItem(Items.BONE_MEAL))
                .offerTo(exporter, convertBetween(ModItems.FISHMEAL, ModItems.COOKED_MILKFISH));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISHMEAL, 3)
                .input(Items.BONE_MEAL)
                .input(Items.COOKED_COD)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(Items.BONE_MEAL), FabricRecipeProvider.conditionsFromItem(Items.BONE_MEAL))
                .offerTo(exporter, convertBetween(ModItems.FISHMEAL, Items.COOKED_COD));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISHMEAL, 3)
                .input(Items.BONE_MEAL)
                .input(Items.COOKED_SALMON)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(Items.BONE_MEAL), FabricRecipeProvider.conditionsFromItem(Items.BONE_MEAL))
                .offerTo(exporter, convertBetween(ModItems.FISHMEAL, Items.COOKED_SALMON));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISH_FOOD, 3)
                .input(Items.COOKED_CHICKEN)
                .input(ModItems.FISHMEAL)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(ModItems.FISHMEAL), FabricRecipeProvider.conditionsFromItem(ModItems.FISHMEAL))
                .offerTo(exporter, convertBetween(ModItems.FISH_FOOD, Items.COOKED_CHICKEN));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISH_FOOD, 3)
                .input(Items.COOKED_BEEF)
                .input(ModItems.FISHMEAL)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(ModItems.FISHMEAL), FabricRecipeProvider.conditionsFromItem(ModItems.FISHMEAL))
                .offerTo(exporter, convertBetween(ModItems.FISH_FOOD, Items.COOKED_BEEF));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISH_FOOD, 3)
                .input(Items.COOKED_PORKCHOP)
                .input(ModItems.FISHMEAL)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(ModItems.FISHMEAL), FabricRecipeProvider.conditionsFromItem(ModItems.FISHMEAL))
                .offerTo(exporter, convertBetween(ModItems.FISH_FOOD, Items.COOKED_PORKCHOP));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISH_FOOD, 3)
                .input(Items.COOKED_MUTTON)
                .input(ModItems.FISHMEAL)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(ModItems.FISHMEAL), FabricRecipeProvider.conditionsFromItem(ModItems.FISHMEAL))
                .offerTo(exporter, convertBetween(ModItems.FISH_FOOD, Items.COOKED_MUTTON));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CORNBREAD)
                .pattern("ABA")
                .input('A', ModItems.CORN)
                .input('B', Items.SUGAR)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CORN), FabricRecipeProvider.conditionsFromItem(ModItems.CORN))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SUSHI, 2)
                .input(Items.WHEAT)
                .input(ModItems.MILKFISH)
                .input(Items.DRIED_KELP)
                .group("food")
                .criterion(FabricRecipeProvider.hasItem(ModItems.MILKFISH), FabricRecipeProvider.conditionsFromItem(ModItems.MILKFISH))
                .offerTo(exporter, convertBetween(ModItems.SUSHI, ModItems.MILKFISH));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SUSHI, 2)
                .input(Items.WHEAT)
                .input(Items.SALMON)
                .input(Items.DRIED_KELP)
                .group("food")
                .criterion(FabricRecipeProvider.hasItem(Items.SALMON), FabricRecipeProvider.conditionsFromItem(Items.SALMON))
                .offerTo(exporter, convertBetween(ModItems.SUSHI, Items.SALMON));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SUSHI, 2)
                .input(Items.WHEAT)
                .input(Items.COD)
                .input(Items.DRIED_KELP)
                .group("food")
                .criterion(FabricRecipeProvider.hasItem(Items.COD), FabricRecipeProvider.conditionsFromItem(Items.COD))
                .offerTo(exporter, convertBetween(ModItems.SUSHI, Items.COD));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.SPONGE)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .input('A', ModItems.CORN)
                .input('B', Items.SEAGRASS)
                .input('C', Items.TUBE_CORAL_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CORN), FabricRecipeProvider.conditionsFromItem(ModItems.CORN))
                .offerTo(exporter, new Identifier(FishAndShiz.MOD_ID, FabricRecipeProvider.getRecipeName(Items.SPONGE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POWERED_PRISMARINE)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .input('A', Items.PRISMARINE_CRYSTALS)
                .input('B', Items.REDSTONE)
                .input('C', Items.SEA_LANTERN)
                .criterion(FabricRecipeProvider.hasItem(Items.SEA_LANTERN), FabricRecipeProvider.conditionsFromItem(Items.SEA_LANTERN))
                .offerTo(exporter, new Identifier(FishAndShiz.MOD_ID, FabricRecipeProvider.getRecipeName(ModBlocks.POWERED_PRISMARINE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.NITROGEN_DETECTOR)
                .pattern("CBD")
                .pattern("AAA")
                .input('A', Items.CHISELED_DEEPSLATE)
                .input('B', Items.GLASS_BOTTLE)
                .input('C', Items.GLASS_PANE)
                .input('D', Items.COPPER_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT), FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, new Identifier(FishAndShiz.MOD_ID, FabricRecipeProvider.getRecipeName(ModItems.NITROGEN_DETECTOR)));
        }

    public static void offerReversibleCompactingRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory reverseCategory, ItemConvertible baseItem, RecipeCategory compactingCategory, ItemConvertible compactItem) {
        RecipeProvider.offerReversibleCompactingRecipes(exporter, reverseCategory, baseItem, compactingCategory, compactItem, getRecipeName(compactItem), null, getRecipeName(baseItem), null);
    }

    public static void offerSmelting(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group) {
        offerMultipleOptions(exporter, RecipeSerializer.SMELTING, inputs, category, output, experience, cookingTime, group, "_from_smelting");
    }

    public static void offerMultipleOptions(Consumer<RecipeJsonProvider> exporter, RecipeSerializer<? extends AbstractCookingRecipe> serializer, List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group, String method) {
        for (ItemConvertible itemConvertible : inputs) {
            CookingRecipeJsonBuilder.create(Ingredient.ofItems(itemConvertible), category, output, experience, cookingTime, serializer).group(group).criterion(RecipeProvider.hasItem(itemConvertible), RecipeProvider.conditionsFromItem(itemConvertible)).offerTo(exporter, getItemPath(output) + method + "_" + RecipeProvider.getItemPath(itemConvertible));
        }
    }

    public static String convertBetween(ItemConvertible to, ItemConvertible from) {
        return getItemPath(to) + "_from_" + RecipeProvider.getItemPath(from);
    }

    public static String getRecipeName(ItemConvertible item) {
        return getItemPath(item);
    }

    public static String getItemPath(ItemConvertible item) {
        Identifier id = Registries.ITEM.getId(item.asItem());
        return id.getNamespace() + ":" + id.getPath();
    }
}
