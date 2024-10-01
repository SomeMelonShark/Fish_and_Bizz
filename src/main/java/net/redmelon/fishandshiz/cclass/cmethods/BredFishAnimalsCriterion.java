package net.redmelon.fishandshiz.cclass.cmethods;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.cclass.HolometabolousAquaticEntity;
import net.redmelon.fishandshiz.cclass.PassiveWaterEntity;
import org.jetbrains.annotations.Nullable;

public class BredFishAnimalsCriterion extends AbstractCriterion<BredFishAnimalsCriterion.Conditions> {
    static final Identifier ID = new Identifier("bred_animals");

    public BredFishAnimalsCriterion() {
    }

    public Identifier getId() {
        return ID;
    }

    public Conditions conditionsFromJson(JsonObject jsonObject, LootContextPredicate lootContextPredicate, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        LootContextPredicate lootContextPredicate2 = EntityPredicate.contextPredicateFromJson(jsonObject, "parent", advancementEntityPredicateDeserializer);
        LootContextPredicate lootContextPredicate3 = EntityPredicate.contextPredicateFromJson(jsonObject, "partner", advancementEntityPredicateDeserializer);
        LootContextPredicate lootContextPredicate4 = EntityPredicate.contextPredicateFromJson(jsonObject, "child", advancementEntityPredicateDeserializer);
        return new Conditions(lootContextPredicate, lootContextPredicate2, lootContextPredicate3, lootContextPredicate4);
    }

    public void trigger(ServerPlayerEntity player, HolometabolousAquaticEntity parent, HolometabolousAquaticEntity partner, @Nullable PassiveWaterEntity child) {
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

        public static Conditions any() {
            return new Conditions(LootContextPredicate.EMPTY, LootContextPredicate.EMPTY, LootContextPredicate.EMPTY, LootContextPredicate.EMPTY);
        }

        public static Conditions create(EntityPredicate.Builder child) {
            return new Conditions(LootContextPredicate.EMPTY, LootContextPredicate.EMPTY, LootContextPredicate.EMPTY, EntityPredicate.asLootContextPredicate(child.build()));
        }

        public static Conditions create(EntityPredicate parent, EntityPredicate partner, EntityPredicate child) {
            return new Conditions(LootContextPredicate.EMPTY, EntityPredicate.asLootContextPredicate(parent), EntityPredicate.asLootContextPredicate(partner), EntityPredicate.asLootContextPredicate(child));
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
