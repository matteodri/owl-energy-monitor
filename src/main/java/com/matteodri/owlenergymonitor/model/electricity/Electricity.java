package com.matteodri.owlenergymonitor.model.electricity;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper for electricity measurements
 */
@XmlRootElement(name = "electricity")
public class Electricity {

    private String id;
    private String ver;
    private Long timestamp;
    private Battery battery;
    private List<Channel> channels;

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getVer() {
        return ver;
    }

    @XmlAttribute
    public void setVer(String ver) {
        this.ver = ver;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }


    public List<Channel> getChannels() {
        return channels;
    }

    @XmlElementWrapper(name = "channels")
    @XmlElement(name = "chan")
    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        return Electricity.class.getSimpleName() + "{" + "id='" + id + '\'' + ", ver='" + ver + '\'' + ", timestamp="
                + timestamp + ", battery=" + battery + ", channels=" + channels + '}';
    }
}
