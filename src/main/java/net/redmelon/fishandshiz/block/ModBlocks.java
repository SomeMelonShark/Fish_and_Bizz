package net.redmelon.fishandshiz.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.custom.CornCropBlock;
import net.redmelon.fishandshiz.block.custom.FanwortBlock;
import net.redmelon.fishandshiz.block.custom.SeaAnemoneBlock;

public class ModBlocks {
    public static final Block FISHMEAL_BLOCK = registerBlock("fishmeal_block",
            new Block(FabricBlockSettings.of(Material.AGGREGATE).strength(0.5f).sounds(BlockSoundGroup.SAND)), ItemGroups.INGREDIENTS);
    public static final Block FANWORT = registerBlockWithoutItem("fanwort",
            new FanwortBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block FANWORT_PLANT = registerBlockWithoutItem("fanwort_plant",
            new FanwortBlock.FanwortPlantBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)));
    public static final Block CORN_CROP = registerBlockWithoutItem("corn_crop",
            new CornCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block SEA_ANEMONE = Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, "sea_anemone"),
            new SeaAnemoneBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).breakInstantly().nonOpaque().sounds(BlockSoundGroup.WART_BLOCK)));
    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItems(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, name), block);
    }
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(FishAndShiz.MOD_ID, name), block);
    }
    private static Item registerBlockItems(String name, Block block, ItemGroup group) {
        Item item = Registry.register(Registries.ITEM, new Identifier(FishAndShiz.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }
    public static void registerModBlocks() {
        FishAndShiz.LOGGER.debug("Registering ModBlocks for "+ FishAndShiz.MOD_ID);
    }
}
