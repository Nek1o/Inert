package com.inert.programSearch;

import java.nio.file.Path;
import java.util.Objects;

public class Program {
    private String name;
    private String path;
    //...


    public Program(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public String toString() {
        return "Program: " + "name = " + name + '\'' + ", path = " + path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equals(name, program.name) && Objects.equals(path, program.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path);
    }
}
