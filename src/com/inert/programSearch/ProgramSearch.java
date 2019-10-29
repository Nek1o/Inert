package com.inert.programSearch;

import com.inert.help.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProgramSearch {
    // TODO Класс program для хранения имени и пути
    // TODO Устанавливать соответствия пути и программы на этапе поиска
    // TODO Вместо List путей использовать Map
    // TODO Сделать параллельный вызов для обоих program files, желательно с параллельным удалением найденных файлов из массива
    private  List<String> paths;
    private List<Path> roots;

    private Path programFilesX86;
    private Path programFiles;

    public ProgramSearch() {
        paths = new ArrayList<>();
        roots = new ArrayList<>();
        File[] rootFiles = File.listRoots();
        for (File file:
             rootFiles) {
            roots.add(file.toPath());
        }
        programFilesX86 = Paths.get(roots.get(0) + File.separator + "Program Files (x86)");
        programFiles = Paths.get(roots.get(0) + File.separator + "Program Files");
    }

    /**
     * Делает из списка длинных имён с пробелами из реестра лист листов
     * с этими имена для более удобной обрабротки в методе поиска
     * @param fileContents
     * @return <tt>List<tt/> <tt>List<tt/> типа <tt>String<tt/>
     * @throws NullPointerException
     */
    private List<List<String>> refineNames(List<String> fileContents) throws NullPointerException {
        // Если список с именами был пустой
        if (fileContents.isEmpty()) {
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
                        || fileContents.get(i).contains("VS")
                        || fileContents.get(i).contains("NVIDIA") )) {
                    for (String name:
                            fileContents.get(i).split(" ")) {
                        refinedNames.get(i).add(name.toLowerCase());
                    }
                }
            }
            refinedNames.removeIf(List::isEmpty);
            return refinedNames;
        }
    }

    /**
     * Ищет пути к исполняемым файлам, используя названия файлов, полученных из реестра
     * @param fileNames
     * @param dir
     * @return
     */
    private List<String> getProgramPaths(List<String> fileNames, Path dir) {
        try {
            List<List<String>> names = refineNames(FileHelper.loadUnrefinedNames());
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {

                for (Path path : stream) {
                    // Если папка
                    if (path.toFile().isDirectory()) {
                        getProgramPaths(fileNames, path);
                    } else {
                        // Если файл
                        // Если исполняемый
                        if (path.toAbsolutePath().toString().contains(".exe")) {

                            String nameWithoutExe = path.getFileName().toString();
                            nameWithoutExe = nameWithoutExe.replace(".exe", "");

                            for (List<String> name : names) {

                                if (name.contains(nameWithoutExe.toLowerCase())) {
                                    fileNames.add(path.toAbsolutePath().toString());
                                    names.remove(name);
                                    break;
                                }
                            }
                            // Если имена кончились, закончим поиск
                            if (names.isEmpty()) {
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileNames;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fileNames;
    }
    //TODO Если содержаться два одинаковых имени, то приписать к ним верхнюю папку

    // Укороченный вызов для внешних объектов
    public List<String> getPaths() {
        List<String> pathsProgramFilesx86 = getProgramPaths(paths, programFilesX86);
        List<String> pathsProgramFiles = getProgramPaths(paths, programFiles);
        return paths;
    }
}
