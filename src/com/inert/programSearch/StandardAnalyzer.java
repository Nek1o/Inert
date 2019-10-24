package com.inert.programSearch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StandardAnalyzer {
    private static final String STANDARD_DIR_PATH = "resources\\standardDirs";
    private static final String STANDARD_EXE_PATH = "resources\\standardExes";
    private List<String> dirs;
    private List<String> exes;
    private static StandardAnalyzer standardDirAnalyzer;

    private StandardAnalyzer() throws IOException {
        dirs = Files.readAllLines(Paths.get(STANDARD_DIR_PATH), StandardCharsets.UTF_8);
        exes = Files.readAllLines(Paths.get(STANDARD_EXE_PATH), StandardCharsets.UTF_8);
    }

    public static StandardAnalyzer getInstance() throws IOException {
        if (standardDirAnalyzer == null) {
            standardDirAnalyzer = new StandardAnalyzer();
        }
        return standardDirAnalyzer;
    }

    public boolean isStandardDir(String dirName) {
        return isStandard(dirs,dirName);
    }
    public boolean isStandardExe(String fileName) {
        return isStandard(exes,fileName);
    }
    private boolean isStandard(List<String> standardList, String name){
        for (String standard : standardList) {
            if (name.contains(standard)) {
                return false;
            }
        }
        return true;
    }
}
