import java.util.function.Function;

public class ProcessingFunction4 implements Function<ImageData, Integer> {

    private RGB[] colors = new RGB[]{
            new RGB(26, 188, 156), //Turquoise
            new RGB(46, 204, 113), //Emerald
            new RGB(52, 152, 219), //Peter River
            new RGB(155, 89, 182), //Amethyst
            new RGB(52, 73, 94), //Wet Asphalt
            new RGB(22, 160, 133), //Green Sea
            new RGB(39, 174, 96), //Nephritis
            new RGB(41, 128, 185), //Belize Hole
            new RGB(142, 68, 173), //Wisteria
            new RGB(44, 62, 80), //Midnight.getBlue()
            new RGB(241, 196, 15), //Sun Flower
            new RGB(230, 126, 34), //Carrot
            new RGB(231, 76, 60), //Alizarin
            new RGB(236, 240, 241), //Clouds
            new RGB(149, 165, 166), //Concrete
            new RGB(243, 156, 18), //Orange
            new RGB(211, 84, 0), //Pumpkin
            new RGB(192, 57, 43), //Pomegranate
            new RGB(189, 195, 199), //Silver,
            new RGB(127, 140, 141)}; //Asbestos

    @Override
    public Integer apply(ImageData imageData) {
        return null;
    }
}
