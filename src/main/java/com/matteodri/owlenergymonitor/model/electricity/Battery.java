package com.matteodri.owlenergymonitor.model.electricity;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "battery")
public class Battery {

    private String level;

    public String getLevel() {
        return level;
    }

    @XmlAttribute
    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return Battery.class.getSimpleName() + "{" + "level='" + level + '\'' + '}';
    }
}
