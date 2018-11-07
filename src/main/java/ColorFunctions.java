public class ColorFunctions {

    public static int getDistance(RGB rgb1, RGB rgb2) {
        return (rgb1.getRed() - rgb2.getRed()) * (rgb1.getRed() - rgb2.getRed()) + (rgb1.getGreen() - rgb2.getGreen()) * (rgb1.getGreen() - rgb2.getGreen()) + (rgb1.getBlue() - rgb2.getBlue()) * (rgb1.getBlue() - rgb2.getBlue());
    }

    public static boolean pointInCircle(int pointX, int pointY, int circleRadius) {
        return getDistanceBetweenPoints(pointX, pointY, circleRadius, circleRadius) < circleRadius;
    }

    public static double getDistanceBetweenPoints(int point1X, int point1Y, int point2X, int point2Y) {
        return Math.sqrt(Math.pow(point2X - point1X, 2) + Math.pow(point2Y - point1Y, 2));
    }
}
