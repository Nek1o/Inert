package com.inert.presets;

import com.inert.help.FileHelper;
import com.inert.processes.PresetStarter;
import com.inert.programSearch.Program;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresetManager {

    private Map<String, Preset> presets;
    private Map<String, Program> programs;

    public PresetManager() throws IOException {
        presets = new HashMap<>();
       // programs = FileHelper.loadPathsAndPrograms();
    }

    public Map<String, Program> getPrograms() throws IOException {
        programs = FileHelper.loadPathsAndPrograms();
        return programs;
    }

    public Preset getPreset(String name) {
        return presets.get(name);
    }

    public void addPreset(Preset preset) {
        presets.put(preset.getName(), preset);
    }

    public void removePreset(@NotNull Preset preset) {
        presets.remove(preset.getName());
    }

    public void removePreset(String name) {
        presets.remove(name);
    }

    public void startPreset(@NotNull Preset preset) {
        if (presets.containsKey(preset.getName())) {
            PresetStarter.start(presets.get(preset.getName()));
        }
    }

    public void startPreset(String name) {
        if (presets.containsKey(name)) {
            PresetStarter.start(presets.get(name));
        }
    }

    public boolean contains(Preset preset) {
        return presets.containsKey(preset.getName());
    }

    public boolean contains(String name) {
        return presets.containsKey(name);
    }
}
