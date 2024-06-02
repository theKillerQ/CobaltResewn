package se.fusion1013.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import se.fusion1013.gui.WalkieTalkieScreen;
import se.fusion1013.items.CobaltItems;
import se.fusion1013.networking.packet.OpenWalkieTalkieScreenS2CPacket;
import se.fusion1013.networking.packet.SetFogValueS2CPacket;
import se.fusion1013.networking.packet.UpdateWalkieTalkieS2CPacket;
import se.fusion1013.networking.packet.UpdateWaterFacilityStatusS2CPacket;
import se.fusion1013.util.item.ItemUtil;

public class CobaltClientNetworking {

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(CobaltNetworkingConstants.UPDATE_WF_STATUS_S2C, UpdateWaterFacilityStatusS2CPacket::receive);

        // Walkie talkie networking
        ClientPlayNetworking.registerGlobalReceiver(CobaltNetworkingConstants.OPEN_WALKIE_TALKIE_SCREEN_S2C, OpenWalkieTalkieScreenS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CobaltNetworkingConstants.UPDATE_WALKIETALKIE_S2C, UpdateWalkieTalkieS2CPacket::receive);

        ClientPlayNetworking.registerGlobalReceiver(CobaltNetworkingConstants.SET_FOG_VALUE_S2C, SetFogValueS2CPacket::receive);
    }

}
