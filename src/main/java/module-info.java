module com.matteodri.owl.energy.monitor {
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires spring.boot.actuator.autoconfigure;
    requires micrometer.core;
    requires java.xml.bind;
    requires java.activation;
    opens com.matteodri.owlenergymonitor;
    opens com.matteodri.owlenergymonitor.services;
    opens com.matteodri.owlenergymonitor.util;
    opens com.matteodri.owlenergymonitor.model.electricity to java.xml.bind;
    opens com.matteodri.owlenergymonitor.model.solar to java.xml.bind;
}
