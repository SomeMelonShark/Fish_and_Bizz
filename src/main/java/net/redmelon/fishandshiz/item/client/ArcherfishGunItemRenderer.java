package net.redmelon.fishandshiz.item.client;

import net.redmelon.fishandshiz.item.custom.ArcherfishGunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ArcherfishGunItemRenderer extends GeoItemRenderer<ArcherfishGunItem> {
    public ArcherfishGunItemRenderer() {
        super(new ArcherfishGunItemModel());
    }
}
