package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class AncientPotBlockEntity extends CustomSingleStackInventoryBlockEntity {
    public AncientPotBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.ANCIENT_POT_BLOCK_ENTITY, pos, state);
    }
}
