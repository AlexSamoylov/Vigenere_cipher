package org.dnu.samoylov.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {
    public static void writeToFile(String path, String data) throws IOException {
        File file = new File(getRelativePath(path));
        file.createNewFile();
        PrintWriter out = new PrintWriter(file);

        out.write(String.valueOf(data));
        out.close();
    }


    public static String getRelativePath(String name) throws IOException {
        return  System.getProperty("user.dir") + "\\data\\" + name;
    }
    public static String getFileText(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getRelativePath(fileName))));
    }
}
