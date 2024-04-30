package se.fusion1013.voice;

import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.VolumeCategory;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import de.maxhenkel.voicechat.api.opus.OpusEncoder;
import org.jetbrains.annotations.Nullable;
import se.fusion1013.Main;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VoiceChatIntegration implements VoicechatPlugin {

    @Nullable
    public static VoicechatServerApi voiceChatAPI;

    public static OpusEncoder encoder;
    public static OpusDecoder decoder;

    private ExecutorService executorService;

    public VoiceChatIntegration() {
        executorService = Executors.newSingleThreadExecutor(runnable -> {
           Thread thread = new Thread(runnable);
           thread.setName("CobaltVoiceThread");
           thread.setUncaughtExceptionHandler((t, e) -> Main.LOGGER.error("Error in voice chat integration voice thread: " + e));
           thread.setDaemon(true);
           return thread;
        });
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted);
        registration.registerEvent(MicrophonePacketEvent.class, micPacket -> VoiceManager.getInstance().onMicPacket(micPacket));
    }

    private void onServerStarted(VoicechatServerStartedEvent event) {
        voiceChatAPI = event.getVoicechat();
        encoder = voiceChatAPI.createEncoder();
        decoder = voiceChatAPI.createDecoder();

        /*
        VolumeCategory speakers = voiceChatAPI.volumeCategoryBuilder()
                // Add speaker categories here if needed
                .build();
        voiceChatAPI.registerVolumeCategory(speakers);
         */
    }

    @Override
    public String getPluginId() {
        return Main.MOD_NAMESPACE;
    }
}
