package net.redmelon.fishandshiz.cclass.cmethods;

import com.google.common.collect.Maps;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CustomCriteria {
    private static final Map<Identifier, Criterion<?>> VALUES = Maps.newHashMap();
    public static final BredFishAnimalsCriterion BRED_ANIMALS = CustomCriteria.register(new BredFishAnimalsCriterion());

    public static <T extends Criterion<?>> T register(T object) {
        if (VALUES.containsKey(object.getId())) {
            throw new IllegalArgumentException("Duplicate criterion id " + object.getId());
        }
        VALUES.put(object.getId(), object);
        return object;
    }

    @Nullable
    public static <T extends CriterionConditions> Criterion<T> getById(Identifier id) {
        return (Criterion<T>) VALUES.get(id);
    }

    public static Iterable<? extends Criterion<?>> getCriteria() {
        return VALUES.values();
    }
}
