package com.matteodri.owlenergymonitor.model.electricity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "battery")
public class Battery {

    String level;

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
