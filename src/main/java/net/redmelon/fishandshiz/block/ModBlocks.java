package net.redmelon.fishandshiz.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.custom.*;

import java.util.function.ToIntFunction;

public class ModBlocks {
    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> {
            return (Boolean)state.get(Properties.LIT) ? litLevel : 0;
        };
    }

    public static final Block FISHMEAL_BLOCK = registerBlock("fishmeal_block",
            new Block(FabricBlockSettings.copyOf(Blocks.SAND).strength(0.5f).sounds(BlockSoundGroup.SAND)));
    public static final Block FANWORT = registerBlockWithoutItem("fanwort",
            new FanwortBlock(FabricBlockSettings.copyOf(Blocks.KELP).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block FANWORT_PLANT = registerBlockWithoutItem("fanwort_plant",
            new FanwortBlock.FanwortPlantBlock(FabricBlockSettings.copyOf(Blocks.KELP_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block VALLISNERIA = registerBlockWithoutItem("vallisneria",
            new VallisneriaBlock(FabricBlockSettings.copyOf(Blocks.SEAGRASS).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block MONTE_CARLO = registerBlockWithoutItem("monte_carlo",
            new MonteCarloBlock(FabricBlockSettings.copyOf(Blocks.SEAGRASS).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block TALL_VALLISNERIA = registerBlockWithoutItem("tall_vallisneria",
            new TallVallisneriaBlock(FabricBlockSettings.copyOf(Blocks.SEAGRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block CORN_CROP = registerBlockWithoutItem("corn_crop",
            new CornCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block SEA_ANEMONE = Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, "sea_anemone"),
            new SeaAnemoneBlock(FabricBlockSettings.of().mapColor(MapColor.ORANGE).pistonBehavior(PistonBehavior.DESTROY).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.WART_BLOCK)));
    public static final Block POWERED_PRISMARINE = registerBlock("powered_prismarine",
            new PoweredPrismarineBlock(FabricBlockSettings.of().luminance(createLightLevelFromLitBlockState(15)).strength(0.3F).sounds(BlockSoundGroup.GLASS)));
    private static Block registerBlock(String name, Block block) {
        registerBlockItems(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, name), block);
    }
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, name), block);
    }
    private static Item registerBlockItems(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(FishAndShiz.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        return item;
    }
    public static void registerModBlocks() {
        FishAndShiz.LOGGER.debug("Registering ModBlocks for "+ FishAndShiz.MOD_ID);
    }
}
