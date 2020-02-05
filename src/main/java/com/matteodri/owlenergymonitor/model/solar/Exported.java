package com.matteodri.owlenergymonitor.model.solar;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "exported")
public class Exported {

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
        return Exported.class.getSimpleName() + "{" + "units='" + units + '\'' + ", value=" + value + '}';
    }
}
