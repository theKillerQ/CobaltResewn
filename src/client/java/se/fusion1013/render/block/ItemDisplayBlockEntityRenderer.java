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
            var xOffset = entity.getOffset().x + .5f + Math.sin(t * entity.getOffsetFrequency().x) * entity.getOffsetAmplitude().x;
            var yOffset = entity.getOffset().y + .5f + Math.sin(t * entity.getOffsetFrequency().y) * entity.getOffsetAmplitude().y;
            var zOffset = entity.getOffset().z + .5f + Math.sin(t * entity.getOffsetFrequency().z) * entity.getOffsetAmplitude().z;
            matrices.translate(xOffset, yOffset, zOffset);

            // Rotation
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getRotation().x + t * entity.getRotationSpeed().x));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getRotation().y + t * entity.getRotationSpeed().y));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getRotation().z + t * entity.getRotationSpeed().z));

            // Scale
            float xScale = (float) (entity.getScale().x + Math.sin(t * entity.getScaleFrequency().x) * entity.getScaleAmplitude().x);
            float yScale = (float) (entity.getScale().y + Math.sin(t * entity.getScaleFrequency().y) * entity.getScaleFrequency().y);
            float zScale = (float) (entity.getScale().z + Math.sin(t * entity.getScaleFrequency().z) * entity.getScaleFrequency().z);
            matrices.scale(xScale, yScale, zScale);

            // Render the item
            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
        }

        matrices.pop();
    }
}
