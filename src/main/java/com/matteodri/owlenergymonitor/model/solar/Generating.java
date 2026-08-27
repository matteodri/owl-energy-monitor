package com.matteodri.owlenergymonitor.model.solar;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "generating")
public class Generating {

    private String units;
    private Float value;

    public String getUnits() {
        return units;
    }

    @XmlAttribute
    public void setUnits(String units) {
        this.units = units;
    }

    public Float getValue() {
        return value;
    }

    @XmlValue
    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Generating.class.getSimpleName() + "{" + "units='" + units + '\'' + ", value=" + value + '}';
    }
}
