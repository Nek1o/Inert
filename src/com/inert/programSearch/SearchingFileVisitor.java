package com.inert.programSearch;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchingFileVisitor extends SimpleFileVisitor<Path> {
    List<String> programNames;
    Map<String, List<FoundPath>> undecidedPathsMap;

    public SearchingFileVisitor(List<String> programNames, Map<String, List<FoundPath>> undecidedPathsMap) {
        this.programNames = programNames;
        this.undecidedPathsMap = undecidedPathsMap;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        if (!file.getFileName().toString().endsWith(".exe") || StandardAnalyzer.getInstance().isStandardExe(file.getFileName().toString())) {
            return FileVisitResult.CONTINUE;
        }
        String fileNameWithoutExe = file.getFileName().toString().replace(".exe", "");
        for (String programName : programNames) {
            TargetMarker targetMarker = PathAnalyzer.analyse(programName, file.toAbsolutePath().toString());
            if (targetMarker != TargetMarker.NONE) {
                undecidedPathsMap.putIfAbsent(programName, new ArrayList<FoundPath>());

                undecidedPathsMap.get(programName).add(new FoundPath(file.toAbsolutePath().toString(), targetMarker));
            }
        }
        return FileVisitResult.CONTINUE;
    }


    @Override
    public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
        //failed.add(path.toString());
        return FileVisitResult.SKIP_SUBTREE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) throws IOException {

        if(StandardAnalyzer.getInstance().isStandardDir(file.getFileName().toString())){
            return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }
}