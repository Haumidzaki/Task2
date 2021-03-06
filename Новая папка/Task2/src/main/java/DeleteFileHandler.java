import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import util.DateFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

public class DeleteFileHandler extends Thread {
    private static final Logger log = Logger.getLogger(DeleteFileHandler.class);
    private final File file;
    private String message;

    public DeleteFileHandler(File file, String message) {
        this.file = file;
        this.message = message;
    }

    @Override
    public void run() {
        long start = System.nanoTime();

        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.nanoTime();

        String dateStart = DateFormatter.dateFormatForHandler(new Date());
        log.log(Level.INFO, message);
        log.log(Level.INFO, "дата: " + dateStart + " время выполнения: " + (end - start) + " нс");
    }
}
