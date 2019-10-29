package com.inert.programSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathAnalyzer {

    public static TargetMarker analyse(String programName, String fullPath) {
        try {
            fullPath = fullPath.substring(3); //обрезаем название диска
        } catch (Exception e) {
            return TargetMarker.NONE;
        }

        String separator = File.separator + File.separator;
        String[] splitedArray = fullPath.split(separator);
        List<String> dirs = new ArrayList<>(Arrays.asList(splitedArray));
        String onlyFileName = dirs.get(dirs.size() - 1);
        onlyFileName = onlyFileName.replace(".exe", "");
        dirs.remove(dirs.size() - 1);

        boolean containsFileName = false;
        boolean containsAnyDir = false;
        if (programName.toLowerCase().contains(onlyFileName.toLowerCase())) {
            containsFileName = true;
        }
        for (int i = 0; i < dirs.size(); i++) {
            if (programName.toLowerCase().contains(dirs.get(i).toLowerCase())) {
                containsAnyDir = true;
                break;
            }
        }
        if (containsAnyDir && containsFileName) {
            return TargetMarker.BOTH;
        }
        if (containsAnyDir) {
            return TargetMarker.IN_PATH;
        }
        if (containsFileName) {
            return TargetMarker.IN_FILE;
        }
        return TargetMarker.NONE;
    }
}
