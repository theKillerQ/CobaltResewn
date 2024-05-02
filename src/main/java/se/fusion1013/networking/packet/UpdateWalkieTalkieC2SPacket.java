package se.fusion1013.networking.packet;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import se.fusion1013.items.CobaltItems;
import se.fusion1013.items.misc.WalkieTalkieItem;
import se.fusion1013.networking.CobaltNetworkingConstants;
import se.fusion1013.util.item.ItemUtil;
import se.fusion1013.util.math.MathUtil;

public class UpdateWalkieTalkieC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {

        ItemStack stack = ItemUtil.getHeldItemOfType(player, CobaltItems.MiscItems.WALKIE_TALKIE);

        if (!(stack.getItem() instanceof WalkieTalkieItem) && !stack.hasNbt()) return;

        int index = buffer.readInt();
        boolean status = buffer.readBoolean();

        boolean activate = WalkieTalkieItem.isActivate(stack);
        boolean mute = WalkieTalkieItem.isMute(stack);
        int canal = WalkieTalkieItem.getCanal(stack);

        switch (index) {
            case 0 -> activate = !activate;
            case 1 -> {
                if (status) canal = MathUtil.loopValue(canal + 1, 1, 8);
                else canal = MathUtil.loopValue(canal - 1, 1, 8);
            }
            case 2 -> mute = !mute;
        }

        WalkieTalkieItem.setActivate(stack, activate);
        WalkieTalkieItem.setMute(stack, mute);
        WalkieTalkieItem.setCanal(stack, canal);

        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        packet.writeItemStack(stack);
        ServerPlayNetworking.send(player, CobaltNetworkingConstants.UPDATE_WALKIETALKIE_S2C, packet);
    }
}
