package se.fusion1013.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.Main;
import se.fusion1013.block.entity.CobaltBlockEntityTypes;
import se.fusion1013.block.entity.LightHolderBlockEntity;
import se.fusion1013.items.CobaltItems;
import se.fusion1013.sounds.CobaltSoundEvents;
import se.fusion1013.util.item.ItemUtil;

public class LightHolderBlock extends BlockWithEntity {

    public static final MapCodec<LightHolderBlock> CODEC = createCodec(LightHolderBlock::new);

    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 14, 14);

    public LightHolderBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(LIT, false));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if (state.get(LIT)) {
            Vec3d center = pos.toCenterPos();
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                    getParticleOffset(random) + center.x,
                    getParticleOffset(random) + pos.getY() + (1/16f)*6,
                    getParticleOffset(random) + center.z,
                    0, 0, 0
            );
        }
    }

    private double getParticleOffset(Random random) {
        return (random.nextDouble() - 0.5) * 0.5;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() == CobaltItems.MiscItems.LIGHT_SOUL) return tryInsertSoul(world, pos, player, hand);
        else return tryTakeSoul(world, pos, player, hand);
    }

    private ActionResult tryInsertSoul(World world, BlockPos pos, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        BlockState state = world.getBlockState(pos);

        if (stack.getItem() != CobaltItems.MiscItems.LIGHT_SOUL) return ActionResult.FAIL;
        if (state.get(LIT).booleanValue()) return ActionResult.FAIL;

        world.setBlockState(pos, state.with(LIT, true));
        player.getStackInHand(hand).setCount(0);

        if (!world.isClient) {
            world.playSound(null, pos, CobaltSoundEvents.LIGHT_HOLDER_INSERT, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

        return ActionResult.SUCCESS;
    }

    private ActionResult tryTakeSoul(World world, BlockPos pos, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        BlockState state = world.getBlockState(pos);

        if (!stack.isEmpty()) return ActionResult.FAIL;
        if (!state.get(LIT).booleanValue()) return ActionResult.FAIL;

        world.setBlockState(pos, state.with(LIT, false));
        player.setStackInHand(hand, CobaltItems.MiscItems.LIGHT_SOUL.getDefaultStack());

        if (!world.isClient) {
            world.playSound(null, pos, CobaltSoundEvents.LIGHT_HOLDER_REMOVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, CobaltBlockEntityTypes.LIGHT_HOLDER_BLOCK_ENTITY, world.isClient ? LightHolderBlockEntity::clientTick : LightHolderBlockEntity::serverTick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightHolderBlockEntity(pos, state);
    }
}
