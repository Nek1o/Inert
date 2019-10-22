package com.inert.presets;

import java.util.ArrayList;
import java.util.List;

public class Preset {
    private List<String> paths;
    private String name;

    public Preset() {
        paths = new ArrayList<>();
        name = "New Preset";
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPath(String path) {
        paths.add(path);
    }

    public void removePath(int i){
        paths.remove(i);
    }

    public void removePath(String path) {
        paths.remove(path);
    }
}
