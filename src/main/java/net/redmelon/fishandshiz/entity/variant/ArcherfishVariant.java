package net.redmelon.fishandshiz.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ArcherfishVariant {
    NORMAL(0),
    EVIL(1);

    private static final ArcherfishVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(ArcherfishVariant::getId))
            .toArray(ArcherfishVariant[]::new);
    private final int id;

    ArcherfishVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ArcherfishVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
