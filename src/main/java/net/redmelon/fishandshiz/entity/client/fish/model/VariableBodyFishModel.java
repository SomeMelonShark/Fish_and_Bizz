package net.redmelon.fishandshiz.entity.client.fish.model;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.FishAndShiz;
import net.redmelon.fishandshiz.entity.variant.VariableBody;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;

/**Allows two different models to be used for the same fish and switches between the two. Experimental**/
public class VariableBodyFishModel<E extends LivingEntity & GeoAnimatable & VariableBody> extends BasicFishModel<E> {
    private final Identifier model1;
    private final Identifier model2;

    public VariableBodyFishModel(
            String name,
            String aname,
            String altSuffix,
            @Nullable String root
    ) {
        super(name, aname, root);

        this.model1 = new Identifier(FishAndShiz.MOD_ID, "geo/" + name + ".geo.json");
        this.model2    = new Identifier(FishAndShiz.MOD_ID, "geo/" + name + altSuffix + ".geo.json");
    }

    @Override
    public Identifier getModelResource(E entity) {
        return entity.useAltBody() ? model1 : model2;
    }
}
