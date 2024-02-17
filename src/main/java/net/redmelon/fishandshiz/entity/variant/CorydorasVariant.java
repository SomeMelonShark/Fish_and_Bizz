package net.redmelon.fishandshiz.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum CorydorasVariant {
    BRONZE(0),
    PANDA(1),
    ALBINO(2);
    private static final CorydorasVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(CorydorasVariant::getId))
            .toArray(CorydorasVariant[]::new);
    private final int id;

    CorydorasVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static CorydorasVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
