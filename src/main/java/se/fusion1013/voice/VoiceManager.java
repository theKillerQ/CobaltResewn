package se.fusion1013.voice;

import de.maxhenkel.voicechat.api.Group;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.packets.MicrophonePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import se.fusion1013.Main;
import se.fusion1013.items.CobaltItem;
import se.fusion1013.items.CobaltItems;
import se.fusion1013.items.misc.WalkieTalkieItem;
import se.fusion1013.util.item.ItemUtil;

import java.util.UUID;

public class VoiceManager {

    private static VoiceManager INSTANCE;

    public static final String GLOBAL_GROUP_NAME = "global";

    private VoiceManager() {
    }

    /// region Voice chat listeners

    public void onMicPacket(MicrophonePacketEvent event) {
        VoicechatConnection senderConnection = event.getSenderConnection();

        if (senderConnection == null) return;
        if (!(senderConnection.getPlayer().getPlayer() instanceof PlayerEntity senderPlayer)) return;
        if (!senderPlayer.hasPermissionLevel(2)) return;

        // Sends audio from players not in the global group, to the global group
        sendMicPacketToGlobalGroup(event, senderConnection, senderPlayer);
    }

    private void sendWalkieTalkieSound(MicrophonePacketEvent event, VoicechatConnection senderConnection, PlayerEntity senderPlayer) {
        var senderWalkie = ItemUtil.getHeldItemOfType(senderPlayer, CobaltItems.MiscItems.WALKIE_TALKIE);
        if (senderWalkie == null) return;

        if (!WalkieTalkieItem.isActivate(senderWalkie) || WalkieTalkieItem.isMute(senderWalkie)) return;

        int senderCanal = WalkieTalkieItem.getCanal(senderWalkie);

        var api = event.getVoicechat();

        // Iterate over all players on the server
        for (PlayerEntity receiverPlayer : senderPlayer.getServer().getPlayerManager().getPlayerList()) {
            if (receiverPlayer.getUuid().equals(senderPlayer.getUuid())) continue; // Do not send to self
            // TODO: Check cross dimensional support

            ItemStack receiverWalkie = ItemUtil.getHeldItemOfType(receiverPlayer, CobaltItems.MiscItems.WALKIE_TALKIE);
            if (receiverWalkie == null) continue;

            int receiverRange = WalkieTalkieItem.getRange(receiverWalkie);
            int receiverCanal = WalkieTalkieItem.getCanal(receiverWalkie);

            if (receiverCanal != senderCanal) continue;
            if (!canBroadcastToReceiver(senderPlayer, receiverPlayer, receiverRange)) continue;

            VoicechatConnection connection = api.getConnectionOf(receiverPlayer.getUuid());
            if (connection == null) continue;

            api.sendStaticSoundPacketTo(connection, event.getPacket().staticSoundPacketBuilder().build());
        }
    }

    private boolean canBroadcastToReceiver(PlayerEntity senderPlayer, PlayerEntity receiverPlayer, int receiverRange) {
        return senderPlayer.getPos().isInRange(receiverPlayer.getPos(), receiverRange);
    }

    private void sendMicPacketToGlobalGroup(MicrophonePacketEvent event, VoicechatConnection senderConnection, PlayerEntity senderPlayer) {

        // If the player is already in the global group, ignore
        var playerGroup = senderConnection.getGroup();
        if (playerGroup != null && playerGroup.getName().equalsIgnoreCase(GLOBAL_GROUP_NAME)) return;

        VoicechatServerApi api = event.getVoicechat();

        var globalGroup = getGroupFromName(api, GLOBAL_GROUP_NAME);
        if (globalGroup == null) return;

        // Iterate over every player on the server
        for (PlayerEntity receiverPlayer : senderPlayer.getServer().getPlayerManager().getPlayerList()) {
            if (receiverPlayer.getUuid().equals(senderPlayer.getUuid())) continue; // Do not send to self

            // Check if the receiving player is actually connected to the voice chat
            VoicechatConnection connection = api.getConnectionOf(receiverPlayer.getUuid());
            if (connection == null) continue;

            // Only send audio to players in the global group
            var receiverGroup = connection.getGroup();
            if (receiverGroup == null) continue;
            if (receiverGroup != globalGroup) continue;

            api.sendStaticSoundPacketTo(senderConnection, event.getPacket().toStaticSoundPacket());
        }
    }

    private Group getGroupFromName(VoicechatServerApi api, String name) {
        for (Group group : api.getGroups()) {
            if (group.getName().equalsIgnoreCase(name)) return group;
        }
        return null;
    }

    /// endregion

    public static VoiceManager getInstance() {
        if (INSTANCE == null) INSTANCE = new VoiceManager();
        return INSTANCE;
    }

}
