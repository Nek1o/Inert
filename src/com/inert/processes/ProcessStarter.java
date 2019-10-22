package com.inert.processes;

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

    public static void scriptStart() {
        // Стартуем скрипт
        ProcessStarter processStarter = ProcessStarter.getInstance();
        Process p = processStarter.start("powershell -ExecutionPolicy UnRestricted -File .\\Search.ps1",
                false);
        // Пока обходим ожидание созданиия файлов вот так
        while (p.isAlive()) {
            try {
                //Thread.currentThread().wait();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Thread.currentThread().start();

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
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return p;
    }
}
