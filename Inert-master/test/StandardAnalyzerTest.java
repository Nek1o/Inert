import com.inert.programSearch.StandardAnalyzer;
import org.junit.Test;
import org.junit.Assert;

public class StandardAnalyzerTest {


    @Test
    public void testStandardDirs() {
        String[] knownStandardDirs = {"Windows", "Microsoft", "nvidia"};

        for (int i = 0; i < knownStandardDirs.length; i++) {
            Assert.assertTrue(StandardAnalyzer.getInstance().isStandardDir(knownStandardDirs[i]));
        }
    }

    @Test
    public void testSimpleDirs() {
        String[] knownNonStandardDirs = {"Programs", "Games", "StarCraft"};

        for (int i = 0; i < knownNonStandardDirs.length; i++) {
            Assert.assertFalse(StandardAnalyzer.getInstance().isStandardDir(knownNonStandardDirs[i]));
        }
    }

    @Test
    public void testStandardExe() {
        String[] knownStandardExes = {"install", "uninstall", "setup"};
        for (int i = 0; i < knownStandardExes.length; i++) {
            Assert.assertTrue(StandardAnalyzer.getInstance().isStandardExe(knownStandardExes[i]));
        }
    }

    @Test
    public void testSimpleExe() {
        String[] knownNonStandardExes = {"launcher", "run", "game"};

        for (int i = 0; i < knownNonStandardExes.length; i++) {
            Assert.assertFalse(StandardAnalyzer.getInstance().isStandardExe(knownNonStandardExes[i]));
        }
    }
}
