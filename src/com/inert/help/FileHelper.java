package com.inert.help;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.inert.processes.ProcessStarter;
import com.inert.programSearch.Precision;
import com.inert.programSearch.Program;
import com.inert.programSearch.ProgramSearchMultiThread;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHelper {


    public static List<String> loadUnrefinedNames() throws InterruptedException {
        // Необходимо подождать, пока скрипт закончит своё выполнение и файл создастся
        // Иначе смерть и NullPointerException
        // Сделано ожидание в scriptStart

        File file = new File("resources" + File.separator + "programas-instalados.txt");
        if (!file.exists()) {
            ProcessStarter.scriptStart();
        }

        ArrayList<String> fileContents = new ArrayList<>();
        try {
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(file), StandardCharsets.UTF_16LE));
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.add(line);
            }
            reader.close();
            // Если ничего не было записано
            if (fileContents.isEmpty()) {
                return null;
            } else {
                return fileContents;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /* TODO сохранять в формате имя : путь. При запуске считывать и хранить в ОЗУ в map
            При создании пресета выбирать по имени из map'а и созранять PresetHelper'ом
    */
    public static void savePathsAndPrograms(Map<String, Program> pathsAndPrograms) throws IOException {
        BufferedWriter writer =
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(new File("resources" + File.separator +
                                        "PathsAndProgramsStorage.txt")), StandardCharsets.UTF_16LE));
        for (Map.Entry<String, Program> pair : pathsAndPrograms.entrySet()) {
            // сохраняем через пробел и двоеточие
            writer.write(pair.getValue().getName() + " : " + pair.getValue().getPath() + "\n");
        }
        writer.close();
    }

    public static Map<String, Program> loadPathsAndPrograms() throws IOException {
        Map<String, Program> loadedPathsAndPrograms = new HashMap<>();
        List<String> lines;
        lines = Files.readAllLines(Paths.get("resources" + File.separator +
                "PathsAndProgramsStorage.txt"), StandardCharsets.UTF_16LE);
        for (String line : lines) {
            loadedPathsAndPrograms.put(line.substring(0, line.indexOf(" :")), // имя
                    new Program(line.substring(0, line.indexOf(" :")), // имя
                            line.substring(line.indexOf(": ") + 1))); // путь
        }
        return loadedPathsAndPrograms;
    }

    /**
     * Сохраняет программы, полученные поиском ProgramSearchMultiThread, в JSON файл в формате:
     * programk: [ programName: name, programPath: path]
     *
     * @param pathsAndPrograms
     * @throws IOException
     */
    public static void savePathsAndProgramsToJson(Map<String, Program> pathsAndPrograms) throws IOException {
        try (JsonWriter writer = new JsonWriter(new FileWriter("resources" + File.separator + "PathsAndPrograms.json"))) {
            writer.beginObject();
            Integer k = 1;
            for (Map.Entry<String, Program> pair : pathsAndPrograms.entrySet()) {
                writer.name("program" + k.toString());
                writer.beginArray();
                writer.value(pair.getValue().getName());
                writer.value(pair.getValue().getPath());
                writer.endArray();
                ++k;
            }
            writer.endObject();
        }
    }

    /**
     * Возвращает map из всех сохранённых программ и путей к ним, найденным
     * ProgramSearchMultiThread, и сохраненных с помощью savePathsAndProgramsToJson
     *
     * @return
     * @throws IOException
     */
    public static Map<String, Program> loadPathAndProgramsFromJson() throws IOException {
        Map<String, Program> programs = new HashMap<>();
        Integer k = 1;
        try (JsonReader reader = new JsonReader(new FileReader("resources" + File.separator + "PathsAndPrograms.json"))) {
            reader.beginObject();
            while (reader.hasNext()) {
                if (reader.nextName().equals("program" + k.toString())) {
                    reader.beginArray();
                    String programName = reader.nextString();
                    String programPath = reader.nextString();
                    programs.put(programName, new Program(programName, programPath, Precision.CORRECT));
                    reader.endArray();
                    ;
                }
                ++k;
            }
            reader.endObject();
        }
        return programs;
    }

    // TODO класс для запуска и запуска в первый раз
    public static Boolean isFirstLaunch() {
        File presetStorageFile = new File("resources" + File.pathSeparator + "PathsAndProgramsStorage.txt");
        return !presetStorageFile.exists();
    }

    public static void launchForTheFirstTime() throws IOException {
        //File presetStorageFile = new File("resources" + File.pathSeparator + "PathsAndProgramsStorage.json");
        //presetStorageFile.createNewFile();
        ProgramSearchMultiThread programSearchMultiThread = new ProgramSearchMultiThread();
        try {
            Map<String, Program> programs = programSearchMultiThread.getPaths();
            //savePathsAndPrograms(programs);
            savePathsAndPrograms(programs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
