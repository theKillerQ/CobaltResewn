package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class CopperCrateBlockEntity extends CustomSingleStackInventoryBlockEntity {
    public CopperCrateBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.COPPER_CRATE_ENTITY, pos, state);
    }
}
