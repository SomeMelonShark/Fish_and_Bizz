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
import net.redmelon.fishandshiz.entity.variant.*;
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
            if(this.entityType.equals(ModEntities.ANGELFISH)) {
                if (nbt.contains("Pattern", NbtElement.STRING_TYPE)) {
                    MutableText text = Text.translatable(ModEntityColor.fromId(nbt.getString("BaseColor")).getTranslationKey());
                    tooltip.add(Text.translatable(AngelfishPattern.fromId(nbt.getString("Pattern")).getTranslationKey()).formatted(formatting));
                    if (!nbt.getString("BaseColor").equals(nbt.getString("PatternColor"))) {
                        text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("PatternColor")).getTranslationKey()));
                    }
                    if (!AngelfishDetail.fromId(nbt.getString("Detail")).equals(AngelfishDetail.NONE)) {
                        tooltip.add(Text.translatable(AngelfishDetail.fromId(nbt.getString("Detail")).getTranslationKey()).formatted(formatting));
                        if (!nbt.getString("DetailColor").equals(nbt.getString("BaseColor"))) {
                            text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("DetailColor")).getTranslationKey()));
                        }
                    }
                    tooltip.add(text.formatted(formatting));
                }
            }
            if(this.entityType.equals(ModEntities.AMUR_CARP)) {
                if (nbt.contains("Pattern", NbtElement.STRING_TYPE)) {
                    MutableText text = Text.translatable(ModEntityColor.fromId(nbt.getString("BaseColor")).getTranslationKey());
                    tooltip.add(Text.translatable(AmurCarpPattern.fromId(nbt.getString("Pattern")).getTranslationKey()).formatted(formatting));
                    if (!nbt.getString("BaseColor").equals(nbt.getString("PatternColor"))) {
                        text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("PatternColor")).getTranslationKey()));
                    }
                    if (!AmurCarpDetail.fromId(nbt.getString("Detail")).equals(AmurCarpDetail.NONE)) {
                        tooltip.add(Text.translatable(AmurCarpDetail.fromId(nbt.getString("Detail")).getTranslationKey()).formatted(formatting));
                        if (!nbt.getString("DetailColor").equals(nbt.getString("BaseColor"))) {
                            text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("DetailColor")).getTranslationKey()));
                        }
                    }
                    tooltip.add(text.formatted(formatting));
                }
            }
            if(this.entityType.equals(ModEntities.BETTA)) {
                if (nbt.contains("Pattern", NbtElement.STRING_TYPE)) {
                    MutableText text = Text.translatable(ModEntityColor.fromId(nbt.getString("BaseColor")).getTranslationKey());
                    tooltip.add(Text.translatable(BettaPattern.fromId(nbt.getString("Pattern")).getTranslationKey()).formatted(formatting));
                    if (!nbt.getString("BaseColor").equals(nbt.getString("PatternColor"))) {
                        text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("PatternColor")).getTranslationKey()));
                    }
                    if (!BettaDetail.fromId(nbt.getString("Detail")).equals(BettaDetail.NONE)) {
                        tooltip.add(Text.translatable(BettaDetail.fromId(nbt.getString("Detail")).getTranslationKey()).formatted(formatting));
                        if (!nbt.getString("DetailColor").equals(nbt.getString("BaseColor"))) {
                            text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("DetailColor")).getTranslationKey()));
                        }
                    }
                    tooltip.add(text.formatted(formatting));
                }
            }
            if(this.entityType.equals(ModEntities.CLOWNFISH)) {
                if (nbt.contains("Pattern", NbtElement.STRING_TYPE)) {
                    MutableText text = Text.translatable(ModEntityColor.fromId(nbt.getString("BaseColor")).getTranslationKey());
                    tooltip.add(Text.translatable(ClownfishPattern.fromId(nbt.getString("Pattern")).getTranslationKey()).formatted(formatting));
                    if (!nbt.getString("BaseColor").equals(nbt.getString("PatternColor"))) {
                        text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("PatternColor")).getTranslationKey()));
                    }
                    if (!BettaDetail.fromId(nbt.getString("Detail")).equals(BettaDetail.NONE)) {
                        tooltip.add(Text.translatable(ClownfishDetail.fromId(nbt.getString("Detail")).getTranslationKey()).formatted(formatting));
                        if (!nbt.getString("DetailColor").equals(nbt.getString("BaseColor"))) {
                            text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("DetailColor")).getTranslationKey()));
                        }
                    }
                    tooltip.add(text.formatted(formatting));
                }
            }
            if(this.entityType.equals(ModEntities.GOATFISH)) {
                if (nbt.contains("Pattern", NbtElement.STRING_TYPE)) {
                    MutableText text = Text.translatable(ModEntityColor.fromId(nbt.getString("BaseColor")).getTranslationKey());
                    tooltip.add(Text.translatable(GoatfishPattern.fromId(nbt.getString("Pattern")).getTranslationKey()).formatted(formatting));
                    if (!nbt.getString("BaseColor").equals(nbt.getString("PatternColor"))) {
                        text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("PatternColor")).getTranslationKey()));
                    }
                    if (!BettaDetail.fromId(nbt.getString("Detail")).equals(BettaDetail.NONE)) {
                        tooltip.add(Text.translatable(GoatfishDetail.fromId(nbt.getString("Detail")).getTranslationKey()).formatted(formatting));
                        if (!nbt.getString("DetailColor").equals(nbt.getString("BaseColor"))) {
                            text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("DetailColor")).getTranslationKey()));
                        }
                    }
                    tooltip.add(text.formatted(formatting));
                }
            }
            if(this.entityType.equals(ModEntities.TANG)) {
                if (nbt.contains("Pattern", NbtElement.STRING_TYPE)) {
                    MutableText text = Text.translatable(ModEntityColor.fromId(nbt.getString("BaseColor")).getTranslationKey());
                    tooltip.add(Text.translatable(TangPattern.fromId(nbt.getString("Pattern")).getTranslationKey()).formatted(formatting));
                    if (!nbt.getString("BaseColor").equals(nbt.getString("PatternColor"))) {
                        text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("PatternColor")).getTranslationKey()));
                    }
                    if (!BettaDetail.fromId(nbt.getString("Detail")).equals(BettaDetail.NONE)) {
                        tooltip.add(Text.translatable(TangDetail.fromId(nbt.getString("Detail")).getTranslationKey()).formatted(formatting));
                        if (!nbt.getString("DetailColor").equals(nbt.getString("BaseColor"))) {
                            text.append(", ").append(Text.translatable(ModEntityColor.fromId(nbt.getString("DetailColor")).getTranslationKey()));
                        }
                    }
                    tooltip.add(text.formatted(formatting));
                }
            }
            if (this.entityType == ModEntities.CORYDORAS && (nbtCompound = stack.getNbt()) != null && nbtCompound.contains("BucketVariantTag", NbtElement.INT_TYPE)) {
                int i = nbtCompound.getInt("BucketVariantTag");
                Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                String string = "entity.fishandshiz.corydoras.type." + CorydorasEntity.getVariety(i);
                MutableText mutableText = Text.translatable(string);
                mutableText.formatted(formattings);
                tooltip.add(mutableText);
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
