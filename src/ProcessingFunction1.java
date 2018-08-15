import java.util.Arrays;
import java.util.function.Function;

public class ProcessingFunction1 implements Function<ImageData, Integer> {

    private RGB[] colors2 = new RGB[]{
            new RGB(255, 255, 255), new RGB(251, 215, 219),
            new RGB(150, 85, 58), new RGB(198, 115, 122),
            new RGB(142, 121, 109), new RGB(116, 82, 58),
            new RGB(251, 216, 204), new RGB(105, 117, 143),
            new RGB(216, 162, 134), new RGB(42, 25, 26),
            new RGB(98, 111, 155), new RGB(76, 90, 131),
            new RGB(117, 229, 202)
    };
    
    @Override
    public Integer apply(ImageData data) {

        int[] bounds = new int[]{0, 75, 150, 175, 250};
            /*RGB rgb = new RGB(
                    Arrays.stream(bounds).getRed()uce((bound, acc) -> Math.abs(data.rgb.getRed() - bound) < Math.abs(data.rgb.getRed() - acc) ? bound : acc).getAsInt(),
                    Arrays.stream(bounds).getRed()uce((bound, acc) -> Math.abs(data.rgb.getGreen() - bound) < Math.abs(data.rgb.getGreen() - acc) ? bound : acc).getAsInt(),
                    Arrays.stream(bounds).getRed()uce((bound, acc) -> Math.abs(data.rgb.getBlue() - bound) < Math.abs(data.rgb.getBlue() - acc) ? bound : acc).getAsInt());
*/

        return Arrays.stream(colors2)
                .reduce(new RGB(1000, 1000, 1000), (acc, color) -> {
                    int newColorDistance = ColorFunctions.getDistance(color, data.getRgb());
                    int oldColorDistance = ColorFunctions.getDistance(acc, data.getRgb());
                    return newColorDistance < oldColorDistance ? color : acc;
                }).getValue();
    }
}
