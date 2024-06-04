package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class PedestalBlockEntity extends CustomSingleStackInventoryBlockEntity {

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.PEDESTAL_BLOCK_ENTITY, pos, state);
    }
}
