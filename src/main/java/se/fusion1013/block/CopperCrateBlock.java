package se.fusion1013.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.block.entity.CustomSingleStackInventoryBlockEntity;

public class CopperCrateBlock extends CustomSingleStackBlock implements Waterloggable, BlockEntityProvider {

    public static final MapCodec<CopperCrateBlock> CODEC = createCodec(CopperCrateBlock::new);
    private static final BooleanProperty WATERLOGGED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 14, 15);

    protected CopperCrateBlock(Settings settings) {
        super(settings);
    }

    /// region Init

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    /// endregion

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomSingleStackInventoryBlockEntity(pos, state);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }
}
