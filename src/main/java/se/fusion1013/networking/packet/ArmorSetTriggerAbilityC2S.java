package se.fusion1013.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import se.fusion1013.items.armor.CobaltArmorSet;
import se.fusion1013.util.item.ArmorUtil;

/**
 * Sent from the client to the server when a player presses the armor set triggered ability keybind.
 * Routes the call to the worn armor set.
 */
public class ArmorSetTriggerAbilityC2S extends ServerPacketBase {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {

        // Iterate over all armor sets and trigger the ability if the player is wearing it

        CobaltArmorSet.REGISTERED_ARMOR_SETS.forEach((key, set) -> {

            // Check if player is wearing the armor set,
            // If they are, trigger the armor set ability

            if (!ArmorUtil.isWearingArmorSet(player, set.material)) return;
            set.triggerArmorAbility(server, player);
        });
    }

}
