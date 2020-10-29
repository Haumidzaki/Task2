import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        new ObserverTheFiles(Paths.get("src/main/watch")).init();
    }
}
