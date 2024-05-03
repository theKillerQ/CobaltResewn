package se.fusion1013.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleBlockEntity extends BlockEntity {

    public ParticleBlockEntity(BlockPos pos, BlockState state) {
        super(CobaltBlockEntityTypes.PARTICLE_BLOCK, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ParticleBlockEntity blockEntity) {
        if (!world.isClient()) return;

        var centerPos = pos.toCenterPos();

        world.addParticle(ParticleTypes.END_ROD, centerPos.getX(), centerPos.getY()+1, centerPos.getZ(), 0, 0, 0);
    }
}
