package com.matteodri.owlenergymonitor.model.solar;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "generated")
public class Generated {

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
        return Generated.class.getSimpleName() + "{" + "units='" + units + '\'' + ", value=" + value + '}';
    }
}
