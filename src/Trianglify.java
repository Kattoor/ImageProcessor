import java.util.function.Function;

public class Trianglify implements Function<ImageData, Integer> {

    private int triangleWidth;
    private int triangleHeight;

    static class TrianglifyTransition implements Transition<Trianglify> {
        private int current;
        private boolean countUp = true;
        private int lower;
        private int upper;

        TrianglifyTransition(int lower, int upper) {
            current = lower;
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public Trianglify get() {
            if (countUp && ++current >= upper)
                countUp = false;
            else if (!countUp && --current <= lower)
                countUp = true;

            return current == lower ? null : new Trianglify(current, current);
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

    public Trianglify(int triangleWidth, int triangleHeight) {
        this.triangleWidth = triangleWidth;
        this.triangleHeight = triangleHeight;
    }

    @Override
    public Integer apply(ImageData imageData) {
        int modX = imageData.getX() % triangleWidth;
        int modY = imageData.getY() % triangleHeight;

        if (pointInTriangle(modX, modY, 0, triangleHeight, triangleWidth / 2, 0, triangleWidth, triangleHeight) && !(imageData.getX() - modX + triangleWidth / 2 >= imageData.getBufferedImage().getWidth() || imageData.getY() - modY + triangleHeight / 2 >= imageData.getBufferedImage().getHeight()))
            return new RGB(imageData.getBufferedImage().getRGB(imageData.getX() - modX + triangleWidth / 2, imageData.getY() - modY + triangleHeight / 2)).getValue();
        else
            return imageData.getRgb().getValue();
    }

    private boolean pointInTriangle(int pointX, int pointY, int triangleX1, int triangleY1, int triangleX2, int triangleY2, int triangleX3, int triangleY3) {
        double A = .5 * (-triangleY2 * triangleX3 + triangleY1 * (-triangleX2 + triangleX3) + triangleX1 * (triangleY2 - triangleY3) + triangleX2 * triangleY3);
        int sign = A < 0 ? -1 : 1;
        double s = (triangleY1 * triangleX3 - triangleX1 * triangleY3 + (triangleY3 - triangleY1) * pointX + (triangleX1 - triangleX3) * pointY) * sign;
        double t = (triangleX1 * triangleY2 - triangleY1 * triangleX2 + (triangleY1 - triangleY2) * pointX + (triangleX2 - triangleX1) * pointY) * sign;

        return s > 0 && t > 0 && (s + t) < 2 * A * sign;
    }
}
