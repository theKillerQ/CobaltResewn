package se.fusion1013.voice;

public interface IAudioFilter {
    short[] apply(short[] rawData, double volumeModifier);
}
