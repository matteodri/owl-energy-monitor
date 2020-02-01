package com.matteodri.owlenergymonitor.model.solar;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper for solar production measurements
 */
@XmlRootElement(name = "solar")
public class Solar {

    private String id;
    private Long timestamp;
    private SolarCurrent solarCurrent;
    private SolarDay solarDay;

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public SolarCurrent getSolarCurrent() {
        return solarCurrent;
    }

    @XmlElement(name = "current")
    public void setSolarCurrent(SolarCurrent solarCurrent) {
        this.solarCurrent = solarCurrent;
    }

    public SolarDay getSolarDay() {
        return solarDay;
    }

    @XmlElement(name = "day")
    public void setSolarDay(SolarDay solarDay) {
        this.solarDay = solarDay;
    }

    @Override
    public String toString() {
        return Solar.class.getSimpleName() + "{" + "id='" + id + '\'' + ", timestamp=" + timestamp + ", solarCurrent="
                + solarCurrent + ", solarDay=" + solarDay + '}';
    }
}
