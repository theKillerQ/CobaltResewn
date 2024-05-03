package se.fusion1013.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import se.fusion1013.items.armor.CobaltArmorSet;
import se.fusion1013.util.item.ArmorUtil;

public class ArmorSetToggleAbilityC2S extends ServerPacketBase {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {

        // Iterate over all armor sets and activate the ability if the player is wearing it

        CobaltArmorSet.REGISTERED_ARMOR_SETS.forEach((key, set) -> {

            // Check if player is wearing the armor set,
            // If they are, activate the armor set ability

            if (!ArmorUtil.isWearingArmorSet(player, set.material)) return;
            // TODO
        });
    }

}
