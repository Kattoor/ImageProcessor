import java.util.Arrays;
import java.util.function.Function;

public class ProcessingFunction3 implements Function<ImageData, Integer> {
    
    @Override
    public Integer apply(ImageData imageData) {
        int[] bounds = new int[]{0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250};
        return new RGB(
                Arrays.stream(bounds).reduce((bound, acc) -> Math.abs(imageData.getRgb().getRed() - bound) < Math.abs(imageData.getRgb().getRed() - acc) ? bound : acc).getAsInt(),
                Arrays.stream(bounds).reduce((bound, acc) -> Math.abs(imageData.getRgb().getGreen() - bound) < Math.abs(imageData.getRgb().getGreen() - acc) ? bound : acc).getAsInt(),
                Arrays.stream(bounds).reduce((bound, acc) -> Math.abs(imageData.getRgb().getBlue() - bound) < Math.abs(imageData.getRgb().getBlue() - acc) ? bound : acc).getAsInt()).getValue();
    }
}
