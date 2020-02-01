package com.matteodri.owlenergymonitor.model.electricity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Channel {

    private String id;
    private ElectricityCurrent electricityCurrent;
    private ElectricityDay electricityDay;

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public ElectricityCurrent getElectricityCurrent() {
        return electricityCurrent;
    }

    @XmlElement(name = "curr")
    public void setElectricityCurrent(ElectricityCurrent electricityCurrent) {
        this.electricityCurrent = electricityCurrent;
    }

    public ElectricityDay getElectricityDay() {
        return electricityDay;
    }

    @XmlElement(name = "day")
    public void setElectricityDay(ElectricityDay electricityDay) {
        this.electricityDay = electricityDay;
    }

    @Override
    public String toString() {
        return Channel.class.getSimpleName() + "{" + "id='" + id + '\'' + ", electricityCurrent=" + electricityCurrent
                + ", electricityDay=" + electricityDay + '}';
    }
}
