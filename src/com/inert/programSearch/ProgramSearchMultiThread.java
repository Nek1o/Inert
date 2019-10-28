package com.inert.programSearch;

import com.inert.help.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.lang3.StringUtils;

public class ProgramSearchMultiThread implements ProgramSearch {
    private List<Path> roots;
    Map<String, List<FoundPath>> undecidedPathsMap;

    private List<String> programNamesFromRegistry;

    private Path programFilesX86;
    private Path programFiles;

    public ProgramSearchMultiThread() {
        roots = new ArrayList<>();
        undecidedPathsMap = new ConcurrentHashMap<>();
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
                if (!StandardAnalyzer.getInstance().isStandardDir(fileContents.get(i))) {
                    programNamesFromRegistry.add(fileContents.get(i).toLowerCase());
                }
            }
        }
        programNamesFromRegistry.removeIf(String::isEmpty);
    }


    // Укороченный вызов для внешних объектов
    public Map<String, Program> getPaths() throws IOException, InterruptedException {
        refineNames(FileHelper.loadUnrefinedNames());

        long start = System.currentTimeMillis();
        Thread task1 = new WalkRunner(programFiles);
        Thread task2 = new WalkRunner(programFilesX86);
        //Thread task3 = new WalkRunner(Paths.get("C:\\Program Files\\WinRAR"));
        task1.start();
        task2.start();
//        task3.start();
        task1.join();
        task2.join();
//        task3.join();
        long end = System.currentTimeMillis();
        System.out.println("Time" + (end - start) / 1000);


        return makeDecision();
    }


    private Map<String, Program> makeDecision() {
        Map<String, Program> resultMap = new HashMap<>();


        //отсекаем те для кого BOTH
        for (Map.Entry<String, List<FoundPath>> pair : undecidedPathsMap.entrySet()) {
            String pgName = pair.getKey();
            List<FoundPath> foundPathList = pair.getValue();
            int countBoth = 0;
            for (FoundPath y : foundPathList) {
                if (y == null) {
                    System.err.println("Unexpected null for " + pgName);
                    continue;
                }
                if (y.getTargetMarker() == TargetMarker.BOTH) {
                    countBoth++;
                }
            }
            for (FoundPath y : foundPathList) {
                if (y == null) {
                    System.err.println("Unexpected null for " + pgName);
                    continue;
                }
                if (y.getTargetMarker() == TargetMarker.BOTH) {
                    Program programToAdd;
                    if(countBoth == 1) {
                        resultMap.put(pgName,new Program(pgName,y.getPotentialPath(),Precision.CORRECT));
                    }
                    if(countBoth > 1) {
                        resultMap.put(pgName,new Program(pgName,y.getPotentialPath(),Precision.SIMILAR));
                    }

                }
            }
        }


        return resultMap;
    }

    class WalkRunner extends Thread {
        Path basicPath;

        WalkRunner(Path basicPath) {
            super();
            this.basicPath = basicPath;
        }

        @Override
        public void run() {
            FileVisitor<Path> visitor = new SearchingFileVisitor(programNamesFromRegistry, undecidedPathsMap);
            try {
                Files.walkFileTree(basicPath, Collections.singleton(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, visitor);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
