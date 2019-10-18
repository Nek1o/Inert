package help;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {


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
    // Возможно стоит поместить в search
    public static List<List<String>> refineNames(List<String> fileContents) throws NullPointerException {

        // Если список с именами был пустой
        if (fileContents == null) {
            throw new NullPointerException();
        }
        else {
            // Лист листов. В нем хранятся листы, состоящие из слов названия программы из реестра
            List<List<String>> refinedNames = new ArrayList<>();
            for (int i = 0; i < fileContents.size(); i++) {
                refinedNames.add(new ArrayList<>());
            }
            for (int i = 0; i < fileContents.size(); i++) {
                // Проверки на системные программы
                if (!( fileContents.get(i).contains("Windows")
                || fileContents.get(i).contains("Microsoft")
                || fileContents.get(i).contains("Update")
                || fileContents.get(i).contains("Universal")
                || fileContents.get(i).contains("vs_")
                || fileContents.get(i).contains("WinRT")
                || fileContents.get(i).contains("Intel(R)")
                || fileContents.get(i).contains("VS"))) {
                    for (String name:
                            fileContents.get(i).split(" ")) {
                        refinedNames.get(i).add(name);
                    }
                }
            }
            refinedNames.removeIf(List::isEmpty);
            return refinedNames;
        }
    }
}
