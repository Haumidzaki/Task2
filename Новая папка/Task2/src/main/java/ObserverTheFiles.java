import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import util.Extension;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ObserverTheFiles {
    private static final Logger log = Logger.getLogger(ObserverTheFiles.class);
    private final Path path;

    public ObserverTheFiles(Path path) {
        this.path = path;
    }

    public void init() {

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            while ((key = watchService.take()) != null) {

                for (WatchEvent<?> event : key.pollEvents()) {

                    File file = new File(path + "/" + event.context());
                    String extension = Extension.getFileExtension(file);
                    BasicFileAttributes attributesFile = Files.readAttributes(file.toPath(), BasicFileAttributes.class);


                    assert attributesFile != null;
                    String message = String.format("имя файла: %s расширение файла: %s время создания файла: %s"
                            , file.getName()
                            , extension
                            , attributesFile.creationTime()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                    log.log(Level.INFO, message);

                    switch (extension) {
                        case "xml":
                        case "json":
                            new ReadFileHandler(file).start();
                            break;
                        default:
                            new DeleteFileHandler(file).start();
                            break;
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
