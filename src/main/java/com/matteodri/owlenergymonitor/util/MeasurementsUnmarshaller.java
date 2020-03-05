package com.matteodri.owlenergymonitor.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.matteodri.owlenergymonitor.model.electricity.Electricity;
import com.matteodri.owlenergymonitor.model.solar.Solar;
import com.matteodri.owlenergymonitor.services.OwlMessageProcessorImpl;

/**
 * Service responsible for unmarshalling the XML messages into measurement objects.
 */

@Component
public class MeasurementsUnmarshaller {

    private static final Logger logger = LogManager.getLogger(OwlMessageProcessorImpl.class);

    private Unmarshaller electricityUnmarshaller;
    private Unmarshaller solarUnmarshaller;

    public MeasurementsUnmarshaller() {
        try {
            JAXBContext electricityJaxbContext = JAXBContext.newInstance(Electricity.class);
            electricityUnmarshaller = electricityJaxbContext.createUnmarshaller();

            JAXBContext solarJaxbContext = JAXBContext.newInstance(Solar.class);
            solarUnmarshaller = solarJaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            logger.error("Error initialising JAXB unmarshaller", e);
        }
    }

    public Electricity unmarshalElectricityXml(String xmlString) {
        try {

            Electricity unmarshalledObject =
                    (Electricity) electricityUnmarshaller.unmarshal(new StringReader(xmlString));

            logger.debug("Unmarshalled object {}", unmarshalledObject);
            return unmarshalledObject;

        } catch (JAXBException e) {
            logger.error("Error unmarshalling XML string '{}'. Exception {}", xmlString, e);
            throw new IllegalArgumentException("Error unmarshalling XML string");
        }
    }

    public Solar unmarshalSolarXml(String xmlString) {
        try {

            Solar unmarshalledObject = (Solar) solarUnmarshaller.unmarshal(new StringReader(xmlString));

            logger.debug("Unmarshalled object {}", unmarshalledObject);
            return unmarshalledObject;

        } catch (JAXBException e) {
            logger.error("Error unmarshalling XML string '{}'", xmlString);
            throw new IllegalArgumentException("Error unmarshalling XML string");
        }
    }
}
