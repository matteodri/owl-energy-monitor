package com.matteodri.owlenergymonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Class to be used for test purposes. Process listens for multicast messages on a given address and port.
 *
 * @author Matteo Dri 02 Nov 2019
 */
public class MulticastListener {

    public static void main(String[] args) {
        String ipAddress = "224.192.32.19";
        int port = 22600;
        MulticastSocket socket = null;
        DatagramPacket inPacket = null;
        byte[] inBuf = new byte[256];
        try {
            socket = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName(ipAddress);
            socket.joinGroup(address);
            System.out.println("Listening to multicast address " + ipAddress + ":" + port + "...");
            while (true) {
                inPacket = new DatagramPacket(inBuf, inBuf.length);
                socket.receive(inPacket);
                String payload = new String(inBuf, 0, inPacket.getLength());
                System.out.println("From " + inPacket.getAddress() + " Payload : " + payload);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
