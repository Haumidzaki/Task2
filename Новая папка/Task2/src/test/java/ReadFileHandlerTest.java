import org.junit.Assert;
import org.junit.Test;
import util.Extension;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ReadFileHandlerTest {

    @Test
    public void readTest() throws FileNotFoundException {
        Path path = Paths.get("src/test/watch_test/testReadFile.txt");
        Path pathToLog = Paths.get("src/test/log/appTest.log");
        String text = "Привет";

        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            FileWriter writer = new FileWriter(path.toFile());
            writer.write(text);
            writer.flush();
            writer.close();

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

            new ReadFileHandler(path.toFile(), message).start();
            Thread.sleep(1000);

            long count = Files.lines(path).count();
            List<String> listLines = Files.lines(pathToLog).collect(Collectors.toList());
            String lastLines = listLines.get(listLines.size() - 1);

            boolean chek = lastLines.endsWith(String.valueOf(count));

            Assert.assertTrue(chek);

            Files.deleteIfExists(path);
            Assert.assertTrue(Files.notExists(path));

         //   Files.deleteIfExists(pathToLog);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
