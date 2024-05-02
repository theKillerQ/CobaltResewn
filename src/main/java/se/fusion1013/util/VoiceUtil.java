package se.fusion1013.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import se.fusion1013.config.CobaltModConfig;

public class VoiceUtil {

    public static boolean canBroadcastToReceiver(World senderWorld, World receiverWorld, Vec3d senderPos, Vec3d receiverPos, int receiverRange) {
        double senderCoordinateScale = senderWorld.getDimension().coordinateScale();
        double receiverCoordinateScale = receiverWorld.getDimension().coordinateScale();

        double appliedRange = CobaltModConfig.applyDimensionScale ? receiverRange / Math.max(senderCoordinateScale, receiverCoordinateScale) : receiverRange;
        return senderPos.isInRange(receiverPos, appliedRange);
    }

}
