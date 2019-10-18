package search;

import help.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProgramSearch {
    public  List<String> Paths;
    public Path root;
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

    public List<String> getProgramPaths(List<String> fileNames, Path dir) {

        List<List<String>> names = FileHelper.refineNames(FileHelper.loadUnrefinedNames());

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if(path.toFile().isDirectory()) {
                    getProgramPaths(fileNames, path);
                } else {
                    if (path.toAbsolutePath().toString().contains(".exe")) {
                        String nameWithoutExe = path.getFileName().toString();
                        nameWithoutExe = nameWithoutExe.replace(".exe", "");
                        for (int i = 0; i < names.size(); i++) {
                            if (names.get(i).contains(nameWithoutExe)) {
                                fileNames.add(path.toAbsolutePath().toString());
                                break;
                            }
                        }
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return fileNames;
    }
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
