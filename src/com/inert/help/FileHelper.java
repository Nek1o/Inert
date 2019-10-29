package com.inert.help;

import com.inert.processes.ProcessStarter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {


    public static List<String> loadUnrefinedNames() throws InterruptedException {
        // Необходимо подождать, пока скрипт закончит своё выполнение и файл создастся
        // Иначе смерть и NullPointerException
        // Сделано ожидание в scriptStart

        File file = new File("resources" + File.separator + "programas-instalados.txt");
        if (!file.exists()) {
            ProcessStarter.scriptStart();
        }

        ArrayList<String> fileContents = new ArrayList<>();
        try {
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(file), StandardCharsets.UTF_16LE));
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.add(line);
            }
            reader.close();
            // Если ничего не было записано
            if (fileContents.isEmpty()) {
                return null;
            }
            else {
                return fileContents;
            }
        } catch (IOException e) {
            return null;
        }
    }
    public static void savePaths(List<String> paths) {
        try {
            BufferedWriter writer =
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(new File("SavedPaths.txt")),
                                    StandardCharsets.UTF_16LE));
            for (String path:
                 paths) {
                writer.write(path + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
