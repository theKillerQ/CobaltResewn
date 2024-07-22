package se.fusion1013.render.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import se.fusion1013.block.LightHolderBlock;
import se.fusion1013.block.entity.LightHolderBlockEntity;
import se.fusion1013.items.CobaltItems;

public class LightHolderBlockEntityRenderer implements BlockEntityRenderer<LightHolderBlockEntity> {

    private static final ItemStack stack = new ItemStack(CobaltItems.MiscItems.LIGHT_SOUL, 1);

    public LightHolderBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(LightHolderBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        World world = entity.getWorld();
        BlockState state = world.getBlockState(entity.getPos());
        if (state.get(LightHolderBlock.LIT).booleanValue()) {
            double offset = Math.sin(MathHelper.lerp(tickDelta, (float)LightHolderBlockEntity.getLastTick(), (float)LightHolderBlockEntity.getTick()) / 32f) / 32.0;

            matrices.translate(0.5, (1/16f)*6 + offset, 0.5);

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
        }

        matrices.pop();
    }
}
