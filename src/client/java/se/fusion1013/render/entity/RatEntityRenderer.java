package se.fusion1013.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SilverfishEntityRenderer;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.util.Identifier;
import se.fusion1013.Main;

public class RatEntityRenderer extends SilverfishEntityRenderer {

    private static final Identifier TEXTURE = new Identifier(Main.MOD_NAMESPACE, "textures/entity/rat.png");

    public RatEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SilverfishEntity silverfishEntity) {
        return TEXTURE;
    }
}
