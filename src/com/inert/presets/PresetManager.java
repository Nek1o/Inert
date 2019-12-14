package com.inert.presets;

import com.inert.processes.PresetStarter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresetManager {
    private Map<String, Preset> presets;

    public PresetManager() {
        presets = new HashMap<>();
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
}
