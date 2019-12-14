package com.inert;


import com.inert.help.FileHelper;
import com.inert.presets.Preset;
import com.inert.processes.PresetStarter;
import com.inert.programSearch.Program;
import com.inert.programSearch.ProgramSearch;
import com.inert.programSearch.ProgramSearchMultiThread;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.DoubleUnaryOperator;

public class MainLogic {

    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO Есть класс для открытия ассоцириованных программ, на всякий случай

        Preset preset = new Preset("test");

        ProgramSearch programSearch = new ProgramSearchMultiThread();
        Map<String, Program> programsMap = programSearch.getPaths();
//        for(Map.Entry<String, Program> x : programSearch.getPaths().entrySet()){
//            //preset.addProgram(x.getValue());
//            System.out.println(x.getValue());
//        }
        preset.addProgram(programsMap.get("mozilla firefox 69.0.3 (x64 ru)"));
        preset.addProgram(programsMap.get("katawa shoujo"));
        PresetStarter.start(preset);

        //FileHelper.launchForTheFirstTime();
    }




}
