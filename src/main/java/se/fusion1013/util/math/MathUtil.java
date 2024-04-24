package se.fusion1013.util.math;

import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class MathUtil {

    public static List<Vector3d> getPointsOnLine(Vector3d start, Vector3d end, float density) {
        List<Vector3d> positions = new ArrayList<>();

        double distance = start.distance(end);
        long steps = Math.round(density * distance);
        Vector3d direction = new Vector3d(end).sub(start).normalize();

        for (int i = 0; i < steps; i++) {
            Vector3d pos = new Vector3d(start).add(new Vector3d(direction).mul(i / density));
            positions.add(pos);
        }

        return positions;
    }

}
