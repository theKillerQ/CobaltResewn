package se.fusion1013.voice;

import uk.me.berndporr.iirj.Butterworth;

public class RadioFilter implements IAudioFilter {
    private final static int SAMPLE_RATE = 48000;

    private final Butterworth HIGHPASS = new Butterworth();
    private final Butterworth BANDPASS = new Butterworth();

    public RadioFilter() {
        HIGHPASS.highPass(4, SAMPLE_RATE, 2000);
        BANDPASS.bandPass(4, SAMPLE_RATE, 50, 2600);
    }

    public short[] apply(short[] rawData, double volumeModifier) {
        double[] doubleData = new double[rawData.length];
        for (int i = 0; i < rawData.length; i++) {
            doubleData[i] = rawData[i];

            doubleData[i] = HIGHPASS.filter(doubleData[i]);
            doubleData[i] = BANDPASS.filter(doubleData[i]);
            doubleData[i] = volume(doubleData[i], 10d * volumeModifier);
        }

        for (int i = 0; i < doubleData.length; i++) {
            rawData[i] = (short) doubleData[i];
        }
        return rawData;
    }

    private double volume(double audio, double level) {
        return audio * level;
    }
}
