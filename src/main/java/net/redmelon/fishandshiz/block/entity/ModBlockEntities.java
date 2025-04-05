package net.redmelon.fishandshiz.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<SeaAnemoneBlockEntity> SEA_ANEMONE_ENTITY;
    public static BlockEntityType<NitrogenDetectorBlockEntity> NITROGEN_DETECTOR_ENTITY;
    public static BlockEntityType<FilterBlockEntity> FILTER_ENTITY;

    public static void registerAllBlockEntities() {
        SEA_ANEMONE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(FishAndShiz.MOD_ID, "sea_anemone"),
                FabricBlockEntityTypeBuilder.create(SeaAnemoneBlockEntity::new,
                        ModBlocks.SEA_ANEMONE).build());

        NITROGEN_DETECTOR_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(FishAndShiz.MOD_ID, "nitrogen_detector"),
                FabricBlockEntityTypeBuilder.create(NitrogenDetectorBlockEntity::new,
                        ModBlocks.NITROGEN_DETECTOR).build());

        FILTER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(FishAndShiz.MOD_ID, "filter"),
                FabricBlockEntityTypeBuilder.create(FilterBlockEntity::new,
                        ModBlocks.FILTER).build());
    }
}
