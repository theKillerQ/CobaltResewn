package se.fusion1013.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import se.fusion1013.items.ItemSet;

/**
 * Sent from the client to the server when a player presses the item set triggered ability keybind.
 * Routes the call to the worn item set.
 */
public class ItemSetTriggerAbilityC2S extends ServerPacketBase {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {

        // Iterate over all sets and trigger the ability if the player has it active

        for (ItemSet set : ItemSet.registeredSets()) {
            if (!set.hasItems(player)) continue; // Check if player has item set

            // Trigger the item set ability
            set.triggerSetAbility(player);
        }
    }

}
