import com.inert.programSearch.PathAnalyzer;
import com.inert.programSearch.TargetMarker;
import org.junit.Test;
import org.junit.Assert;

public class PathAnalyzerTest {

    @Test
    public void testInFile(){
        String fileName = "audacity.exe";
        String[] paths = {
                "C:\\Users\\Никита\\Desktop\\audacity.exe",
                "C:\\Program Files\\Mozilla Firefox\\audacity.exe",
                "C:\\Program Files\\MATLAB\\audacity.exe"
        };

        for (int i = 0; i < paths.length; i++) {
            Assert.assertEquals(TargetMarker.IN_FILE, PathAnalyzer.analyse(fileName,paths[i]));
        }

    }
    @Test
    public void testInPath(){
        String[] fileName = {"osu!.exe", "audacity.exe"};

        String[] paths = {
                "C:\\Users\\Никита\\AppData\\Local\\osu!\\os123213u!.exe",
                "C:\\Program Files (x86)\\Audacity\\audaci123213ty.exe"
        };

        for (int i = 0; i < paths.length; i++) {
            Assert.assertEquals(TargetMarker.IN_PATH, PathAnalyzer.analyse(fileName[i],paths[i]));
        }

    }
    @Test
    public void testBoth(){
        String[] fileName ={"osu!.exe", "Katawa Shoujo.exe", "audacity.exe"};
        String[] paths = {
                "C:\\Users\\Никита\\AppData\\Local\\osu!\\osu!.exe",
                "\"C:\\Program Files (x86)\\Katawa Shoujo\\Katawa Shoujo.exe",
                "C:\\Program Files (x86)\\Audacity\\audacity.exe"
        };

        for (int i = 0; i < paths.length; i++) {
            Assert.assertEquals(TargetMarker.BOTH, PathAnalyzer.analyse(fileName[i],paths[i]));
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
