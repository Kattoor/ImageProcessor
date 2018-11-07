import java.util.function.Function;

public interface Transition<T> {
    Function<ImageData, Integer> get();
    int getCurrent();
    int getUpper();
    int getLower();
}
