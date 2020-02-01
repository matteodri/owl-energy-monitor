package com.matteodri.owlenergymonitor.model.solar;

public class SolarDay {

    private Generated generated;
    private Exported exported;

    public Generated getGenerated() {
        return generated;
    }

    public void setGenerated(Generated generated) {
        this.generated = generated;
    }

    public Exported getExported() {
        return exported;
    }

    public void setExported(Exported exported) {
        this.exported = exported;
    }

    @Override
    public String toString() {
        return SolarDay.class.getSimpleName() + "{" + "generated=" + generated + ", exported=" + exported + '}';
    }
}
