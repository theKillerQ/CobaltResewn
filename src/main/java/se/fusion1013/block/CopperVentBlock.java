package se.fusion1013.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CopperVentBlock extends WallMountedBlock {

    public static final MapCodec<CopperVentBlock> CODEC = createCodec(CopperVentBlock::new);

    protected static final VoxelShape FLOOR_SHAPE;
    protected static final VoxelShape CEILING_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape EAST_SHAPE;
    protected static final VoxelShape WEST_SHAPE;

    private static final float PARTICLE_VELOCITY = 0.05f;

    protected CopperVentBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(FACE, BlockFace.WALL));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(FACE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        switch (state.get(FACE)) {
            case FLOOR: return FLOOR_SHAPE;
            case CEILING: return CEILING_SHAPE;
            case WALL:
                switch (direction) {
                    case NORTH: return NORTH_SHAPE;
                    case SOUTH: return SOUTH_SHAPE;
                    case EAST: return EAST_SHAPE;
                    case WEST: return WEST_SHAPE;
                }
                break;
        }

        return NORTH_SHAPE; // Should never happen?
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(6) == 0) {
            world.playSound(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, .3f, 1.2f, false);
        }

        Direction direction = state.get(FACING);
        switch (state.get(FACE)) {
            case FLOOR:
                world.addParticle(ParticleTypes.CLOUD, pos.getX() + .5, pos.getY(), pos.getZ() + .5, 0, PARTICLE_VELOCITY, 0);
                break;
            case CEILING:
                world.addParticle(ParticleTypes.CLOUD, pos.getX() + .5, pos.getY()+1, pos.getZ() + .5, 0, -PARTICLE_VELOCITY, 0);
                break;
            case WALL:
                switch (direction) {
                    case NORTH:
                        world.addParticle(ParticleTypes.CLOUD, pos.getX() + .5, pos.getY()+.5, pos.getZ()+1, 0, 0, -PARTICLE_VELOCITY);
                        break;
                    case SOUTH:
                        world.addParticle(ParticleTypes.CLOUD, pos.getX() + .5, pos.getY()+.5, pos.getZ(), 0, 0, PARTICLE_VELOCITY);
                        break;
                    case EAST:
                        world.addParticle(ParticleTypes.CLOUD, pos.getX(), pos.getY()+.5, pos.getZ()+.5, PARTICLE_VELOCITY, 0, 0);
                        break;
                    case WEST:
                        world.addParticle(ParticleTypes.CLOUD, pos.getX()+1, pos.getY()+.5, pos.getZ()+.5, -PARTICLE_VELOCITY, 0, 0);
                        break;
                }
                break;
        }
    }

    @Override
    protected MapCodec<? extends WallMountedBlock> getCodec() {
        return CODEC;
    }

    static {
        CEILING_SHAPE = Block.createCuboidShape(3.0D, 14.0D, 3.0D, 13.0D, 16.0D, 13.0D);
        FLOOR_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D);

        NORTH_SHAPE = Block.createCuboidShape(3.0D, 3.0D, 14.0D, 13.0D, 13.0D, 16.0D);
        SOUTH_SHAPE = Block.createCuboidShape(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 2.0D);

        EAST_SHAPE = Block.createCuboidShape(0.0D, 3.0D, 3.0D, 2.0D, 13.0D, 13.0D);
        WEST_SHAPE = Block.createCuboidShape(14.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D);
    }
}
