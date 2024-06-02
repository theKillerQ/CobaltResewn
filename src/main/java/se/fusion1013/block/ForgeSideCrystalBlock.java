package se.fusion1013.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.block.entity.ForgeSideCrystalBlockEntity;

public class ForgeSideCrystalBlock extends Block implements BlockEntityProvider {

    public ForgeSideCrystalBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeSideCrystalBlockEntity(pos, state);
    }
}
