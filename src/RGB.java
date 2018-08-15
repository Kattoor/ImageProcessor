public class RGB {

    private int red;
    private int green;
    private int blue;
    private int value;

    RGB(int rgb) {
        red = (rgb >> 16) & 0xFF;
        green = (rgb >> 8) & 0xFF;
        blue = rgb & 0xFF;
        value = rgb;
    }

    RGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        value = red;
        value = (value << 8) + green;
        value = (value << 8) + blue;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}