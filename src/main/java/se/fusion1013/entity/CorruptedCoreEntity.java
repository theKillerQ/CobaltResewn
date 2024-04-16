package se.fusion1013.entity;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.Main;
import se.fusion1013.util.block.BlockSpreadManager;

import java.util.Iterator;
import java.util.Set;

public class CorruptedCoreEntity extends HostileEntity {

    private final ServerBossBar bossBar;
    private final SculkSpreadManager spreadManager;
    private final BlockSpreadManager blockSpreadManager;
    private final net.minecraft.util.math.random.Random random;

    private CorruptedCoreState state = CorruptedCoreState.ALIVE;

    private static final BlockSpreadManager.IBlockSpreadReplacer replacer = new BlockSpreadManager.IBlockSpreadReplacer() {
        @Override
        public BlockState replaceWith(BlockState blockState) {
            if (blockState.getBlock() == Blocks.SCULK) return Blocks.STONE.getDefaultState();
            if (blockState.getBlock() == Blocks.SCULK_VEIN) return Blocks.AIR.getDefaultState();
            if (blockState.getBlock() == Blocks.SCULK_CATALYST) return Blocks.AIR.getDefaultState();
            if (blockState.getBlock() == Blocks.SCULK_SENSOR) return Blocks.AIR.getDefaultState();
            if (blockState.getBlock() == Blocks.SCULK_SHRIEKER) return Blocks.AIR.getDefaultState();
            return null;
        }
    };

    public CorruptedCoreEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = (ServerBossBar) (new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS)).setDarkenSky(false);
        this.spreadManager = SculkSpreadManager.create();
        this.random = Random.create();
        this.blockSpreadManager = BlockSpreadManager.create();
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {}

    @Override
    public void move(MovementType movementType, Vec3d movement) {}

    @Override
    public boolean damage(DamageSource source, float amount) {

        if (getWorld().isClient) return super.damage(source, amount);

        switch (state) {
            case ALIVE -> damagedAlive(source, amount);
            case DYING -> damagedDying(source, amount);
        }

        return super.damage(source, amount);
    }

    private void damagedAlive(DamageSource source, float amount) {
        var charge = Math.round(Math.min(amount, 4));

        this.spreadManager.spread(BlockPos.ofFloored(this.getX()-2, this.getY()-1, this.getZ()), charge);
        this.spreadManager.spread(BlockPos.ofFloored(this.getX()+2, this.getY()-1, this.getZ()), charge);
        this.spreadManager.spread(BlockPos.ofFloored(this.getX(), this.getY()-1, this.getZ()-2), charge);
        this.spreadManager.spread(BlockPos.ofFloored(this.getX(), this.getY()-1, this.getZ()+2), charge);
    }

    private void damagedDying(DamageSource source, float amount) {

    }

    private float getHealthPercentage() {
        return (getHealth() - (getMaxHealth() * 0.5f)) / (getMaxHealth() * 0.5f);
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        if (getHealthPercentage() < 0 && state == CorruptedCoreState.ALIVE) {
            switchState(CorruptedCoreState.DYING);
        }

        switch (state) {
            case ALIVE -> aliveTick();
            case DYING -> dyingTick();
        }
    }

    @Override
    public void tick() {
        super.tick();

        // Spawn dying particles
        if (getWorld().isClient && state == CorruptedCoreState.DYING) {
            getWorld().addParticle(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 1, 1, 1);
        }
    }

    private void aliveTick() {
        var healthPercent = getHealthPercentage();
        this.bossBar.setPercent(healthPercent);
        updatePlayers();
        this.spreadManager.tick(getWorld(), BlockPos.ofFloored(this.getX(), this.getY(), this.getZ()), random, true);
    }

    private void dyingTick() {
        this.blockSpreadManager.tick(getWorld());

        if (blockSpreadManager.getCursors().size() > 0) return;

        // There are no cursors left, remove the entity
        remove(RemovalReason.DISCARDED);
    }

    private void switchState(CorruptedCoreState newState) {
        if (newState == CorruptedCoreState.DYING) {
            this.bossBar.setVisible(false);

            // Start spreading grass
            for (int i = 0; i < 3; i++) {
                this.blockSpreadManager.spreadFrom(BlockPos.ofFloored(this.getX()-2, this.getY()-1, this.getZ()), 30, replacer);
                this.blockSpreadManager.spreadFrom(BlockPos.ofFloored(this.getX()+2, this.getY()-1, this.getZ()), 30, replacer);
                this.blockSpreadManager.spreadFrom(BlockPos.ofFloored(this.getX(), this.getY()-1, this.getZ()-2), 30, replacer);
                this.blockSpreadManager.spreadFrom(BlockPos.ofFloored(this.getX(), this.getY()-1, this.getZ()+2), 30, replacer);
            }

            setInvisible(true);

            getWorld().playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_WARDEN_DEATH, SoundCategory.HOSTILE, 1f, 1f);

            // Spawn dying particles
            if (getWorld().isClient && state == CorruptedCoreState.DYING) {
                getWorld().addParticle(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 1, 1, 1);
            }
        }

        state = newState;
    }

    private void updatePlayers() {
        for (PlayerEntity playerEntity : this.getWorld().getPlayers()) {
            ServerPlayerEntity player = (ServerPlayerEntity) playerEntity;
            if (player.distanceTo(this) < 16) bossBar.addPlayer(player);
            else bossBar.removePlayer(player);
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        this.bossBar.setPercent(0);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        bossBar.removePlayer(player);
    }

    public static DefaultAttributeContainer.Builder createCorruptedCoreAttributes() {
        return DefaultAttributeContainer.builder()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                .add(EntityAttributes.GENERIC_ARMOR)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS)
                .add(EntityAttributes.GENERIC_MAX_ABSORPTION)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
    }

    private enum CorruptedCoreState {
        ALIVE,
        DYING
    }
}
