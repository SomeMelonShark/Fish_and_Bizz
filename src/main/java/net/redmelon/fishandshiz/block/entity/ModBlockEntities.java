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

    public static void registerAllBlockEntities() {
        SEA_ANEMONE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(FishAndShiz.MOD_ID, "sea_anemone"),
                FabricBlockEntityTypeBuilder.create(SeaAnemoneBlockEntity::new,
                        ModBlocks.SEA_ANEMONE).build());
    }
}
