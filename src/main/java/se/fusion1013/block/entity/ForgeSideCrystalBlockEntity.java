package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class ForgeSideCrystalBlockEntity extends BlockEntity {
    public ForgeSideCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.FORGE_SIDE_CRYSTAL_BLOCK, pos, state);
    }
}
