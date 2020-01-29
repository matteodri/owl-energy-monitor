package com.matteodri.owlenergymonitor.services;

import java.net.InetAddress;

public interface OwlMessageProcessor {

    void process(InetAddress fromAddress, String message);
}
