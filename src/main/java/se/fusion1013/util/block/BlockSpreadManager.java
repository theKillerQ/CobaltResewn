package se.fusion1013.util.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.Main;

import java.util.*;

public class BlockSpreadManager {

    public static final int MAX_CHARGE = 1000;
    private static final int MAX_CURSORS = 32;

    private final int extraBlockChance;
    private final int maxDistance;
    private final int spreadChance;
    private final int decayChance;
    private final int ticksPerUpdate;
    private int currentTick;

    private List<Cursor> cursors = new ArrayList<>();

    public BlockSpreadManager(int extraBlockChance, int maxDistance, int spreadChance, int decayChance, int ticksPerUpdate) {
        this.extraBlockChance = extraBlockChance;
        this.maxDistance = maxDistance;
        this.spreadChance = spreadChance;
        this.decayChance = decayChance;
        this.ticksPerUpdate = ticksPerUpdate;
        this.currentTick = 0;
    }

    public static BlockSpreadManager create() {
        return new BlockSpreadManager(10, 4, 10, 5, 3);
    }

    public void spreadFrom(BlockPos pos, int charge, IBlockSpreadReplacer replacer) {
        while (charge > 0) {
            int actualCharge = Math.min(charge, MAX_CHARGE);
            addCursor(new Cursor(pos, charge, replacer));
            charge -= actualCharge;
        }
    }

    private void addCursor(Cursor cursor) {
        if (cursors.size() < MAX_CURSORS) cursors.add(cursor);
    }

    public void tick(WorldAccess world) {
        if (cursors.isEmpty()) return;
        currentTick++;
        if (currentTick < ticksPerUpdate) return;
        currentTick = 0;

        // Step through the list backwards and update cursors
        // If cursor has no charge, remove it from the list
        for (int i = cursors.size() - 1; i >= 0; i--) {
            var cursor = cursors.get(i);
            cursor.step(world);

            if (cursor.charge > 0) continue;

            // TODO: Modify here if it turns out that merging charges is needed

            // Cursors charge is 0 or less; remove it
            cursors.remove(i);
        }
    }

    public int getExtraBlockChance() {
        return extraBlockChance;
    }
    public int getMaxDistance() {
        return maxDistance;
    }
    public int getSpreadChance() {
        return spreadChance;
    }
    public int getDecayChance() {
        return decayChance;
    }
    public List<Cursor> getCursors() { return cursors; }

    public static class Cursor {

        private final BlockPos origin;
        public int charge;
        private final IBlockSpreadReplacer replacer;

        private BlockPos pos;
        private final Random random;

        public Cursor(BlockPos origin, int charge, IBlockSpreadReplacer replacer) {
            this.origin = origin;
            this.charge = charge;
            this.replacer = replacer;
            this.random = Random.create();
            pos = origin;
        }

        public static Cursor create(BlockPos origin, int charge, IBlockSpreadReplacer replacer) {
            return new Cursor(origin, charge, replacer);
        }

        public void step(WorldAccess world) {
            if (!canStep(world, pos)) return;
            if (!canReplace(world, pos)) {
                endStep(world);
                return;
            }

            BlockState blockState = world.getBlockState(pos);
            var replaceWith = replacer.replaceWith(blockState);
            world.setBlockState(pos, replaceWith, 3);
            world.updateNeighbors(pos, world.getBlockState(pos).getBlock());

            world.playSound(null, pos, SoundEvents.BLOCK_SCULK_CHARGE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.syncWorldEvent(WorldEvents.SCULK_CHARGE, pos, 0);
            // TODO: Display particles

            endStep(world);
        }

        private void endStep(WorldAccess world) {
            charge--;
            selectNextBlockPos(world, random);
        }

        private void selectNextBlockPos(WorldAccess world, Random random) {
            var nearby = getNearbyReplaceable(world);
            if (nearby.length <= 0) return;
            pos = nearby[random.nextBetween(0, nearby.length - 1)];
        }

        private BlockPos[] getNearbyReplaceable(WorldAccess world) {
            List<BlockPos> replaceable = new ArrayList<>();
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos neighbor = pos.add(BlockPos.ofFloored(x, y, z));
                        if (canReplace(world, neighbor)) replaceable.add(neighbor);
                    }
                }
            }
            return replaceable.toArray(new BlockPos[0]);
        }

        private boolean canReplace(WorldAccess world, BlockPos pos) {
            var replaceWith = replacer.replaceWith(world.getBlockState(pos));
            return replaceWith != null;
        }

        private boolean canStep(WorldAccess world, BlockPos block) {
            if (charge <= 0) return false;
            if (world instanceof ServerWorld serverWorld) return serverWorld.shouldTickBlockPos(block);
            return false;
        }
    }

    public interface IBlockSpreadReplacer {
        BlockState replaceWith(BlockState blockState);
    }
}
