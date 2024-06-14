package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class ForgeBlockEntity extends BlockEntity {
    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.FORGE_BLOCK_ENTITY, pos, state);
    }
}
