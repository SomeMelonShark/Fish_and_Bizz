package net.redmelon.fishandshiz.cclass.cmethods;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.advancement.criterion.BredAnimalsCriterion;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.cclass.AnimalFishEntity;
import net.redmelon.fishandshiz.cclass.AnimalWaterEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import org.jetbrains.annotations.Nullable;

public class BredFishAnimalsCriterion extends AbstractCriterion<net.minecraft.advancement.criterion.BredAnimalsCriterion.Conditions> {
    static final Identifier ID = new Identifier("bred_animals");

    public BredFishAnimalsCriterion() {
    }

    public Identifier getId() {
        return ID;
    }

    public BredAnimalsCriterion.Conditions conditionsFromJson(JsonObject jsonObject, LootContextPredicate lootContextPredicate, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        LootContextPredicate lootContextPredicate2 = EntityPredicate.contextPredicateFromJson(jsonObject, "parent", advancementEntityPredicateDeserializer);
        LootContextPredicate lootContextPredicate3 = EntityPredicate.contextPredicateFromJson(jsonObject, "partner", advancementEntityPredicateDeserializer);
        LootContextPredicate lootContextPredicate4 = EntityPredicate.contextPredicateFromJson(jsonObject, "child", advancementEntityPredicateDeserializer);
        return new BredAnimalsCriterion.Conditions(lootContextPredicate, lootContextPredicate2, lootContextPredicate3, lootContextPredicate4);
    }

    public void trigger(ServerPlayerEntity player, AnimalFishEntity parent, AnimalFishEntity partner, @Nullable PassiveWaterEntity child) {
        LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, parent);
        LootContext lootContext2 = EntityPredicate.createAdvancementEntityLootContext(player, partner);
        LootContext lootContext3 = child != null ? EntityPredicate.createAdvancementEntityLootContext(player, child) : null;
        this.trigger(player, (conditions) -> {
            return conditions.matches(lootContext, lootContext2, lootContext3);
        });
    }

    public void trigger2(ServerPlayerEntity player, AnimalWaterEntity parent, AnimalWaterEntity partner, @Nullable PassiveWaterEntity child) {
        LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, parent);
        LootContext lootContext2 = EntityPredicate.createAdvancementEntityLootContext(player, partner);
        LootContext lootContext3 = child != null ? EntityPredicate.createAdvancementEntityLootContext(player, child) : null;
        this.trigger(player, (conditions) -> {
            return conditions.matches(lootContext, lootContext2, lootContext3);
        });
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final LootContextPredicate parent;
        private final LootContextPredicate partner;
        private final LootContextPredicate child;

        public Conditions(LootContextPredicate player, LootContextPredicate parent, LootContextPredicate partner, LootContextPredicate child) {
            super(BredFishAnimalsCriterion.ID, player);
            this.parent = parent;
            this.partner = partner;
            this.child = child;
        }

        public static BredAnimalsCriterion.Conditions any() {
            return new BredAnimalsCriterion.Conditions(LootContextPredicate.EMPTY, LootContextPredicate.EMPTY, LootContextPredicate.EMPTY, LootContextPredicate.EMPTY);
        }

        public static BredAnimalsCriterion.Conditions create(EntityPredicate.Builder child) {
            return new BredAnimalsCriterion.Conditions(LootContextPredicate.EMPTY, LootContextPredicate.EMPTY, LootContextPredicate.EMPTY, EntityPredicate.asLootContextPredicate(child.build()));
        }

        public static BredAnimalsCriterion.Conditions create(EntityPredicate parent, EntityPredicate partner, EntityPredicate child) {
            return new BredAnimalsCriterion.Conditions(LootContextPredicate.EMPTY, EntityPredicate.asLootContextPredicate(parent), EntityPredicate.asLootContextPredicate(partner), EntityPredicate.asLootContextPredicate(child));
        }

        public boolean matches(LootContext parentContext, LootContext partnerContext, @Nullable LootContext childContext) {
            if (this.child == LootContextPredicate.EMPTY || childContext != null && this.child.test(childContext)) {
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
