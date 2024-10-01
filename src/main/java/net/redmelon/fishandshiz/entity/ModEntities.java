package net.redmelon.fishandshiz.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.entity.custom.*;
import net.redmelon.fishandshiz.entity.custom.fish.*;

public class ModEntities {
    public static final EntityType<AngelfishEntity> ANGELFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "angelfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    AngelfishEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.7f)).build());

    public static final EntityType<AngelfishFryEntity> ANGELFISH_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "angelfish_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    AngelfishFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.2f)).build());

    public static final EntityType<AngelfishEggEntity> ANGELFISH_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "angelfish_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    AngelfishEggEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.3f)).build());

    public static final EntityType<ArcherfishEntity> ARCHERFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "archerfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    ArcherfishEntity::new).dimensions(EntityDimensions.fixed(0.6f, 0.4f)).build());

    public static final EntityType<MilkfishEntity> MILKFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "milkfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    MilkfishEntity::new).dimensions(EntityDimensions.fixed(0.8f, 0.3f)).build());

    public static final EntityType<MilkfishFryEntity> MILKFISH_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "milkfish_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    MilkfishFryEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.2f)).build());

    public static final EntityType<MilkfishEggEntity> MILKFISH_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "milkfish_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    MilkfishEggEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.1f)).build());

    public static final EntityType<SalmonEggEntity> SALMON_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "salmon_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    SalmonEggEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.1f)).build());

    public static final EntityType<SalmonFryEntity> SALMON_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "saslmon_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    SalmonFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.2f)).build());

    public static final EntityType<NeonTetraEntity> NEON_TETRA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "neon_tetra"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    NeonTetraEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.2f)).build());

    public static final EntityType<NeonTetraFryEntity> NEON_TETRA_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "neon_tetra_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    NeonTetraFryEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.2f)).build());

    public static final EntityType<NeonTetraEggEntity> NEON_TETRA_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "neon_tetra_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    NeonTetraEggEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.1f)).build());

    public static final EntityType<CorydorasEntity> CORYDORAS = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "corydoras"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    CorydorasEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.3f)).build());

    public static final EntityType<CorydorasFryEntity> CORYDORAS_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "corydoras_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    CorydorasFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.2f)).build());

    public static final EntityType<CorydorasEggEntity> CORYDORAS_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "corydoras_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    CorydorasEggEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.1f)).build());

    public static final EntityType<OscarEntity> OSCAR = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "oscar"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    OscarEntity::new).dimensions(EntityDimensions.fixed(1.0f, 0.7f)).build());

    public static final EntityType<OscarFryEntity> OSCAR_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "oscar_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    OscarFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.3f)).build());

    public static final EntityType<OscarEggEntity> OSCAR_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "oscar_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    OscarEggEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.4f)).build());

    public static final EntityType<RainbowfishEntity> RAINBOWFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "rainbowfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    RainbowfishEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.3f)).build());

    public static final EntityType<RainbowfishFryEntity> RAINBOWFISH_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "rainbowfish_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    RainbowfishFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.2f)).build());

    public static final EntityType<RainbowfishEggEntity> RAINBOWFISH_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "rainbowfish_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    RainbowfishEggEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.1f)).build());

    public static final EntityType<AuratusEntity> AURATUS = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "auratus"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    AuratusEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.3f)).build());

    public static final EntityType<AuratusFryEntity> AURATUS_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "auratus_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    AuratusFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.2f)).build());

    public static final EntityType<GraylingEntity> GRAYLING = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "grayling"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    GraylingEntity::new).dimensions(EntityDimensions.fixed(0.9f, 0.3f)).build());

    public static final EntityType<GraylingFryEntity> GRAYLING_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "grayling_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    GraylingFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.2f)).build());

    public static final EntityType<GraylingEggEntity> GRAYLING_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "grayling_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    GraylingEggEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.1f)).build());

    public static final EntityType<AmurCarpEntity> AMUR_CARP = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "amur_carp"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    AmurCarpEntity::new).dimensions(EntityDimensions.fixed(0.9f, 0.3f)).build());

    public static final EntityType<AmurCarpFryEntity> AMUR_CARP_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "amur_carp_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    AmurCarpFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.2f)).build());

    public static final EntityType<AmurCarpEggEntity> AMUR_CARP_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "amur_carp_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    AmurCarpEggEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.1f)).build());

    public static final EntityType<BettaEntity> BETTA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "betta"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    BettaEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.3f)).build());

    public static final EntityType<BettaFryEntity> BETTA_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "betta_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    BettaFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.2f)).build());

    public static final EntityType<BettaEggEntity> BETTA_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "betta_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    BettaEggEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.1f)).build());

    public static final EntityType<PlatyEntity> PLATY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "platy"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    PlatyEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.2f)).build());

    public static final EntityType<PlatyFryEntity> PLATY_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "platy_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    PlatyFryEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.2f)).build());

    public static final EntityType<ClownfishEntity> CLOWNFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "clownfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    ClownfishEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.3f)).build());

    public static final EntityType<ClownfishFryEntity> CLOWNFISH_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "clownfish_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    ClownfishFryEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.2f)).build());

    public static final EntityType<ClownfishEggEntity> CLOWNFISH_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "clownfish_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    ClownfishEggEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.1f)).build());

    public static final EntityType<TangEntity> TANG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "tang"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    TangEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.5f)).build());

    public static final EntityType<TangFryEntity> TANG_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "tang_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    TangFryEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.2f)).build());

    public static final EntityType<TangEggEntity> TANG_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "tang_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    TangEggEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.1f)).build());

    public static final EntityType<GoatfishEntity> GOATFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "goatfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    GoatfishEntity::new).dimensions(EntityDimensions.fixed(0.9f, 0.4f)).build());

    public static final EntityType<DottybackEntity> DOTTYBACK = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "dottyback"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    DottybackEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.3f)).build());

    public static final EntityType<DottybackFryEntity> DOTTYBACK_FRY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "dottyback_fry"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    DottybackFryEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.2f)).build());

    public static final EntityType<TangEggEntity> DOTTYBACK_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "dottyback_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    TangEggEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.1f)).build());

    public static final EntityType<MarineAngelfishEntity> MARINE_ANGELFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "marine_angelfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    MarineAngelfishEntity::new).dimensions(EntityDimensions.fixed(0.8f, 0.5f)).build());

    public static final EntityType<ParrotfishEntity> PARROTFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "parrotfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    ParrotfishEntity::new).dimensions(EntityDimensions.fixed(1.1f, 0.4f)).build());

    public static final EntityType<ButterflyfishEntity> BUTTERFLYFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "butterflyfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    ButterflyfishEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.4f)).build());

    public static final EntityType<TriggerfishEntity> TRIGGERFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "triggerfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    TriggerfishEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.4f)).build());

    public static final EntityType<RedTailCatfishEntity> RED_TAIL_CATFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "red_tail_catfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    RedTailCatfishEntity::new).dimensions(EntityDimensions.fixed(1.5f, 0.6f)).build());

    public static final EntityType<MudCrabEntity> MUD_CRAB = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "mud_crab"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    MudCrabEntity::new).dimensions(EntityDimensions.fixed(0.7f, 0.5f)).build());

    public static final EntityType<MudCrabLarvaEntity> MUD_CRAB_LARVA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "mud_crab_larva"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT,
                    MudCrabLarvaEntity::new).dimensions(EntityDimensions.fixed(MudCrabLarvaEntity.WIDTH, MudCrabLarvaEntity.HEIGHT)).build());

    public static final EntityType<ManeJellyfishEntity> LION_MANE_JELLYFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "lion_mane_jellyfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    ManeJellyfishEntity::new).dimensions(EntityDimensions.fixed(2.0f, 2.0f)).build());

    public static final EntityType<VolcanoSnailEntity> VOLCANO_SNAIL = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "volcano_snail"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    VolcanoSnailEntity::new).dimensions(EntityDimensions.fixed(0.3f, 0.3f)).build());

    public static final EntityType<VolcanoSnailEggEntity> VOLCANO_SNAIL_EGG = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "volcano_snail_egg"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    VolcanoSnailEggEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.4f)).build());

    public static final EntityType<CrayfishEntity> CRAYFISH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "crayfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    CrayfishEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.1f)).build());

    public static final EntityType<CrayfishLarvaEntity> CRAYFISH_LARVA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "crayfish_larva"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE,
                    CrayfishLarvaEntity::new).dimensions(EntityDimensions.fixed(CrayfishLarvaEntity.WIDTH, CrayfishLarvaEntity.HEIGHT)).build());

    public static final EntityType<CapybaraEntity> CAPYBARA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "capybara"), FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT,
                    CapybaraEntity::new).dimensions(EntityDimensions.fixed(0.8f, 0.6f)).build());

    public static final EntityType<ArcherfishSpitEntity> ARCHERFISH_SPIT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FishAndShiz.MOD_ID, "archerfish_spit"), FabricEntityTypeBuilder.<ArcherfishSpitEntity>create(SpawnGroup.MISC,
                    ArcherfishSpitEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

    public static void setAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.ANGELFISH, AngelfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ANGELFISH_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ANGELFISH_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ARCHERFISH, ArcherfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MILKFISH, MilkfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MILKFISH_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MILKFISH_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.NEON_TETRA, NeonTetraEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.NEON_TETRA_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.NEON_TETRA_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CORYDORAS, CorydorasEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CORYDORAS_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CORYDORAS_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.OSCAR, OscarEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.OSCAR_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.OSCAR_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.RAINBOWFISH, RainbowfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.RAINBOWFISH_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.RAINBOWFISH_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.AURATUS, AuratusEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.AURATUS_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GRAYLING, GraylingEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GRAYLING_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GRAYLING_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.AMUR_CARP, AmurCarpEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.AMUR_CARP_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.AMUR_CARP_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BETTA, BettaEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BETTA_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BETTA_EGG, BettaEggEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.PLATY, PlatyEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.PLATY_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CLOWNFISH, ClownfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CLOWNFISH_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CLOWNFISH_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TANG, TangEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TANG_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TANG_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GOATFISH, GoatfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DOTTYBACK, DottybackEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DOTTYBACK_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DOTTYBACK_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MARINE_ANGELFISH, MarineAngelfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.PARROTFISH, ParrotfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BUTTERFLYFISH, ButterflyfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TRIGGERFISH, TriggerfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.RED_TAIL_CATFISH, RedTailCatfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SALMON_EGG, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SALMON_FRY, AnimalFishEntity.createFishAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MUD_CRAB, MudCrabEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MUD_CRAB_LARVA, MudCrabLarvaEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CRAYFISH, CrayfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CRAYFISH_LARVA, CrayfishLarvaEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.LION_MANE_JELLYFISH, ManeJellyfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.VOLCANO_SNAIL, VolcanoSnailEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.VOLCANO_SNAIL_EGG, VolcanoSnailEggEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CAPYBARA, CapybaraEntity.setAttributes());
    }
}
