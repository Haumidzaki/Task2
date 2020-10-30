import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileHandlerTest {

    @Test
    public void deleteTest(){
        Path path = Paths.get("src/test/watch_test/test.txt");
        try {

            Assert.assertTrue(Files.notExists(path));
            Files.createFile(Paths.get("src/test/watch_test/test.txt"));
            Assert.assertTrue(Files.exists(path));

            new DeleteFileHandler(path.toFile()).start();
            Thread.sleep(1000);
            Assert.assertTrue(Files.notExists(path));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
