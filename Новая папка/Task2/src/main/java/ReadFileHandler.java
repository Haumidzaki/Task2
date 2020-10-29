import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadFileHandler extends Thread {
    private static final Logger log = Logger.getLogger(ReadFileHandler.class);
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final File file;


    public ReadFileHandler(File file) {
        this.file = file;
    }

    @Override
    public void run() {

        long start = System.nanoTime();
        long count = 0;

        try {
            count = Files.lines(file.toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.nanoTime();

        String dateStart = dateFormat.format(new Date());
        log.log(Level.INFO, "дата: " + dateStart + " время выполнения: "
                + (end - start) + " нс" + " количество строк: " + count);
    }
}
