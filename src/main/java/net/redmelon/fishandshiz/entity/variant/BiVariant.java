package net.redmelon.fishandshiz.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum BiVariant {
    NORMAL(0),
    SPECIAL(1);

    private static final BiVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(BiVariant::getId))
            .toArray(BiVariant[]::new);
    private final int id;

    BiVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static BiVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
