package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PedestalBlockEntity extends CustomSingleStackInventoryBlockEntity {

    private static int tick;
    private static int lastTick;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.PEDESTAL_BLOCK_ENTITY, pos, state);
    }

    public static void clientTick(World world, BlockPos pos, BlockState blockState, PedestalBlockEntity pedestalBlockEntity) {
        lastTick = tick;
        tick++;
    }

    public static void serverTick(World world, BlockPos pos, BlockState blockState, PedestalBlockEntity pedestalBlockEntity) {

    }

    public static int getTick() {
        return tick;
    }

    public static int getLastTick() {
        return lastTick;
    }
}
