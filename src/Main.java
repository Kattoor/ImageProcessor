import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class Main extends JFrame {

    public static void main(String[] args) throws IOException {
        new Main();
    }

    private int width = 400;
    private int height = 600;

    private Main() throws IOException {
        super("ImageProcessor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        BufferedImage img = ImageIO.read(new File("img3.jpg"));
        Image scaledInstance = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(scaledInstance, 0, 0, null);
        g.dispose();

        add(create(scaledImage));
        add(create(process(scaledImage, data -> {
            int triangleWidth = 10;
            int triangleHeight = 10;
            int modX = data.getX() % triangleWidth;
            int modY = data.getY() % triangleHeight;

            if (pointInTriangle(modX, modY, 0, triangleHeight, triangleWidth / 2, 0, triangleWidth, triangleHeight) && !(data.getX() - modX + triangleWidth / 2 >= data.getBufferedImage().getWidth() || data.getY() - modY + triangleHeight / 2 >= data.getBufferedImage().getHeight()))
                return new RGB(data.getBufferedImage().getRGB(data.getX() - modX + triangleWidth / 2, data.getY() - modY + triangleHeight / 2)).getValue();
            else
                return new RGB(255, 255, 255).getValue();
        })));


        JLabel jLabel = create(process(scaledImage, new Circlify(15)));

        add(jLabel);

        new Thread(() -> {
            try {
                int i = 1;
                boolean countUp = true;
                while (true) {
                    if (countUp && ++i >= 15) {
                        countUp = false;
                        Thread.sleep(100);
                    } else if (!countUp && --i <= 2) {
                        countUp = true;
                        Thread.sleep(100);
                    }

                    Image image = process(scaledImage, new Circlify(i)).getScaledInstance(jLabel.getWidth(), jLabel.getHeight(), Image.SCALE_SMOOTH);

                    ImageIcon imageIcon = new ImageIcon(image);
                    jLabel.setIcon(imageIcon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        pack();
        setVisible(true);
    }

    private boolean pointInTriangle(int pointX, int pointY, int triangleX1, int triangleY1, int triangleX2, int triangleY2, int triangleX3, int triangleY3) {
        double A = .5 * (-triangleY2 * triangleX3 + triangleY1 * (-triangleX2 + triangleX3) + triangleX1 * (triangleY2 - triangleY3) + triangleX2 * triangleY3);
        int sign = A < 0 ? -1 : 1;
        double s = (triangleY1 * triangleX3 - triangleX1 * triangleY3 + (triangleY3 - triangleY1) * pointX + (triangleX1 - triangleX3) * pointY) * sign;
        double t = (triangleX1 * triangleY2 - triangleY1 * triangleX2 + (triangleY1 - triangleY2) * pointX + (triangleX2 - triangleX1) * pointY) * sign;

        return s > 0 && t > 0 && (s + t) < 2 * A * sign;
    }



    private JLabel create(BufferedImage bufferedImage) {
        JLabel jLabel = new JLabel();
        jLabel.setSize(width, height);
        jLabel.setMinimumSize(new Dimension(width, height));
        jLabel.setMaximumSize(new Dimension(width, height));
        Image imageRight = bufferedImage.getScaledInstance(jLabel.getWidth(), jLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIconRight = new ImageIcon(imageRight);
        jLabel.setIcon(imageIconRight);
        return jLabel;
    }

    private BufferedImage process(BufferedImage bufferedImage, Function<ImageData, Integer> function) {
        bufferedImage = cloneBufferedImage(bufferedImage);
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++)
                bufferedImage.setRGB(x, y, function.apply(new ImageData(x, y, new RGB(bufferedImage.getRGB(x, y)), bufferedImage)));
        }
        return bufferedImage;
    }

    private BufferedImage cloneBufferedImage(BufferedImage bufferedImage) {
        ColorModel colorModel = bufferedImage.getColorModel();
        WritableRaster writableRaster = bufferedImage.copyData(null);
        return new BufferedImage(colorModel, writableRaster, colorModel.isAlphaPremultiplied(), null);
    }
}






/*/*add(create(process(img, data ->
                Arrays.stream(colors)
                        .getRed()uce(new RGB(1000, 1000, 1000), (acc, color) -> {

                            int low = Integer.min(Integer.min(data.rgb.getRed(), data.rgb.getGreen()), data.rgb.getBlue());
                            int high = Integer.max(Integer.max(data.rgb.getRed(), data.rgb.getGreen()), data.rgb.getBlue());
                            int mid = data.rgb.getRed() != low && data.rgb.getRed() != high ? data.rgb.getRed() :
                                    data.rgb.getGreen() != low && data.rgb.getGreen() != high ? data.rgb.getGreen() : data.rgb.getBlue();

                            if (data.rgb.getRed() == low)
                                data.rgb.getRed() = low - (mid - low);
                            else if (data.rgb.getRed() == high)
                                data.rgb.getRed() = high + (high - mid);

                            if (data.rgb.getGreen() == low)
                                data.rgb.getGreen() = low - (mid - low);
                            else if (data.rgb.getGreen() == high)
                                data.rgb.getGreen() = high + (high - mid);

                            if (data.rgb.getBlue() == low)
                                data.rgb.getBlue() = low - (mid - low);
                            else if (data.rgb.getBlue() == high)
                                data.rgb.getBlue() = high + (high - mid);

                            int newColorDistance = ColorFunctions.getDistance(color, data.rgb);
                            int oldColorDistance = ColorFunctions.getDistance(acc, data.rgb);
                            if (data.x == 324 && data.y == 1218) {
                                *//*System.out.println(color.getRed() + "," + color.getGreen() + "," + color.getBlue());
                                System.out.println(data.rgb.getRed() + "," + data.rgb.getGreen() + "," + data.rgb.getBlue());*//*
                                System.out.printf("#%02x%02x%02x\n", color.getRed(), color.getGreen(), color.getBlue());
                                System.out.printf("#%02x%02x%02x\n", data.rgb.getRed(), data.rgb.getGreen(), data.rgb.getBlue());
                                System.out.println(newColorDistance);
                                System.out.println(oldColorDistance);
                                System.out.println();
                            }
                            return newColorDistance < oldColorDistance ? color : acc;
                        }).getValue())));*/
       /* add(create(process(img, data ->
                new RGB(data.rgb.getRed() > data.rgb.getGreen() && data.rgb.getRed() > data.rgb.getBlue() ? 255 : data.rgb.getRed(),
                        data.rgb.getGreen() > data.rgb.getRed() && data.rgb.getGreen() > data.rgb.getBlue() ? 255 : data.rgb.getGreen(),
                        data.rgb.getBlue() > data.rgb.getRed() && data.rgb.getBlue() > data.rgb.getGreen() ? 255 : data.rgb.getBlue()).getValue()
        )));*/
        /*add(create(process(img, data -> {
            int low = Integer.min(Integer.min(data.rgb.getRed(), data.rgb.getGreen()), data.rgb.getBlue());
            int high = Integer.max(Integer.max(data.rgb.getRed(), data.rgb.getGreen()), data.rgb.getBlue());
            int mid = data.rgb.getRed() != low && data.rgb.getRed() != high ? data.rgb.getRed() :
                    data.rgb.getGreen() != low && data.rgb.getGreen() != high ? data.rgb.getGreen() : data.rgb.getBlue();

            if (data.rgb.getRed() == low)
                data.rgb.getRed() = low - (mid - low);
            else if (data.rgb.getRed() == high)
                data.rgb.getRed() = high + (high - mid);

            if (data.rgb.getGreen() == low)
                data.rgb.getGreen() = low - (mid - low);
            else if (data.rgb.getGreen() == high)
                data.rgb.getGreen() = high + (high - mid);

            if (data.rgb.getBlue() == low)
                data.rgb.getBlue() = low - (mid - low);
            else if (data.rgb.getBlue() == high)
                data.rgb.getBlue() = high + (high - mid);

            return new RGB(data.rgb.getRed(), data.rgb.getGreen(), data.rgb.getBlue()).getValue();
        })));*/