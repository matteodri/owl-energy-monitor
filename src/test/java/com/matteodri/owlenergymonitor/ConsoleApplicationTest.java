package com.matteodri.owlenergymonitor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matteodri.owlenergymonitor.services.OwlMessageProcessor;

@ExtendWith(MockitoExtension.class)
class ConsoleApplicationTest {

    @Mock
    private OwlMessageProcessor owlMessageProcessor;

    @InjectMocks
    private ConsoleApplication target = new ConsoleApplication();

    @Test
    @DisplayName("Message received on custom address")
    void run() throws Exception {
        String payload = "<these are the contents of a multicast message!>";
        String multicastAddress = "224.10.10.10";
        int multicastPort = 23456;
        target.setMulticastAddress(multicastAddress);
        target.setMulticastPort(multicastPort);

        try {

            new Thread(() -> {
                try {
                    target.run();
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                    Assertions.fail();
                }
            }).start();

            Thread.sleep(2000L);

            publishMessage(multicastAddress, multicastPort, payload);

        } catch (Exception ioe) {
            ioe.printStackTrace();
            Assertions.fail();
        }

        verify(owlMessageProcessor).process(any(InetAddress.class), eq(payload));
    }

    private void publishMessage(String multicastAddress, int multicastPort, String payload) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(multicastAddress);
        byte[] buffer = payload.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, multicastPort);
        socket.send(packet);
        socket.close();

    }
}
