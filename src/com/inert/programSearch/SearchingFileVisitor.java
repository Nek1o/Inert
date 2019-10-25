package com.inert.programSearch;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;

public class SearchingFileVisitor extends SimpleFileVisitor<Path> {
    List<String>  programNames;
    Map<String,Program> resultProgramMap;

    public SearchingFileVisitor(List<String> programNames, Map<String, Program> resultProgramMap) {
        this.programNames = programNames;
        this.resultProgramMap = resultProgramMap;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        if (!file.getFileName().toString().endsWith(".exe") ||
                !StandardAnalyzer.getInstance().isStandardExe(file.getFileName().toString()) ) {
            return FileVisitResult.CONTINUE;
        }
        String fileNameWithoutExe = file.getFileName().toString().replace(".exe","");
        for(String programName : programNames){
            if (programName.contains(fileNameWithoutExe)) {
                resultProgramMap.put(programName,new Program(programName, file.toAbsolutePath().toString()));
                programNames.remove(programName);
                break;
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

//        if(StandardAnalyzer.getInstance().isStandard(file.getFileName().toString())){
//            return FileVisitResult.SKIP_SUBTREE;
//        }
        return FileVisitResult.CONTINUE;
    }
}