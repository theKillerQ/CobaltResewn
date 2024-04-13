package com.example.items.trinkets;

import com.example.model.BackpackModel;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

public class BackpackItem extends TrinketItem implements TrinketRenderer {

    private static final Map<Integer, BipedEntityModel<LivingEntity>> LIVING_MODELS = new HashMap<>();

    public BackpackItem(Settings settings) {
        super(settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void render(ItemStack stack, SlotReference slotReference, net.minecraft.client.render.entity.model.EntityModel<? extends LivingEntity> contextModel, net.minecraft.client.util.math.MatrixStack matrices, net.minecraft.client.render.VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        Identifier baseTexture = getBaseTexture();
        Identifier overlayTexture = getOverlayTexture();

        BipedEntityModel<LivingEntity> model = LIVING_MODELS.computeIfAbsent(entity.getId(), id -> new BackpackModel());
        model.setVisible(false);
        model.body.visible = true;
        model.rightArm.visible = true;
        model.leftArm.visible = true;

        boolean hasGlint = stack.hasGlint();
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(baseTexture), false, hasGlint);

        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1.0f);
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, overlayTexture);
    }

    public static Identifier getBaseTexture() {
        return BackpackModel.TEXTURE_BASE;
    }

    public static Identifier getOverlayTexture() {
        return BackpackModel.TEXTURE_OVERLAY;
    }
}
