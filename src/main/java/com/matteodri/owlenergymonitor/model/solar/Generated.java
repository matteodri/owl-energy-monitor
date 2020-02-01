package com.matteodri.owlenergymonitor.model.solar;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "generated")
public class Generated {

    private String units;
    private Double value;

    public String getUnits() {
        return units;
    }

    @XmlAttribute
    public void setUnits(String units) {
        this.units = units;
    }

    public Double getValue() {
        return value;
    }

    @XmlValue
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Generated.class.getSimpleName() + "{" + "units='" + units + '\'' + ", value=" + value + '}';
    }
}
