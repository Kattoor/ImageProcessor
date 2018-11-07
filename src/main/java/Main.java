import org.w3c.dom.Node;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main extends JFrame {

    public static void main(String[] args) throws IOException, URISyntaxException {
        new Main();
    }

    private int width;
    private int height;

    private void configureMetaData(Node root, IIOMetadata meta, int imageIndex) throws IIOInvalidTreeException {
        String metaFormat = meta.getNativeMetadataFormatName();
        Node asTree = meta.getAsTree(metaFormat);
        Node child = root.getFirstChild();
        while (child != null) {
            if ("GraphicControlExtension".equals(child.getNodeName())) {
                break;
            }
            child = child.getNextSibling();
        }
        IIOMetadataNode gce = (IIOMetadataNode) child;
        gce.setAttribute("userDelay", "FALSE");
        gce.setAttribute("delayTime", "12");
        gce.setAttribute("disposalMethod", "none");

        if (imageIndex == 0) {
            IIOMetadataNode aes = new IIOMetadataNode("ApplicationExtensions");
            IIOMetadataNode ae = new IIOMetadataNode("ApplicationExtension");
            ae.setAttribute("applicationID", "NETSCAPE");
            ae.setAttribute("authenticationCode", "2.0");
            byte[] uo = new byte[]{0x1, 0x0, 0x0};
            ae.setUserObject(uo);
            aes.appendChild(ae);
            root.appendChild(aes);
        }
        meta.setFromTree(metaFormat, root);
    }


    private Main() throws IOException, URISyntaxException {
        super("ImageProcessor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

/*        BufferedImage img = ImageIO.read(new File("./yugioh.gif"));
        int scale = img.getWidth() / 500;
        height = img.getHeight() / scale;
        width = img.getWidth() / scale;
        Image scaledInstance = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(scaledInstance, 0, 0, null);
        g.dispose();*/


        Icon icon = new ImageIcon("C:\\Users\\Administrator\\yugioh2.gif");
        JLabel jLabel = create();
        jLabel.setIcon(icon);
        add(jLabel);

        ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
        ImageInputStream inputStream = ImageIO.createImageInputStream(new File("C:\\Users\\Administrator\\yugioh2.gif"));
        reader.setInput(inputStream);
        int count = reader.getNumImages(true);
        List<BufferedImage> imgs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            BufferedImage img = reader.read(i);
            width = img.getWidth();
            height = img.getHeight();
            imgs.add(process(img, new Trianglify(5,5)));
        }
//        child.setUserObject(new byte[]{ 0x1, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF)});


        ImageWriter writer = ImageIO.getImageWritersByFormatName("GIF").next();
        File file = new File("C:\\Users\\Administrator\\yugioh-circlified.gif");
        ImageOutputStream outputStream = ImageIO.createImageOutputStream(file);
        writer.setOutput(outputStream);
        writer.prepareWriteSequence(null);
        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(imgs.get(0)), writer.getDefaultWriteParam());
        Node root = metadata.getAsTree(metadata.getNativeMetadataFormatName());
        for (int i = 0; i < count; i++) {
            configureMetaData(root, metadata, i);
            writer.writeToSequence(new IIOImage(imgs.get(i), null, metadata), null);
        }
        writer.endWriteSequence();
        writer.reset();
        writer.dispose();
        outputStream.flush();
        outputStream.close();

        Icon icon2 = new ImageIcon("C:\\Users\\Administrator\\yugioh-circlified.gif");
        JLabel jLabel2 = create();
        jLabel2.setIcon(icon2);
        add(jLabel2);

        //BufferedImage image = new BufferedImage(jLabel.getWidth(), jLabel.getHeight(), BufferedImage.TYPE_INT_RGB);

        /*WritableRaster raster = image.getRaster();
        for (int i = 0; i < jLabel.getWidth(); i++) {
            for (int j = 0; j < jLabel.getHorizontalAlignment(); j++) {
                int[] colorArray = getColorForPixel(pixels[i][j]);
                raster.setPixel(i, j, colorArray);
            }
        }*/

        //ImageIO.write(image, "gif", new File("CardImage"));


        pack();
        setVisible(true);
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
