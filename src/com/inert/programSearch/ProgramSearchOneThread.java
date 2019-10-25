package com.inert.programSearch;

import com.inert.help.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProgramSearchOneThread implements ProgramSearch{
    // TODO Сделать параллельный вызов для обоих program files, желательно с параллельным удалением найденных файлов из массива
    private List<Path> roots;
    private Map<String, Program> resultMap;
    private List<String> programNamesFromRegistry;

    private Path programFilesX86;
    private Path programFiles;

    public ProgramSearchOneThread() {
        roots = new ArrayList<>();
        resultMap = new ConcurrentHashMap<>();
        programNamesFromRegistry = Collections.synchronizedList(new ArrayList<>());
        File[] rootFiles = File.listRoots();
        for (File file : rootFiles) {
            roots.add(file.toPath());
        }
        programFilesX86 = Paths.get(roots.get(0) + File.separator + "Program Files (x86)");
        programFiles = Paths.get(roots.get(0) + File.separator + "Program Files");
    }

    /**
     * Делает из списка длинных имён с пробелами из реестра лист листов
     * с этими имена для более удобной обрабротки в методе поиска
     *
     * @param fileContents
     * @return <tt>List<tt/> <tt>List<tt/> типа <tt>String<tt/>
     * @throws NullPointerException
     */
    private void refineNames(List<String> fileContents) throws NullPointerException {
        // Если список с именами был пустой
        if (fileContents.isEmpty()) {
            throw new IllegalArgumentException();
        } else {

            for (int i = 0; i < fileContents.size(); i++) {
                // Проверки на системные программы
                if (!(fileContents.get(i).contains("Windows") || fileContents.get(i).contains("Microsoft") || fileContents.get(i).contains("Update") || fileContents.get(i).contains("Universal") || fileContents.get(i).contains("vs_") || fileContents.get(i).contains("WinRT") || fileContents.get(i).contains("Intel(R)") || fileContents.get(i).contains("VS") || fileContents.get(i).contains("NVIDIA"))) {
                    programNamesFromRegistry.add(fileContents.get(i).toLowerCase());
                }
            }
            programNamesFromRegistry.removeIf(String::isEmpty);
        }
    }


    // Укороченный вызов для внешних объектов
    public Map<String, Program> getPaths() throws IOException, InterruptedException {
        refineNames(FileHelper.loadUnrefinedNames());
        FileVisitor<Path> visitor = new SearchingFileVisitor(programNamesFromRegistry, resultMap);
        Files.walkFileTree(programFiles, Collections.singleton(FileVisitOption.FOLLOW_LINKS),
                Integer.MAX_VALUE, visitor);
        Files.walkFileTree(programFilesX86, Collections.singleton(FileVisitOption.FOLLOW_LINKS),
                Integer.MAX_VALUE, visitor);

        return resultMap;
    }


}
