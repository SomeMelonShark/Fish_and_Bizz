package net.redmelon.fishandshiz.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CORN_KERNELS, 3)
                .input(ModItems.CORN)
                .group("food")
                .criterion(FabricRecipeProvider.hasItem(ModItems.CORN), FabricRecipeProvider.conditionsFromItem(ModItems.CORN))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.CORN_KERNELS, ModItems.CORN));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISHMEAL, 3)
                .input(Items.BONE_MEAL)
                .input(Items.COOKED_COD)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(Items.COOKED_COD), FabricRecipeProvider.conditionsFromItem(Items.COOKED_COD))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.FISHMEAL, Items.COOKED_COD));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISHMEAL, 3)
                .input(Items.BONE_MEAL)
                .input(Items.COOKED_SALMON)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(Items.COOKED_SALMON), FabricRecipeProvider.conditionsFromItem(Items.COOKED_SALMON))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.FISHMEAL, Items.COOKED_SALMON));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISH_FOOD, 3)
                .input(Items.COOKED_CHICKEN)
                .input(ModItems.FISHMEAL)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(ModItems.FISHMEAL), FabricRecipeProvider.conditionsFromItem(ModItems.FISHMEAL))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.FISH_FOOD, Items.COOKED_CHICKEN));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISH_FOOD, 3)
                .input(Items.COOKED_BEEF)
                .input(ModItems.FISHMEAL)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(ModItems.FISHMEAL), FabricRecipeProvider.conditionsFromItem(ModItems.FISHMEAL))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.FISH_FOOD, Items.COOKED_BEEF));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISH_FOOD, 3)
                .input(Items.COOKED_PORKCHOP)
                .input(ModItems.FISHMEAL)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(ModItems.FISHMEAL), FabricRecipeProvider.conditionsFromItem(ModItems.FISHMEAL))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.FISH_FOOD, Items.COOKED_PORKCHOP));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FISH_FOOD, 3)
                .input(Items.COOKED_MUTTON)
                .input(ModItems.FISHMEAL)
                .group("misc")
                .criterion(FabricRecipeProvider.hasItem(ModItems.FISHMEAL), FabricRecipeProvider.conditionsFromItem(ModItems.FISHMEAL))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.FISH_FOOD, Items.COOKED_MUTTON));
        }
    }
