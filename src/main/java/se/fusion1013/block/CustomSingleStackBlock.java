package se.fusion1013.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.block.entity.CustomSingleStackInventoryBlockEntity;

public abstract class CustomSingleStackBlock extends Block implements BlockEntityProvider {

    public static final DirectionProperty FACING;

    public CustomSingleStackBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /// region Insert / Extract Items

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (world.isClient) return ActionResult.SUCCESS;
        Inventory blockEntity = (Inventory) world.getBlockEntity(blockPos);

        if (!player.getStackInHand(hand).isEmpty()) {
            // Check what is the first open slot and put an item from the player's hand there
            if (blockEntity.getStack(0).isEmpty()) {
                // Insert the item into the inventory and remove it from the player
                blockEntity.setStack(0, player.getStackInHand(hand).copy());
                player.getStackInHand(hand).setCount(0);

                blockEntity.markDirty();

                // Play item insert sound and display particle
                world.playSound(null, blockPos, SoundEvents.BLOCK_DECORATED_POT_INSERT, SoundCategory.BLOCKS, 1.0f, 0.7f);
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.DUST_PLUME, blockPos.getX() + .5f, blockPos.getY() + .9f, blockPos.getZ() + .5f, 7, 0, 0, 0, 0);
                }
            } else {
                // If the inventory is full, play fail effects
                world.playSound(null, blockPos, SoundEvents.BLOCK_DECORATED_POT_INSERT_FAIL, SoundCategory.BLOCKS, 1.0f, 0.7f);
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.SMOKE, blockPos.getX() + .5f, blockPos.getY() + .9f, blockPos.getZ() + .5f, 7, 0, 0, 0, 0);
                }
            }
        } else {
            // If the player is not holding anything we'll get give him the items in the block entity one by one

            // Find the first slot that has an item and give it to the player
            if (!blockEntity.getStack(0).isEmpty()) {
                player.getInventory().offerOrDrop(blockEntity.getStack(0));
                blockEntity.removeStack(0);

                blockEntity.markDirty();
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CustomSingleStackInventoryBlockEntity) {
                ItemScatterer.spawn(world, pos, (CustomSingleStackInventoryBlockEntity)blockEntity);
                // update comparators
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    /// endregion
}
