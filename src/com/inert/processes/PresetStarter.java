package com.inert.processes;

import com.inert.presets.Preset;

import java.util.List;

public class PresetStarter {

    // TODO Добавить возврат процессов
    public static void start (Preset preset) {
        ProcessStarter processStarter = new ProcessStarter();
        for (String programToStart:
             preset.getPaths()) {
            processStarter.start(programToStart, false);
        }
    }
}
