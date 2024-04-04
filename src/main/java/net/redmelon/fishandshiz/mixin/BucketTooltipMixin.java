package net.redmelon.fishandshiz.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.fish.*;
import net.redmelon.fishandshiz.entity.variant.AngelfishColor;
import net.redmelon.fishandshiz.entity.variant.AngelfishPattern;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EntityBucketItem.class)
public abstract class BucketTooltipMixin {
    private final EntityType<?> entityType;

    protected BucketTooltipMixin(EntityType<?> entityType) {
        this.entityType = entityType;
    }

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        NbtCompound nbtCompound;
        NbtCompound nbt = stack.getNbt();
        Formatting[] formatting = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
        if (nbt != null) {
            if (this.entityType == ModEntities.CORYDORAS && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                int i = nbtCompound.getInt("BucketVariantTag");
                Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                String string = "entity.fishandshiz.corydoras.type." + CorydorasEntity.getVariety(i);
                MutableText mutableText = Text.translatable(string);
                mutableText.formatted(formattings);
                tooltip.add(mutableText);
            }
            if(this.entityType.equals(ModEntities.ANGELFISH)) {
                if (nbt.contains("Pattern", NbtElement.STRING_TYPE)) {
                    MutableText text = Text.translatable(AngelfishColor.fromId(nbt.getString("BaseColor")).getTranslationKey());
                    tooltip.add(Text.translatable(AngelfishPattern.fromId(nbt.getString("Pattern")).getTranslationKey()).formatted(formatting));
                    if (!nbt.getString("BaseColor").equals(nbt.getString("PatternColor"))) {
                        text.append(", ").append(Text.translatable(AngelfishColor.fromId(nbt.getString("PatternColor")).getTranslationKey()));
                    }
                    tooltip.add(text.formatted(formatting));
                }
                if (this.entityType == ModEntities.CORYDORAS_EGG && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                    int i = nbtCompound.getInt("BucketVariantTag");
                    Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                    String string = "entity.fishandshiz.corydoras.type." + CorydorasEntity.getVariety(i);
                    MutableText mutableText = Text.translatable(string);
                    mutableText.formatted(formattings);
                    tooltip.add(mutableText);
                }
                if (this.entityType == ModEntities.CORYDORAS_FRY && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                    int i = nbtCompound.getInt("BucketVariantTag");
                    Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                    String string = "entity.fishandshiz.corydoras.type." + CorydorasEntity.getVariety(i);
                    MutableText mutableText = Text.translatable(string);
                    mutableText.formatted(formattings);
                    tooltip.add(mutableText);
                }
                if (this.entityType == ModEntities.AMUR_CARP && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                    int i = nbtCompound.getInt("BucketVariantTag");
                    Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                    String string = "entity.fishandshiz.amur_carp.type." + AmurCarpEntity.getVariety(i);
                    MutableText mutableText = Text.translatable(string);
                    mutableText.formatted(formattings);
                    tooltip.add(mutableText);
                }
                if (this.entityType == ModEntities.AMUR_CARP_EGG && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                    int i = nbtCompound.getInt("BucketVariantTag");
                    Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                    String string = "entity.fishandshiz.amur_carp.type." + AmurCarpEntity.getVariety(i);
                    MutableText mutableText = Text.translatable(string);
                    mutableText.formatted(formattings);
                    tooltip.add(mutableText);
                }
                if (this.entityType == ModEntities.AMUR_CARP_FRY && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                    int i = nbtCompound.getInt("BucketVariantTag");
                    Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                    String string = "entity.fishandshiz.amur_carp.type." + AmurCarpEntity.getVariety(i);
                    MutableText mutableText = Text.translatable(string);
                    mutableText.formatted(formattings);
                    tooltip.add(mutableText);
                }
                if (this.entityType == ModEntities.BETTA && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                    int i = nbtCompound.getInt("BucketVariantTag");
                    Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                    String string = "entity.fishandshiz.betta.type." + BettaEntity.getVariety(i);
                    MutableText mutableText = Text.translatable(string);
                    mutableText.formatted(formattings);
                    tooltip.add(mutableText);
                }
                if (this.entityType == ModEntities.BETTA_EGG && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                    int i = nbtCompound.getInt("BucketVariantTag");
                    Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                    String string = "entity.fishandshiz.betta.type." + BettaEntity.getVariety(i);
                    MutableText mutableText = Text.translatable(string);
                    mutableText.formatted(formattings);
                    tooltip.add(mutableText);
                }
                if (this.entityType == ModEntities.BETTA_FRY && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                    int i = nbtCompound.getInt("BucketVariantTag");
                    Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                    String string = "entity.fishandshiz.betta.type." + BettaEntity.getVariety(i);
                    MutableText mutableText = Text.translatable(string);
                    mutableText.formatted(formattings);
                    tooltip.add(mutableText);
                }
                if (this.entityType == ModEntities.ARCHERFISH && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                    int i = nbtCompound.getInt("BucketVariantTag");
                    Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                    String string = "entity.fishandshiz.archerfish.type." + ArcherfishEntity.getVariety(i);
                    MutableText mutableText = Text.translatable(string);
                    mutableText.formatted(formattings);
                    tooltip.add(mutableText);
                }
            }
        }
    }
}
