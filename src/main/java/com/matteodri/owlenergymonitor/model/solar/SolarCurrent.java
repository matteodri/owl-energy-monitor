package com.matteodri.owlenergymonitor.model.solar;

public class SolarCurrent {

    private Generating generating;
    private Exporting exporting;

    public Generating getGenerating() {
        return generating;
    }

    public void setGenerating(Generating generating) {
        this.generating = generating;
    }

    public Exporting getExporting() {
        return exporting;
    }

    public void setExporting(Exporting exporting) {
        this.exporting = exporting;
    }

    @Override
    public String toString() {
        return SolarCurrent.class.getSimpleName() + "{" + "generating=" + generating + ", exporting=" + exporting + '}';
    }
}
