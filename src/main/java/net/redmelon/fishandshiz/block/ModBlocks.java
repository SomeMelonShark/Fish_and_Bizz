package net.redmelon.fishandshiz.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
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
            new Block(FabricBlockSettings.copyOf(Blocks.SAND).mapColor(MapColor.OAK_TAN).strength(0.5f).sounds(BlockSoundGroup.SAND)));
    public static final Block MULM = registerBlock("mulm",
            new MulmBlock(FabricBlockSettings.copyOf(Blocks.PODZOL).strength(0.5f).sounds(BlockSoundGroup.GRASS)));
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
    public static final Block AMAZON_SWORD = Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, "amazon_sword"),
            new AmazonSwordBlock(FabricBlockSettings.of().mapColor(MapColor.GREEN).pistonBehavior(PistonBehavior.DESTROY).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block POTHOS_PLANT = registerBlockWithoutItem("pothos_plant",
            new PothosPlantBlock(FabricBlockSettings.copyOf(Blocks.LILY_PAD).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.LILY_PAD)));
    public static final Block POTHOS_ROOT_CAP = registerBlockWithoutItem("pothos_root_cap",
            new PothosRootBlock(FabricBlockSettings.copyOf(Blocks.KELP).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block POTHOS_ROOTS = registerBlockWithoutItem("pothos_roots",
            new PothosRootBlock.PothosRootsBlock(FabricBlockSettings.copyOf(Blocks.KELP_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block ANUBIAS = Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, "anubias"),
            new AnubiasBlock(FabricBlockSettings.of().mapColor(MapColor.GREEN).pistonBehavior(PistonBehavior.DESTROY).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block LILY_PAD_STEM = registerBlockWithoutItem("lily_pad_stem",
            new LilyPadStemBlock(FabricBlockSettings.copyOf(Blocks.KELP_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block SEA_ANEMONE = Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, "sea_anemone"),
            new SeaAnemoneBlock(FabricBlockSettings.of().mapColor(MapColor.ORANGE).pistonBehavior(PistonBehavior.DESTROY).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.WART_BLOCK)));
    public static final Block NITROGEN_DETECTOR = Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, "nitrogen_detector"),
            new NitrogenDetectorBlock(FabricBlockSettings.of().mapColor(MapColor.GRAY).pistonBehavior(PistonBehavior.DESTROY).strength(0.2f).nonOpaque().sounds(BlockSoundGroup.STONE)));
    public static final Block FILTER = Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, "filter"),
            new FilterBlock(FabricBlockSettings.of().mapColor(MapColor.DARK_AQUA).pistonBehavior(PistonBehavior.NORMAL).ticksRandomly().strength(1.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block POWERED_PRISMARINE = registerBlock("powered_prismarine",
            new PoweredPrismarineBlock(FabricBlockSettings.of().mapColor(MapColor.LIGHT_BLUE).luminance(createLightLevelFromLitBlockState(15)).strength(0.3F).sounds(BlockSoundGroup.GLASS)));
    public static final Block CULTURE_FEED = registerBlock("culture_feed",
            new CultureFeedBlock(FabricBlockSettings.of().mapColor(MapColor.BROWN).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GLOW_LICHEN)));
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
