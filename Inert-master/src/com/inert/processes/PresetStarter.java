package com.inert.processes;

import com.inert.presets.Preset;
import com.inert.programSearch.Program;

import java.util.List;

public class PresetStarter {

    // TODO Добавить возврат процессов
    public static void start (Preset preset) {
        ProcessStarter processStarter = new ProcessStarter();
        for (Program programToStart:
             preset.getPrograms()) {
            processStarter.programStart(programToStart);
        }
    }
}
