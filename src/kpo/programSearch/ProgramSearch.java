package kpo.programSearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProgramSearch {
    private List<String> Paths;
    Path root;
    public ProgramSearch() {
        Paths = new ArrayList<>();
        root = java.nio.file.Paths.get("C:" + File.separator);
    }

    @Override
    public String toString() {
        return "ProgramSearch{" +
                "Paths=" + Paths +
                ", root=" + root +
                '}';
    }

//    private List<String> getProgramNames(List<String> fileNames, Path dir) {
//
//        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
//            for (Path path : stream) {
//                if(path.toFile().isDirectory()) {
//                    if () {
//                        getProgramNames(fileNames, path);
//                    }
//                } else {
//                    if (path.toAbsolutePath().toString().contains(".exe")) {
//                        fileNames.add(path.toAbsolutePath().toString());
//                        System.out.println(path.getFileName());
//                    }
//
//                }
//            }
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//        return fileNames;
//    }
//    public List<String> getUsablePrograms () {
//        // Не брать uninstaller'ы, системные программы
//        List<String> unusablePrograms;
//        unusablePrograms = this.getProgramNames(Paths, root); // Путь до корневого каталога
//        List<String> names = new ArrayList<>();
//        String[] pureName;
//        // Отделяем имена программ от полных путей для удобства
//        // А стоит ли?
//        for (String nameWithPath : unusablePrograms) {
//            pureName = nameWithPath.split(File.separator);
//            if ( ) {
//                names.add(nameWithPath);
//            }
//            //names.add(strings[strings.length - 1]);
//        }
//
//        return names;
//    }
}
