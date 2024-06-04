package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class ExposedCopperCrateBlockEntity extends CustomSingleStackInventoryBlockEntity {
    public ExposedCopperCrateBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.EXPOSED_COPPER_CRATE, pos, state);
    }
}
