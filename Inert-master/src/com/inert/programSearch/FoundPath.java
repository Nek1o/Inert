package com.inert.programSearch;

public class FoundPath {
    private String potentialPath;
    private TargetMarker targetMarker;

    public FoundPath(String potentialPath, TargetMarker targetMarker) {
        this.potentialPath = potentialPath;
        this.targetMarker = targetMarker;
    }

    public String getPotentialPath() {
        return potentialPath;
    }

    public void setPotentialPath(String potentialPath) {
        this.potentialPath = potentialPath;
    }

    public TargetMarker getTargetMarker() {
        return targetMarker;
    }

    public void setTargetMarker(TargetMarker targetMarker) {
        this.targetMarker = targetMarker;
    }
}
