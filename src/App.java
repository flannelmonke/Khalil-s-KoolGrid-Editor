import frameManipulation.frame_maker;
import frameManipulation.frame_manager;

public class App {
    public static void main(String[] args) {
        frame_manager manager = new frame_manager(new frame_maker(null));
    }
}   