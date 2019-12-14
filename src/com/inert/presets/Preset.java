package com.inert.presets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Preset {

    @JsonProperty("name")
    private String name;

    @JsonProperty("programs")
    private List<Program> programs;

    @JsonIgnore
    private PresetHelper presetHelper;

    public Preset() {}

    // getter
    public String getName() {
        return name;
    }
    // setter
    public void setName(String name) {
        this.name = name;
    }
    // getter
    public PresetHelper getPresetHelper() {
        return presetHelper;
    }
    // setter
    public void setPresetHelper(PresetHelper presetHelper) {
        this.presetHelper = presetHelper;
    }

    public Preset(String name) {
        programs = new ArrayList<>();
        this.name = name;
        presetHelper = new PresetHelper();
        presetHelper.createPresetJSON();
    }

    public void addProgram(Program program) {
        programs.add(program);
        try {
            presetHelper.savePresetToJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO Сделать добавление листа программ.
    public void addPrograms(List<Program> programs) {

    }

    public void deleteProgram(Program program) {
        programs.remove(program);
        try {
            presetHelper.savePresetToJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO Сделать удаление всего пресета

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
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
         */
        private void createPresetJSON() {
            File singlePresetJson = new File(presetDirectory.getAbsolutePath() +
                    File.separator + name + ".json");
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
        public void savePresetToJSON() throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(presetDirectory.getAbsolutePath() +
                            File.separator + name + ".json"),
                    Preset.this);


//            try (JsonWriter writer = new JsonWriter(new FileWriter(presetDirectory.getAbsolutePath() +
//                    File.separator + name))) {
//                writer.beginObject();
//                Integer k = 1;
//                for (Program program : programs) {
//                    writer.name("program" + k.toString());
//                    writer.beginArray();
//                    writer.value(program.getName());
//                    writer.value(program.getPath());
//                    writer.endArray();
//                    ++k;
//                }
//                writer.endObject();
//            }
        }
        /**
         * Загружает пресет из JSON-файла
         * @return
         * @throws IOException
         */
        public void loadPresetFromJSON() throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            Preset preset = mapper.readValue(new File(presetDirectory.getAbsolutePath() +
                            File.separator + name + ".json"),
                    Preset.class);
            Preset.this.setPrograms(preset.getPrograms());
            Preset.this.setName(preset.getName());



//            Map<String, Program> programs = new HashMap<>();
//            Integer k = 1;
//            try (JsonReader reader = new JsonReader(new FileReader(presetDirectory.getAbsolutePath() +
//                    File.separator + name + ".json"))) {
//                reader.beginObject();
//                while (reader.hasNext()) {
//                    if (reader.nextName().equals("program" + k.toString())) {
//                        reader.beginArray();
//                        String programName = reader.nextString();
//                        String programPath = reader.nextString();
//                        programs.put(programName, new Program(programName, programPath, Precision.CORRECT));
//                        reader.endArray();;
//                    }
//                    ++k;
//                }
//                reader.endObject();
//            }
//            return programs;
        }

        public void updatePreset() {

        }

    }
}

