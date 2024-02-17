package net.redmelon.fishandshiz.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum AmurCarpVariant {
    WILD(0),
    CREAM1(1),
    CREAM2(2),
    CREAM3(3),
    CREAM4(4);

    private static final AmurCarpVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(AmurCarpVariant::getId))
            .toArray(AmurCarpVariant[]::new);
    private final int id;

    AmurCarpVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static AmurCarpVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
