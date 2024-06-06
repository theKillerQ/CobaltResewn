package se.fusion1013.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import se.fusion1013.gui.ItemDisplayScreen;

public class OpenItemDisplayScreenS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {
        client.execute(() -> {
            if (client.player == null) return;
            var blockPos = buffer.readBlockPos();
            if (blockPos == null) return;
            // new ItemDisplayScreen(blockPos);
        });
    }
}
