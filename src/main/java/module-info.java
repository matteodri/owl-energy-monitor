module com.matteodri.owl.energy.monitor {
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires java.sql;
    requires org.apache.logging.log4j;
    opens com.matteodri.owlenergymonitor;
    //opens com.matteodri.owlenergymonitor.services;
    opens com.matteodri.owlenergymonitor.util;
}