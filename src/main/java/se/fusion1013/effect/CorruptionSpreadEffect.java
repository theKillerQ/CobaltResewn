package se.fusion1013.effect;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3i;
import se.fusion1013.block.CobaltBlocks;

public class CorruptionSpreadEffect extends StatusEffect {
    public CorruptionSpreadEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xdb05fa);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        var world = entity.getWorld();
        var pos = entity.getBlockPos();
        var posBelow = pos.subtract(new Vec3i(0, 1, 0));
        var blockState = world.getBlockState(posBelow);

        if (blockState.getBlock() == CobaltBlocks.SCULK_SPREADER) return;
        if (!blockState.isSolid()) return;

        world.setBlockState(posBelow, CobaltBlocks.SCULK_SPREADER.getDefaultState());
    }
}
