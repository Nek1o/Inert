package controller;

import help.FileHelper;
import javafx.scene.control.ProgressBar;
import processes.ProcessStarter;
import search.ProgramSearch;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // TODO Искать все .exe файлы - чушь
        // TODO Нужно найти все установленные программы, потом искать пути к ним
        // TODO Запустить powershell скрипт, который это сделает
        // TODO Отфильтровать полученные программы
        // TODO Есть класс для открытия ассоцириованных программ, на всякий случай

        //FileHelper.scriptStart();

        List<String> paths;
        ProgramSearch programSearch = new ProgramSearch();
        paths = programSearch.getPaths();
        for (String name : paths) {
            System.out.println(name);
        }



//        ProcessStarter processStarter = new ProcessStarter();
//        processStarter.("C:\\Users\\Никита\\AppData\\Local\\osu!\\osu!.exe", true);

    }
}
