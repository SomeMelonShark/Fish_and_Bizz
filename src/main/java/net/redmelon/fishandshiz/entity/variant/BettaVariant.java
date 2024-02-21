package net.redmelon.fishandshiz.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum BettaVariant {
    WILD1(0),
    WILD2(1),
    VEIL1(2),
    VEIL2(3),
    FAN1(4),
    FAN2(5),
    FAN3(6),
    SPECIAL(7);

    private static final BettaVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(BettaVariant::getId))
            .toArray(BettaVariant[]::new);
    private final int id;

    BettaVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static BettaVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
