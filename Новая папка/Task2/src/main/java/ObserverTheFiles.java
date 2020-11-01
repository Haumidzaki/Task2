import util.Extension;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ObserverTheFiles {
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


                    switch (extension) {
                        case "xml":
                        case "json":
                            new ReadFileHandler(file, message).start();
                            break;
                        default:
                            new DeleteFileHandler(file, message).start();
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
