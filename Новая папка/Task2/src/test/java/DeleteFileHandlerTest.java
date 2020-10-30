import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class DeleteFileHandlerTest {

    @Test
    public void deleteTest(){
        Path path = Paths.get("src/test/watch_test/test.txt");
        Path pathToLog = Paths.get("src/test/log/appTest.log");
        try {

            Files.deleteIfExists(path);
            Assert.assertTrue(Files.notExists(path));
            Files.createFile(path);
            Assert.assertTrue(Files.exists(path));
            new DeleteFileHandler(path.toFile()).start();
            Thread.sleep(1000);
            Assert.assertTrue(Files.notExists(path));

         //   Files.deleteIfExists(pathToLog);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
