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

    private int width = 500;
    private int height = 500;

    private Main() throws IOException {
        super("ImageProcessor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        BufferedImage img = ImageIO.read(new File("wannes.jpg"));
        Image scaledInstance = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(scaledInstance, 0, 0, null);
        g.dispose();

        add(create(scaledImage));
        add(transition(scaledImage, new Trianglify.TrianglifyTransition(0, 20)));

        add(transition(scaledImage, new Circlify.CirclifyTransition(0, 13)));

        pack();
        setVisible(true);
    }

    private JLabel transition(BufferedImage originalImage, Transition transition) {
        JLabel jLabel = create();
        System.out.println(jLabel.getHeight());
        System.out.println(jLabel.getWidth());
        new Thread(() -> {
            try {
                while (true) {
                    Image image = process(originalImage, transition.get()).getScaledInstance(jLabel.getWidth(), jLabel.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(image);
                    jLabel.setIcon(imageIcon);

                    if (transition.getCurrent() >= transition.getUpper())
                        Thread.sleep(250);
                    else if (transition.getCurrent() <= transition.getLower())
                        Thread.sleep(250);
                    else
                        Thread.sleep(20);
                    pack();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return jLabel;
    }

    private JLabel create() {
        JLabel jLabel = new JLabel();
        jLabel.setSize(width, height);
        jLabel.setMinimumSize(new Dimension(width, height));
        jLabel.setMaximumSize(new Dimension(width, height));
        jLabel.setIcon(new ImageIcon());
        return jLabel;
    }

    private JLabel create(BufferedImage bufferedImage) {
        JLabel jLabel = create();
        ImageIcon imageIcon = new ImageIcon(bufferedImage.getScaledInstance(jLabel.getWidth(), jLabel.getHeight(), Image.SCALE_SMOOTH));
        jLabel.setIcon(imageIcon);
        return jLabel;
    }

    private BufferedImage process(BufferedImage bufferedImage, Function<ImageData, Integer> function) {
        if (function == null)
            return bufferedImage;
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