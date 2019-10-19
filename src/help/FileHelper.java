package help;

import processes.ProcessStarter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static void scriptStart() {
        // Стартуем скрипт
        ProcessStarter processStarter = ProcessStarter.getInstance();
        // Это работает, остается переопределить поток вывода
        Process p = processStarter.start("powershell -ExecutionPolicy UnRestricted -File .\\Search.ps1",
                false);

    }
    public static List<String> loadUnrefinedNames() throws InterruptedException {
        // Необходимо подождать, пока скрипт закончит своё выполнение и файл создастся
        // Иначе смерть и NullPointerException


        File file = new File("programas-instalados.txt");
        if (!file.exists()) {
            scriptStart();
            Thread.sleep(10000);
        }
        ArrayList<String> fileContents = new ArrayList<String>();
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
}
