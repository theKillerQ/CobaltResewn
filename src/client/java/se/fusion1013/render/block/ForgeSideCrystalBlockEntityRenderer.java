package se.fusion1013.render.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import se.fusion1013.block.entity.ForgeSideCrystalBlockEntity;
import se.fusion1013.block.entity.RuneBlockEntity;
import se.fusion1013.items.CobaltItems;

@Environment(EnvType.CLIENT)
public class ForgeSideCrystalBlockEntityRenderer implements BlockEntityRenderer<ForgeSideCrystalBlockEntity> {

    private static final ItemStack stack = new ItemStack(CobaltItems.MiscItems.FORGE_SIDE_CRYSTAL, 1);

    public ForgeSideCrystalBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(ForgeSideCrystalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;

        matrices.translate(0.5, 10 + offset, 0.5);
        matrices.scale(10, 10, 10);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 0.5f));

        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);

        matrices.pop();
    }
}
