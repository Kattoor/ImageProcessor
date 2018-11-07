import java.awt.image.BufferedImage;

public class ImageData {
    private int x;
    private int y;
    private RGB rgb;
    private BufferedImage bufferedImage;

    ImageData(int x, int y, RGB rgb, BufferedImage bufferedImage) {
        this.x = x;
        this.y = y;
        this.rgb = rgb;
        this.bufferedImage = bufferedImage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public RGB getRgb() {
        return rgb;
    }

    public void setRgb(RGB rgb) {
        this.rgb = rgb;
    }

    public BufferedImage getBufferedImage() {
         return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
