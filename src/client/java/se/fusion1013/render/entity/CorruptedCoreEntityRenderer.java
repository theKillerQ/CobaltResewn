package se.fusion1013.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import se.fusion1013.MainClient;
import se.fusion1013.entity.CorruptedCoreEntity;

public class CorruptedCoreEntityRenderer extends MobEntityRenderer<CorruptedCoreEntity, CorruptedCoreEntityModel> {

    public CorruptedCoreEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CorruptedCoreEntityModel(context.getPart(MainClient.MODEL_CORRUPTED_CORE_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(CorruptedCoreEntity entity) {
        return new Identifier("cobalt", "textures/entity/corrupted_core.png");
    }
}
