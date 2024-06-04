package se.fusion1013.render.block;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import se.fusion1013.block.entity.CobaltBlockEntityTypes;

public class CobaltBlockEntityRenderers {

    public static void registerAll() {
        BlockEntityRendererRegistry.register(CobaltBlockEntityTypes.FORGE_SIDE_CRYSTAL_BLOCK, ForgeSideCrystalBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(CobaltBlockEntityTypes.PEDESTAL_BLOCK_ENTITY, PedestalBlockEntityRenderer::new);
    }

}
