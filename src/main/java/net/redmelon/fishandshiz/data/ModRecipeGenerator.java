package net.redmelon.fishandshiz.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
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

        //DO!
        offerSmelting(exporter, List.of(ModItems.CULTURE_FEED_BUCKET), RecipeCategory.MISC, ModItems.DRIED_CULTURE_FEED,
                0.20f, 200, "misc");

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CORNBREAD)
                .pattern("ABA")
                .input('A', ModItems.CORN)
                .input('B', Items.SUGAR)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CORN), FabricRecipeProvider.conditionsFromItem(ModItems.CORN))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.CORNBREAD)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SUSHI, 2)
                .input(Items.WHEAT)
                .input(ModItems.MILKFISH)
                .input(Items.DRIED_KELP)
                .group("food")
                .criterion(FabricRecipeProvider.hasItem(ModItems.MILKFISH), FabricRecipeProvider.conditionsFromItem(ModItems.MILKFISH))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.SUSHI, ModItems.MILKFISH));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SUSHI, 2)
                .input(Items.WHEAT)
                .input(Items.SALMON)
                .input(Items.DRIED_KELP)
                .group("food")
                .criterion(FabricRecipeProvider.hasItem(Items.SALMON), FabricRecipeProvider.conditionsFromItem(Items.SALMON))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.SUSHI, Items.SALMON));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SUSHI, 2)
                .input(Items.WHEAT)
                .input(Items.COD)
                .input(Items.DRIED_KELP)
                .group("food")
                .criterion(FabricRecipeProvider.hasItem(Items.COD), FabricRecipeProvider.conditionsFromItem(Items.COD))
                .offerTo(exporter, FabricRecipeProvider.convertBetween(ModItems.SUSHI, Items.COD));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.SPONGE)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .input('A', ModItems.CORN)
                .input('B', Items.SEAGRASS)
                .input('C', Items.TUBE_CORAL_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CORN), FabricRecipeProvider.conditionsFromItem(ModItems.CORN))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(Items.SPONGE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POWERED_PRISMARINE)
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .input('A', Items.PRISMARINE_CRYSTALS)
                .input('B', Items.REDSTONE)
                .input('C', Items.SEA_LANTERN)
                .criterion(FabricRecipeProvider.hasItem(Items.SEA_LANTERN), FabricRecipeProvider.conditionsFromItem(Items.SEA_LANTERN))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModBlocks.POWERED_PRISMARINE)));
        }
    }
