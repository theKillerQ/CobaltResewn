package se.fusion1013.render.block;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import se.fusion1013.block.entity.ItemDisplayBlockEntity;

public class ItemDisplayBlockEntityRenderer implements BlockEntityRenderer<ItemDisplayBlockEntity> {

    public ItemDisplayBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(ItemDisplayBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        var stack = entity.getStack(0);

        if (!entity.getStack(0).isEmpty()) {

            var t = entity.getWorld().getTime() + tickDelta;

            // Translation
            // Add .5 to each to center it inside the block by default
            var xOffset = entity.getxOffset() + .5f + Math.sin(t * entity.getxOffsetFrequency()) * entity.getxOffsetAmplitude();
            var yOffset = entity.getyOffset() + .5f + Math.sin(t * entity.getyOffsetFrequency()) * entity.getyOffsetAmplitude();
            var zOffset = entity.getzOffset() + .5f + Math.sin(t * entity.getzOffsetFrequency()) * entity.getzOffsetAmplitude();
            matrices.translate(xOffset, yOffset, zOffset);

            // Rotation
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getxRotation() + t * entity.getxRotationSpeed()));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getyRotation() + t * entity.getyRotationSpeed()));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getzRotation() + t * entity.getzRotationSpeed()));

            // Scale
            float xScale = (float) (entity.getxScale() + Math.sin(t * entity.getxScaleFrequency()) * entity.getxScaleAmplitude());
            float yScale = (float) (entity.getyScale() + Math.sin(t * entity.getyScaleFrequency()) * entity.getyScaleAmplitude());
            float zScale = (float) (entity.getzScale() + Math.sin(t * entity.getzScaleFrequency()) * entity.getzScaleAmplitude());
            matrices.scale(xScale, yScale, zScale);

            // Render the item
            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
        }

        matrices.pop();
    }
}
