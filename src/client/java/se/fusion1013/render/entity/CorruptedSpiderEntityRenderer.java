package se.fusion1013.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.util.Identifier;
import se.fusion1013.Main;
import se.fusion1013.entity.CorruptedSpiderEntity;
import se.fusion1013.render.entity.feature.CorruptedSpiderEyesFeatureRenderer;

public class CorruptedSpiderEntityRenderer extends MobEntityRenderer<CorruptedSpiderEntity, SpiderEntityModel<CorruptedSpiderEntity>> {

    private static final Identifier TEXTURE = new Identifier(Main.MOD_NAMESPACE, "textures/entity/corrupted_spider.png");

    public CorruptedSpiderEntityRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.SPIDER);
    }

    public CorruptedSpiderEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer layer) {
        super(context, new SpiderEntityModel(context.getPart(layer)), 0.8f);
        this.addFeature(new CorruptedSpiderEyesFeatureRenderer(this));
    }

    @Override
    protected float getLyingAngle(CorruptedSpiderEntity entity) {
        return 180.0f;
    }

    @Override
    public Identifier getTexture(CorruptedSpiderEntity spiderEntity) {
        return TEXTURE;
    }
}
