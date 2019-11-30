package com.inert.presets;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.inert.programSearch.Precision;
import com.inert.programSearch.Program;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Preset {
    private List<Program> programs;
    private PresetHelper presetHelper;
    private String name;

    public Preset(String name) {
        this.name = name;
        presetHelper = new PresetHelper();
        presetHelper.createPresetJson(name);
    }

    public void addProgram(Program program) {
        programs.add(program);
        try {
            presetHelper.savePreset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // сохранить это в файл
    }

    public void deleteProgram(Program program) {
        programs.remove(program);
        try {
            presetHelper.savePreset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public List<String> getPaths() {
        List<String> paths = new ArrayList<>();
        for (Program program : programs) {
            paths.add(program.getPath());
        }
        return paths;
    }

    /**
     * Класс для работы с файлами для пресетов.
     */
    class PresetHelper {

        private File presetDirectory;

        // Конструктор
        public PresetHelper() {
            presetDirectory = new File("resources" + File.separator +
                    "presets");
        }


        /**
         * Создаёт JSON-файл для одного пресета в директории presets
         * @param name имя пресета
         */
        public void createPresetJson(String name) {
            File singlePresetJson = new File(presetDirectory.getAbsolutePath()+ name + ".json");
            try {
                if (singlePresetJson.createNewFile()) {
                    System.out.println("Successful preset file creating");
                }
                else {
                    System.out.println("Failed to create a preset file");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Сохраняет пресет, перезаписывая его
         * @throws IOException
         */
        public void savePreset() throws IOException {
            try (JsonWriter writer = new JsonWriter(new FileWriter(presetDirectory.getAbsolutePath() +
                    File.separator + name))) {
                writer.beginObject();
                Integer k = 1;
                for (Program program : programs) {
                    writer.name("program" + k.toString());
                    writer.beginArray();
                    writer.value(program.getName());
                    writer.value(program.getPath());
                    writer.endArray();
                    ++k;
                }
                writer.endObject();
            }
        }

        /**
         * Загружает пресет из JSON-файла
         * @return
         * @throws IOException
         */
        public Map<String, Program> loadPreset() throws IOException {
            Map<String, Program> programs = new HashMap<>();
            Integer k = 1;
            try (JsonReader reader = new JsonReader(new FileReader("resources" + File.separator +
                    presetDirectory.getAbsolutePath() + File.separator + name + ".json"))) {
                reader.beginObject();
                while (reader.hasNext()) {
                    if (reader.nextName().equals("program" + k.toString())) {
                        reader.beginArray();
                        String programName = reader.nextString();
                        String programPath = reader.nextString();
                        programs.put(programName, new Program(programName, programPath, Precision.CORRECT));
                        reader.endArray();;
                    }
                    ++k;
                }
                reader.endObject();
            }
            return programs;
        }

        public void updatePreset() {

        }

    }
}

