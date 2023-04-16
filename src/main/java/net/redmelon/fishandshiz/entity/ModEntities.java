package net.redmelon.fishandshiz.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.custom.*;

public class ModEntities {
    public static final EntityType<AngelfishEntity> ANGELFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "angelfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    AngelfishEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.5f)).build());

    public static final EntityType<AngelfishFryEntity> ANGELFISH_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "angelfish_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    AngelfishFryEntity::new).dimensions(EntityDimensions.fixed(AngelfishFryEntity.WIDTH, AngelfishFryEntity.HEIGHT)).build());

    public static final EntityType<AngelfishEggEntity> ANGELFISH_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "angelfish_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    AngelfishEggEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.3f)).build());

    public static final EntityType<ArcherfishEntity> ARCHERFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "archerfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    ArcherfishEntity::new).dimensions(EntityDimensions.fixed(0.6f, 0.4f)).build());

    public static final EntityType<MilkfishEntity> MILKFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "milkfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    MilkfishEntity::new).dimensions(EntityDimensions.fixed(0.8f, 0.3f)).build());

    public static final EntityType<MilkfishFryEntity> MILKFISH_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "milkfish_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    MilkfishFryEntity::new).dimensions(EntityDimensions.fixed(MilkfishFryEntity.WIDTH, MilkfishFryEntity.HEIGHT)).build());

    public static final EntityType<MilkfishEggEntity> MILKFISH_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "milkfish_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    MilkfishEggEntity::new).dimensions(EntityDimensions.fixed(1.0f, 0.1f)).build());

    public static final EntityType<NeonTetraEntity> NEON_TETRA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "neon_tetra"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    NeonTetraEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.1f)).build());

    public static final EntityType<NeonTetraFryEntity> NEON_TETRA_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "neon_tetra_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    NeonTetraFryEntity::new).dimensions(EntityDimensions.fixed(NeonTetraFryEntity.WIDTH, NeonTetraFryEntity.HEIGHT)).build());

    public static final EntityType<NeonTetraEggEntity> NEON_TETRA_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "neon_tetra_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    NeonTetraEggEntity::new).dimensions(EntityDimensions.fixed(0.1f, 0.1f)).build());

    public static final EntityType<MudCrabEntity> MUD_CRAB = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "mud_crab"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    MudCrabEntity::new).dimensions(EntityDimensions.fixed(0.7f, 0.5f)).build());

    public static final EntityType<MudCrabEggEntity> MUD_CRAB_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "mud_crab_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    MudCrabEggEntity::new).dimensions(EntityDimensions.fixed(0.6f, 0.1f)).build());

    public static final EntityType<CapybaraEntity> CAPYBARA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "capybara"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE,
                    CapybaraEntity::new).dimensions(EntityDimensions.fixed(0.8f, 0.6f)).build());
}
