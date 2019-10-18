package help;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    private Path root;

    public void scriptStart() {
        // Стартуем скрипт
    }
    public static List<String> loadUnrefinedNames() {
        File file = new File("programas-instalados.txt");
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
