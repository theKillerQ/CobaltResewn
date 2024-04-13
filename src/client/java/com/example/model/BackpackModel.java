package com.example.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class BackpackModel extends BipedEntityModel<LivingEntity> {

    public static final Identifier TEXTURE_BASE = new Identifier("textures/models/armor/backpack.png");
    public static final Identifier TEXTURE_OVERLAY = new Identifier("textures/models/armor/backpack_overlay.png");

    public BackpackModel() {
        super(createModel());
    }

    public static ModelPart createModel() {
        ModelData modelData = BipedEntityModel.getModelData(new Dilation(0f), -20f);
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.getChild(EntityModelPartNames.BODY).addChild("backpack", ModelPartBuilder.create()
                        .uv(0, 32).cuboid(-4.0F, 0.0F, 2.0F, 8.0F, 10.0F, 4.0F, new Dilation(2, 2, 2))
                        .uv(0, 46).cuboid(-3.0F, 4.0F, 6.0F, 6.0F, 6.0F, 2.0F, new Dilation(2, 2, 2)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        /*
        modelPartData.getChild("body").addChild("backpack", ModelPartBuilder.create()
                        .uv(0, 32).cuboid(-4.0F, 0.0F, 2.0F, 8.0F, 10.0F, 4.0F)
                        .uv(0, 46).cuboid(-3.0F, 4.0F, 6.0F, 6.0F, 6.0F, 2.0F)
                        .uv(24, 32).cuboid("straps", -4.0F, -0.05F, -3.0F, 8.0F, 8.0F, 5.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
         */

        var texturedModel = TexturedModelData.of(modelData, 64, 64).createModel();
        texturedModel.xScale*=2;
        return texturedModel;
    }
}
