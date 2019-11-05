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

    public static final String DEFAULT_MULTICAST_ADDRESS = "224.192.32.19";
    public static final int DEFAULT_MULTICAST_PORT = 22600;

    @Autowired
    private OwlMessageProcessor owlMessageProcessor;

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        app.setBannerMode(Mode.CONSOLE);
        app.run(args);

    }

    @Override
    public void run(String... args) throws Exception {
        String ipAddress = DEFAULT_MULTICAST_ADDRESS;
        int port = DEFAULT_MULTICAST_PORT;
        MulticastSocket socket = null;
        DatagramPacket inPacket = null;
        byte[] inBuf = new byte[256];
        try {
            socket = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName(ipAddress);
            socket.joinGroup(address);
            logger.info("Listening to multicast address " + ipAddress + ":" + port + "...");
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
