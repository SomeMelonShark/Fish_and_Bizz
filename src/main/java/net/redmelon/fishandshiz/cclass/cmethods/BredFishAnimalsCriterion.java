package net.redmelon.fishandshiz.cclass.cmethods;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import org.jetbrains.annotations.Nullable;

public class BredFishAnimalsCriterion extends AbstractCriterion<net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions> {
    static final Identifier ID = new Identifier("bred_animals");

    public BredFishAnimalsCriterion() {
    }

    public Identifier getId() {
        return ID;
    }

    public net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        EntityPredicate.Extended extended2 = EntityPredicate.Extended.getInJson(jsonObject, "parent", advancementEntityPredicateDeserializer);
        EntityPredicate.Extended extended3 = EntityPredicate.Extended.getInJson(jsonObject, "partner", advancementEntityPredicateDeserializer);
        EntityPredicate.Extended extended4 = EntityPredicate.Extended.getInJson(jsonObject, "child", advancementEntityPredicateDeserializer);
        return new net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions(extended, extended2, extended3, extended4);
    }

    public void trigger(ServerPlayerEntity player, AnimalFishEntity parent, AnimalFishEntity partner, @Nullable PassiveWaterEntity child) {
        LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, parent);
        LootContext lootContext2 = EntityPredicate.createAdvancementEntityLootContext(player, partner);
        LootContext lootContext3 = child != null ? EntityPredicate.createAdvancementEntityLootContext(player, child) : null;
        this.trigger(player, (conditions) -> {
            return conditions.matches(lootContext, lootContext2, lootContext3);
        });
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final EntityPredicate.Extended parent;
        private final EntityPredicate.Extended partner;
        private final EntityPredicate.Extended child;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended parent, EntityPredicate.Extended partner, EntityPredicate.Extended child) {
            super(BredFishAnimalsCriterion.ID, player);
            this.parent = parent;
            this.partner = partner;
            this.child = child;
        }

        public static net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions any() {
            return new net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
        }

        public static net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions create(EntityPredicate.Builder child) {
            return new net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.ofLegacy(child.build()));
        }

        public static net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions create(EntityPredicate parent, EntityPredicate partner, EntityPredicate child) {
            return new net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.ofLegacy(parent), EntityPredicate.Extended.ofLegacy(partner), EntityPredicate.Extended.ofLegacy(child));
        }

        public boolean matches(LootContext parentContext, LootContext partnerContext, @Nullable LootContext childContext) {
            if (this.child == EntityPredicate.Extended.EMPTY || childContext != null && this.child.test(childContext)) {
                return this.parent.test(parentContext) && this.partner.test(partnerContext) || this.parent.test(partnerContext) && this.partner.test(parentContext);
            } else {
                return false;
            }
        }

        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("parent", this.parent.toJson(predicateSerializer));
            jsonObject.add("partner", this.partner.toJson(predicateSerializer));
            jsonObject.add("child", this.child.toJson(predicateSerializer));
            return jsonObject;
        }
    }
}
