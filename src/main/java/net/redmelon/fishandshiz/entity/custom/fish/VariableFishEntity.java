package net.redmelon.fishandshiz.entity.custom.fish;

import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.entity.variant.*;

public interface VariableFishEntity<P extends GenericTextureProvider, D extends GenericTextureProvider> {
    ModEntityColor getBaseColor();
    ModEntityColor getPatternColor();
    ModEntityColor getDetailColor();
    P getPattern();
    D getDetail();
}
