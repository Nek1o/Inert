package processes;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessStarter {
    private static ProcessStarter processStarter;

    public static ProcessStarter getInstance() {
        if (processStarter == null) {
            processStarter = new ProcessStarter();
        }
        return processStarter;
    }
    public Process start(String cmd, boolean wait) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmd);
            //BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            //если все равно глючит хрень ниже надо раскомментировать
            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
        }
        catch(java.io.IOException e) {
            System.out.println("Can'not execute: " + cmd);
            return null;
        }
        if (wait) {
            try {
                p.waitFor();
            }
            catch (java.lang.InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return p;
    }
}
