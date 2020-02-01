package com.matteodri.owlenergymonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.matteodri.owlenergymonitor.services.OwlMessageProcessor;

/**
 * Base Spring Boot application class.
 *
 * @author Matteo Dri 05 Nov 2019
 */
@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(ConsoleApplication.class);

    private static final String DEFAULT_MULTICAST_ADDRESS = "224.192.32.19";
    private static final int DEFAULT_MULTICAST_PORT = 22600;

    private String multicastAddress = DEFAULT_MULTICAST_ADDRESS;
    private int multicastPort = DEFAULT_MULTICAST_PORT;

    public void setMulticastAddress(String multicastAddress) {
        this.multicastAddress = multicastAddress;
    }

    public void setMulticastPort(int multicastPort) {
        this.multicastPort = multicastPort;
    }

    @Autowired
    private OwlMessageProcessor owlMessageProcessor;

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        app.setBannerMode(Mode.CONSOLE);
        app.run(args);

    }

    @Override
    public void run(String... args) throws Exception {
        MulticastSocket socket = null;
        DatagramPacket inPacket = null;
        byte[] inBuf = new byte[2048];
        try {
            socket = new MulticastSocket(multicastPort);
            InetAddress address = InetAddress.getByName(multicastAddress);
            socket.joinGroup(address);
            logger.info("Listening to multicast address " + multicastAddress + ":" + multicastPort + "...");
            while (true) {
                inPacket = new DatagramPacket(inBuf, inBuf.length);
                socket.receive(inPacket);
                String payload = new String(inBuf, 0, inPacket.getLength());
                owlMessageProcessor.process(inPacket.getAddress(), payload);
            }
        } catch (IOException ioe) {
            logger.error("Error encountered listening to Owl messages", ioe);
        }
    }

}
