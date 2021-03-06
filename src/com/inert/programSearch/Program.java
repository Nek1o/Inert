package com.inert.programSearch;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Program {
    private String name;
    private String path;
    @JsonIgnore
    private Precision precision;
    //...


    public Program(String name, String path, Precision precision) {
        this.name = name;
        this.path = path;
        this.precision = precision;
    }

    public Program(String name, String path) {
        this.name = name;
        this.path = path;
        this.precision = Precision.SIMILAR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Precision getPrecision() {
        return precision;
    }

    public void setPrecision(Precision precision) {
        this.precision = precision;
    }

    @Override
    public String toString() {
        return "\nname: " + name + "\npath " + path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equals(name, program.name) && Objects.equals(path, program.path) && precision == program.precision;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, precision);
    }
}
