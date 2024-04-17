package se.fusion1013.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.block.entity.CopperCrateBlockEntity;

import java.util.List;

public class CopperCrateBlock extends Block implements Waterloggable, BlockEntityProvider {

    public static final MapCodec<CopperCrateBlock> CODEC = createCodec(CopperCrateBlock::new);
    private static final BooleanProperty WATERLOGGED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 14, 15);

    protected CopperCrateBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.WATERLOGGED);
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CopperCrateBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof CopperCrateBlockEntity copperCrateBlockEntity) {
            if (world.isClient) return ActionResult.CONSUME;
            else {
                ItemStack itemStack = player.getStackInHand(hand);
                ItemStack itemStack2 = copperCrateBlockEntity.getStack();
                if (!itemStack.isEmpty() && (itemStack2.isEmpty() || ItemStack.canCombine(itemStack2, itemStack) && itemStack2.getCount() < itemStack2.getMaxCount())) {
                    player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
                    ItemStack itemStack3 = player.isCreative() ? itemStack.copyWithCount(1) : itemStack.split(1);
                    float pitchMod; // The amount to increase the pitch of the insert sound by
                    if (copperCrateBlockEntity.isEmpty()) {
                        copperCrateBlockEntity.setStack(itemStack3);
                        pitchMod = (float)itemStack3.getCount() / (float)itemStack3.getMaxCount();
                    } else {
                        itemStack2.increment(1);
                        pitchMod = (float)itemStack2.getCount() / (float)itemStack2.getMaxCount();
                    }

                    world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_DECORATED_POT_INSERT, SoundCategory.BLOCKS, 1.0f, 0.7f + 0.5f * pitchMod, false);

                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.DUST_PLUME, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 7, 0, 0, 0, 0);
                    }

                    copperCrateBlockEntity.markDirty();
                } else {
                    world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_DECORATED_POT_INSERT_FAIL, SoundCategory.BLOCKS, 1, 1, false);
                }

                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                return ActionResult.SUCCESS;
            }
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof CopperCrateBlockEntity copperCrateBlockEntity) {
            return copperCrateBlockEntity.asStack();
        } else {
            return super.getPickStack(world, pos, state);
        }
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }
}
