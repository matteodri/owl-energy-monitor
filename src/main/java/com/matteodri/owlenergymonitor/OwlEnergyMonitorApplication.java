package com.matteodri.owlenergymonitor;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Base Spring Boot application class.
 *
 * @author Matteo Dri 05 Nov 2019
 */
@SpringBootApplication
public class OwlEnergyMonitorApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OwlEnergyMonitorApplication.class);
        app.setBannerMode(Mode.CONSOLE);
        app.run(OwlEnergyMonitorApplication.class);
    }
}
