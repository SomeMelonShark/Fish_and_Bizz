package net.redmelon.fishandshiz.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum AngelfishVariant {
    WILD1(0),
    WILD2(1),
    WILD3(2),
    MARBLE1(3),
    MARBLE2(4),
    PANTS1(5),
    PANTS2(6),
    PANTS3(7),
    PANTS4(8),
    STRIPES1(9),
    STRIPES2(10),
    STRIPES3(11),
    SPECIAL(12);

    private static final AngelfishVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(AngelfishVariant::getId))
            .toArray(AngelfishVariant[]::new);
    private final int id;

    AngelfishVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static AngelfishVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
