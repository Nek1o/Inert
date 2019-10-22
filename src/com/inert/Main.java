package com.inert;

import com.inert.help.FileHelper;
import com.inert.programSearch.ProgramSearch;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // TODO Есть класс для открытия ассоцириованных программ, на всякий случай

        //FileHelper.scriptStart();

        List<String> paths;
        ProgramSearch programSearch = new ProgramSearch();
        paths = programSearch.getPaths();
        for (String name : paths) {
            System.out.println(name);
        }
    }
}
