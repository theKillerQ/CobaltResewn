package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import se.fusion1013.block.CobaltBlocks;

/**
 * {@link BlockEntity} that spreads sculk around it for a specified number of ticks.
 */
public class SculkSpreaderBlockEntity extends BlockEntity {

    private static final int MAX_ALIVE_TICK = 200; // The number of ticks this block will exist for before getting removed.

    public final SculkSpreadManager spreadManager;
    public int aliveTicks = 0; // The number of ticks the block has existed for.

    public final Random random;

    public SculkSpreaderBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.SCULK_SPREADER, pos, state);
        spreadManager = SculkSpreadManager.create();
        random = Random.create();
    }

    public static void tick(World world, BlockPos pos, BlockState state, SculkSpreaderBlockEntity blockEntity) {
        blockEntity.aliveTicks++; // Increment

        // If it has been alive for more than the allowed time, replace it with sculk
        if (blockEntity.aliveTicks > MAX_ALIVE_TICK) {
            world.setBlockState(pos, Blocks.SCULK.getDefaultState());
            return;
        }

        // Tick the spread manager
        blockEntity.spreadManager.tick(world, pos, blockEntity.random, true);

        // If there are no cursors in the spread manager, spread
        if (blockEntity.spreadManager.getCursors().stream().count() <= 0) {
            blockEntity.spreadManager.spread(BlockPos.ofFloored(pos.getX()+1, pos.getY(), pos.getZ()), 5);
            blockEntity.spreadManager.spread(BlockPos.ofFloored(pos.getX()-1, pos.getY(), pos.getZ()), 5);
            blockEntity.spreadManager.spread(BlockPos.ofFloored(pos.getX(), pos.getY(), pos.getZ()+1), 5);
            blockEntity.spreadManager.spread(BlockPos.ofFloored(pos.getX(), pos.getY(), pos.getZ()-1), 5);
        }
    }


}
