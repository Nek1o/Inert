import com.inert.programSearch.PathAnalyzer;
import com.inert.programSearch.TargetMarker;
import org.junit.Test;
import org.junit.Assert;

public class PathAnalyzerTest {

    @Test
    public void testInFile(){
        String fileName = "myprogram.exe";
        String[] paths = {
                "C:\\Users\\Анатолий\\Desktop\\Inert-master\\myprogram.exe",
                "C:\\Program Files (x86)\\Hero Editor\\myprogram.exe",
                "C:\\Program Files (x86)\\DeskPins\\myprogram.exe"
        };

        for (int i = 0; i < paths.length; i++) {
            Assert.assertEquals(TargetMarker.IN_FILE, PathAnalyzer.analyse(fileName,paths[i]));
        }

    }
    @Test
    public void testInPath(){
        String fileName = "myprogram.exe";
        String[] paths = {
                "C:\\Users\\myprogram\\Desktop\\Inert-master\\myp3456rogram.exe",
                "C:\\Program Files (x86)\\myprogram\\mypro567867589gram.exe",
                "C:\\myprogram\\DeskPins\\mypr56874ogra46m.exe"
        };

        for (int i = 0; i < paths.length; i++) {
            Assert.assertEquals(TargetMarker.IN_PATH, PathAnalyzer.analyse(fileName,paths[i]));
        }

    }
    @Test
    public void testBoth(){
        String fileName = "myprogram.exe";
        String[] paths = {
                "C:\\Users\\myprogram\\Desktop\\Inert-master\\myprogram.exe",
                "C:\\Program Files (x86)\\myprogram\\myprogram.exe",
                "C:\\myprogram\\DeskPins\\myprogram.exe"
        };

        for (int i = 0; i < paths.length; i++) {
            Assert.assertEquals(TargetMarker.BOTH, PathAnalyzer.analyse(fileName,paths[i]));
        }

    }
    @Test
    public void testNone(){
        String fileName = "myprogram.exe";
        String[] paths = {
                "C:\\Games\\S.T.A.L.K.E.R. Shadow of Chernobyl\\Settings.exe",
                "C:\\Games\\Super Seducer 2.exe",
                "C:\\Program Files\\dotnet\\dotnet.exe"
        };

        for (int i = 0; i < paths.length; i++) {
            Assert.assertEquals(TargetMarker.NONE, PathAnalyzer.analyse(fileName,paths[i]));
        }

    }
}
