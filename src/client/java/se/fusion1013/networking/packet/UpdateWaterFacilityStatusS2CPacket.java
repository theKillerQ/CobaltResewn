package se.fusion1013.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import se.fusion1013.util.FacilityStatus;

public class UpdateWaterFacilityStatusS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {
        client.execute(() -> {
            FacilityStatus.POWER_CURRENT = buffer.readInt();
            FacilityStatus.PRESSURE_CURRENT = buffer.readInt();
        });
    }
}
