package galaxygameryt.cultivation_mastery.util.helpers;

public class MathHelper {
    public static float roundFloat(float value, int scale) {
        return (float) (Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale));
    }
}
