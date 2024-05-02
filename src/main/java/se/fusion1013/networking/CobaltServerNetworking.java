package se.fusion1013.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import se.fusion1013.items.armor.CobaltArmorSet;
import se.fusion1013.networking.packet.UpdateSpeakerC2SPacket;
import se.fusion1013.networking.packet.UpdateWalkieTalkieC2SPacket;

import static se.fusion1013.networking.CobaltNetworkingConstants.*;

public class CobaltServerNetworking {

    public static void register() {
        // Register global receivers
        ServerPlayNetworking.registerGlobalReceiver(KEY_ARMOR_TRIGGER_ID_C2S, CobaltArmorSet::onKeyArmorTrigger);
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_WALKIETALKIE_C2S, UpdateWalkieTalkieC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_SPEAKER_C2S, UpdateSpeakerC2SPacket::receive);
    }

}
