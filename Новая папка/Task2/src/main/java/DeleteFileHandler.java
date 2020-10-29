import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeleteFileHandler extends Thread {
    private static final Logger log = Logger.getLogger(DeleteFileHandler.class);
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final File file;

    public DeleteFileHandler(File file) {
        this.file = file;
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

        String dateStart = dateFormat.format(new Date());
        log.log(Level.INFO, "дата: " + dateStart + " время выполнения " + (end - start) + " нс");
    }
}
