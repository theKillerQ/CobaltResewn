package se.fusion1013.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import se.fusion1013.effect.EnvironmentEffect;
import se.fusion1013.gui.WalkieTalkieScreen;
import se.fusion1013.items.CobaltItems;
import se.fusion1013.render.effect.EnvironmentEffectValues;
import se.fusion1013.util.item.ItemUtil;

public class SetFogValueS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {
        client.execute(() -> {
            var type = buffer.readString();

            switch (type) {
                case "start" -> EnvironmentEffectValues.setStart(buffer.readFloat());
                case "end" -> EnvironmentEffectValues.setEnd(buffer.readFloat());
                case "color" -> EnvironmentEffectValues.setColor(buffer.readFloat());
                case "padding" -> EnvironmentEffectValues.setPaddingDuration(buffer.readInt());
            }
        });
    }
}
