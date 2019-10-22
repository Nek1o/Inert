package search;

import help.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProgramSearch {
    private  List<String> Paths;
    private Path root;


    public ProgramSearch() {
        Paths = new ArrayList<>();
        root = java.nio.file.Paths.get("C:" + File.separator);
    }

    private List<List<String>> refineNames(List<String> fileContents) throws NullPointerException {
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
    private List<String> getProgramPaths(List<String> fileNames, Path dir) {
        try {
            List<List<String>> names = refineNames(FileHelper.loadUnrefinedNames());
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {

                for (Path path : stream) {
                    if (path.toFile().isDirectory()) {
                        getProgramPaths(fileNames, path);
                    } else {
                        if (path.toAbsolutePath().toString().contains(".exe")) {
                            String nameWithoutExe = path.getFileName().toString();
                            nameWithoutExe = nameWithoutExe.replace(".exe", "");
                            for (List<String> name : names) {
                                if (name.contains(nameWithoutExe)) {
                                    fileNames.add(path.toAbsolutePath().toString());
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //return fileNames;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fileNames;
    }
    public List<String> getPaths() {
        return getProgramPaths(Paths, root);
    }
}
