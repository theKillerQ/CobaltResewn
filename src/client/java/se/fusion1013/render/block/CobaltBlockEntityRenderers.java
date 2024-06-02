package se.fusion1013.render.block;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import se.fusion1013.block.entity.CobaltBlockEntityTypes;

public class CobaltBlockEntityRenderers {

    public static void registerAll() {
        BlockEntityRendererRegistry.register(CobaltBlockEntityTypes.FORGE_SIDE_CRYSTAL_BLOCK, ForgeSideCrystalBlockEntityRenderer::new);
    }

}
