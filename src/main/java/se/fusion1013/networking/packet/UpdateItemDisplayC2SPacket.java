package se.fusion1013.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import se.fusion1013.Main;
import se.fusion1013.block.entity.ItemDisplayBlockEntity;
import se.fusion1013.screen.ItemDisplayScreenHandler;

public class UpdateItemDisplayC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {

        var blockPos = buffer.readBlockPos();

        var offset = buffer.readVector3f();
        var offsetFrequency = buffer.readVector3f();
        var offsetAmplitude = buffer.readVector3f();

        var scale = buffer.readVector3f();
        var scaleFrequency = buffer.readVector3f();
        var scaleAmplitude = buffer.readVector3f();

        var rotation = buffer.readVector3f();
        var rotationSpeed = buffer.readVector3f();

        server.execute(() -> {
            var world = player.getWorld();

            var blockEntity = server.getWorld(player.getWorld().getRegistryKey()).getBlockEntity(blockPos);

            if (blockEntity instanceof ItemDisplayBlockEntity itemDisplayBlockEntity) {
                itemDisplayBlockEntity.setOffset(offset);
                itemDisplayBlockEntity.setOffsetFrequency(offsetFrequency);
                itemDisplayBlockEntity.setOffsetAmplitude(offsetAmplitude);

                itemDisplayBlockEntity.setScale(scale);
                itemDisplayBlockEntity.setScaleFrequency(scaleFrequency);
                itemDisplayBlockEntity.setScaleAmplitude(scaleAmplitude);

                itemDisplayBlockEntity.setRotation(rotation);
                itemDisplayBlockEntity.setRotationSpeed(rotationSpeed);

                itemDisplayBlockEntity.markDirty();
                world.updateListeners(blockPos, itemDisplayBlockEntity.getCachedState(), world.getBlockState(blockPos), Block.NOTIFY_LISTENERS);
            }
        });
    }
}
