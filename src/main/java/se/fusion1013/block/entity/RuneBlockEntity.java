package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import se.fusion1013.block.CobaltBlocks;
import se.fusion1013.block.RuneBlock;
import se.fusion1013.items.CobaltItems;

public class RuneBlockEntity extends BlockEntity {
    public RuneBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.RUNE_BLOCK, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, RuneBlockEntity blockEntity) {
        if (world.isClient) return;

        boolean playerInRange = false;

        var players = world.getPlayers();
        for (PlayerEntity player : players) {
            var mainHandItem = player.getMainHandStack().getItem();
            var offhandItem = player.getOffHandStack().getItem();

            if (mainHandItem == CobaltBlocks.RUNE_BLOCK.asItem() || mainHandItem == CobaltItems.MiscItems.RUNE_MODIFIER) playerInRange = true;
            if (offhandItem == CobaltBlocks.RUNE_BLOCK.asItem() || offhandItem == CobaltItems.MiscItems.RUNE_MODIFIER) playerInRange = true;

            if (
                    mainHandItem != Items.SOUL_LANTERN
                ) continue;

            // Player is holding lantern, check distance
            if (player.getPos().distanceTo(pos.toCenterPos()) < 6) playerInRange = true;
        }

        world.setBlockState(pos, state.with(RuneBlock.VISIBLE, playerInRange));
    }
}
