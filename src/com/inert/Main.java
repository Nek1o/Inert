package com.inert;


import com.inert.programSearch.Program;
import com.inert.programSearch.ProgramSearch;
import com.inert.programSearch.ProgramSearchMultiThread;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO Есть класс для открытия ассоцириованных программ, на всякий случай

        //FileHelper.scriptStart();

//        Scanner scanner = new Scanner(System.in);
//        scanner.nextInt();
        ProgramSearch programSearch = new ProgramSearchMultiThread();
        for (Map.Entry<String, Program> x : programSearch.getPaths().entrySet()) {
            System.out.println(x.getValue().toString());
        }
    }
}
