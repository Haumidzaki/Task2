package util;

import java.io.File;

public class Extension {

    private Extension() {
    }

    public static String getFileExtension(File file) {

        if (file.isFile()) {
            String[] sp = file.getName().split("\\.");
            return sp[sp.length - 1];
        }
        return "";
    }
}
