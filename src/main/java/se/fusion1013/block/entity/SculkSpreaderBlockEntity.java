package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import se.fusion1013.block.CobaltBlocks;

public class SculkSpreaderBlockEntity extends BlockEntity {

    private static final int MAX_ALIVE_TICK = 200;

    public final SculkSpreadManager spreadManager;
    public final Random random;

    public int aliveTicks = 0;

    public SculkSpreaderBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.SCULK_SPREADER, pos, state);
        spreadManager = SculkSpreadManager.create();
        random = Random.create();
    }

    public static void tick(World world, BlockPos pos, BlockState state, SculkSpreaderBlockEntity blockEntity) {
        blockEntity.aliveTicks++;
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
