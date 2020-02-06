package com.matteodri.owlenergymonitor.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.matteodri.owlenergymonitor.model.electricity.Channel;
import com.matteodri.owlenergymonitor.model.electricity.Electricity;
import com.matteodri.owlenergymonitor.model.electricity.ElectricityCurrent;
import com.matteodri.owlenergymonitor.model.electricity.ElectricityDay;
import com.matteodri.owlenergymonitor.model.solar.Exported;
import com.matteodri.owlenergymonitor.model.solar.Exporting;
import com.matteodri.owlenergymonitor.model.solar.Generated;
import com.matteodri.owlenergymonitor.model.solar.Generating;
import com.matteodri.owlenergymonitor.model.solar.Solar;

/**
 * Test class for {@link MeasurementsUnmarshaller}.
 */
public class MeasurementsUnmarshallerTest {

    public static final double FLOATING_COMPARISON_DELTA = 0.001d;

    private MeasurementsUnmarshaller target;

    @BeforeEach
    public void setUp() {
        target = new MeasurementsUnmarshaller();
    }

    @Test
    @DisplayName("Unmarshal an electricity message")
    public void testUnmarshalElectricityXml() {
        Electricity electricity = target.unmarshalElectricityXml("<electricity id='44371914D92A' ver='2.0'>"
                + "  <timestamp>1580421382</timestamp>" + "  <signal rssi='-33' lqi='4'/>" + "  <battery level='100%'/>"
                + "  <channels>" + "    <chan id='0'>" + "      <curr units='w'>333.00</curr>"
                + "      <day units='wh'>9608.41</day></chan>" + "  </channels>\n" + "</electricity>");

        assertNotNull(electricity);
        assertNotNull(electricity.getBattery());
        assertNotNull(electricity.getTimestamp());
        assertNotNull(electricity.getChannels());
        assertEquals(1580421382, electricity.getTimestamp().longValue());
        assertEquals("100%", electricity.getBattery().getLevel());

        Channel channel = electricity.getChannels().get(0);
        assertEquals("0", channel.getId());

        ElectricityCurrent electricityCurrent = channel.getElectricityCurrent();
        ElectricityDay electricityDay = channel.getElectricityDay();
        assertEquals("w", electricityCurrent.getUnits());
        assertEquals(333d, electricityCurrent.getValue().doubleValue(), FLOATING_COMPARISON_DELTA);
        assertEquals("wh", electricityDay.getUnits());
        assertEquals(9608.41d, electricityDay.getValue().doubleValue(), FLOATING_COMPARISON_DELTA);
    }

    @Test
    @DisplayName("Unmarshal an electricity malformed message")
    public void testUnmarshalElectricityMalformedXml() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            target.unmarshalElectricityXml("< this is not XML>--..");
        });

        assertEquals("Error unmarshalling XML string", exception.getMessage());
    }

    @Test
    @DisplayName("Unmarshal a solar message")
    public void testUnmarshalSolarXml() {
        Solar solar =
                target.unmarshalSolarXml("<solar id=\"44371914D92A\">\n" + "   <timestamp>1580417543</timestamp>\n"
                        + "   <current>\n" + "      <generating units=\"w\">250.00</generating>\n"
                        + "      <exporting units=\"w\">17.50</exporting>\n" + "   </current>\n" + "   <day>\n"
                        + "      <generated units=\"wh\">11437.27</generated>\n"
                        + "      <exported units=\"wh\">7552.66</exported>\n" + "   </day>\n" + "</solar>");

        assertNotNull(solar);
        assertNotNull(solar.getTimestamp());
        assertNotNull(solar.getSolarCurrent());
        assertNotNull(solar.getSolarDay());
        assertEquals(1580417543, solar.getTimestamp().longValue());

        Generating generating = solar.getSolarCurrent().getGenerating();
        Exporting exporting = solar.getSolarCurrent().getExporting();
        assertEquals("w", generating.getUnits());
        assertEquals(250d, generating.getValue().doubleValue(), FLOATING_COMPARISON_DELTA);
        assertEquals("w", exporting.getUnits());
        assertEquals(17.5d, exporting.getValue().doubleValue(), FLOATING_COMPARISON_DELTA);

        Generated generated = solar.getSolarDay().getGenerated();
        Exported exported = solar.getSolarDay().getExported();
        assertEquals("wh", generated.getUnits());
        assertEquals(11437.27d, generated.getValue().doubleValue(), FLOATING_COMPARISON_DELTA);
        assertEquals("wh", exported.getUnits());
        assertEquals(7552.66d, exported.getValue().doubleValue(), FLOATING_COMPARISON_DELTA);
    }

    @Test
    @DisplayName("Unmarshal a solar malformed message")
    public void testUnmarshalSolarMalformedXml() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            target.unmarshalSolarXml("< this is not XML>--..");
        });

        assertEquals("Error unmarshalling XML string", exception.getMessage());
    }
}
