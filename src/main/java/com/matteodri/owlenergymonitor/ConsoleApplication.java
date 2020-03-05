package com.matteodri.owlenergymonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${multicast-address:224.192.32.19}")
    private String multicastAddress;

    @Value("${multicast-port:22600}")
    private int multicastPort;


    @Autowired
    private OwlMessageProcessor owlMessageProcessor;

    public void setMulticastAddress(String multicastAddress) {
        this.multicastAddress = multicastAddress;
    }

    public void setMulticastPort(int multicastPort) {
        this.multicastPort = multicastPort;
    }

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        app.setBannerMode(Mode.CONSOLE);
        app.run(args);

    }

    @Override
    public void run(String... args) throws Exception {

        final MulticastSocket socket = new MulticastSocket(multicastPort);
        InetAddress address = InetAddress.getByName(multicastAddress);
        socket.joinGroup(address);
        logger.info("Listening to multicast address: {}:{}...", multicastAddress, multicastPort);

        // run infinite loop listening for multicast messages in separate thread
        new Thread(() -> {
            byte[] inBuf = new byte[2048];
            DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);
            while (true) {
                try {
                    socket.receive(inPacket);
                    String payload = new String(inBuf, 0, inPacket.getLength());
                    owlMessageProcessor.process(inPacket.getAddress(), payload);

                } catch (IOException ioe) {
                    logger.error("Error encountered listening to Owl messages", ioe);
                }
            }
        }).start();
    }

}
