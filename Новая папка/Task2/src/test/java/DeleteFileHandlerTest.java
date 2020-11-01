import org.junit.Assert;
import org.junit.Test;
import util.Extension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

            String extension = Extension.getFileExtension(path.toFile());
            BasicFileAttributes attributesFile = Files.readAttributes(path, BasicFileAttributes.class);

            assert attributesFile != null;
            String message = String.format("имя файла: %s расширение файла: %s время создания файла: %s"
                    , path.getFileName()
                    , extension
                    , attributesFile.creationTime()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            new DeleteFileHandler(path.toFile(), message).start();
            Thread.sleep(1000);
            Assert.assertTrue(Files.notExists(path));

         //   Files.deleteIfExists(pathToLog);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
