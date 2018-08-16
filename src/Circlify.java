import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class Circlify implements Function<ImageData, Integer> {

    private int radius;

    static class CirclifyTransition implements Transition<Circlify> {
        private int current;
        private boolean countUp = true;
        private int lower;
        private int upper;

        CirclifyTransition(int lower, int upper) {
            current = lower;
            this.lower = lower;
            this.upper = upper;
        }

        public Circlify get() {
                if (countUp && ++current >= upper)
                    countUp = false;
                else if (!countUp && --current <= lower)
                    countUp = true;

                return current == lower ? null : new Circlify(current);
        }

        @Override
        public int getCurrent() {
            return current;
        }

        @Override
        public int getLower() {
            return lower;
        }

        @Override
        public int getUpper() {
            return upper;
        }
    }

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
            return imageData.getRgb().getValue();

    }
}
