import java.util.function.Function;

public class Circlify implements Function<ImageData, Integer> {

    private int radius;

    Circlify(int radius) {
        this.radius = radius;
    }

    @Override
    public Integer apply(ImageData imageData) {
        int circleRadius = radius;
        int modX = imageData.getX() % (circleRadius * 2);
        int modY = imageData.getY() % (circleRadius * 2);

        if (ColorFunctions.pointInCircle(modX, modY, circleRadius) && !(imageData.getX() - modX + circleRadius >= imageData.getBufferedImage().getWidth() || imageData.getY() - modY + circleRadius >= imageData.getBufferedImage().getHeight()))
            return new RGB(imageData.getBufferedImage().getRGB(imageData.getX() - modX + circleRadius, imageData.getY() - modY + circleRadius)).getValue();
        else
            return new RGB(255, 255, 255).getValue();

    }
}
