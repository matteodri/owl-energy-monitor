package com.matteodri.owlenergymonitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

import com.matteodri.owlenergymonitor.services.MetricsUtils;

/**
 * Service test to verify the functionality of the application bringing up the Spring context and testing the API.
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
        classes = {OwlEnergyMonitorApplication.class, MainConfig.class})
@TestPropertySource(properties = {"multicast-address:224.0.0.10", "multicast-port = 10000", "server.port = 6999",
        "multicast-listener-delay = 10"})

public class ConsoleApplicationServiceTest {

    private static final String PROMETHEUS_ENDPOINT = "http://localhost:6999/actuator/prometheus";
    private static final double E_CONSUMPTION_CURRENT = 158.2;
    private static final double E_CONSUMPTION_TODAY = 7654.39;
    private static final double E_BATTERY_LEVEL_CURRENT = 95;
    private static final double E_GENERATED_CURRENT = 1545.34;
    private static final double E_GENERATED_TODAY = 8790.56;
    private static final double E_EXPORTED_CURRENT = 340.12;
    private static final double E_EXPORTED_TODAY = 1378.23;
    private static final double TOLERANCE = 0.001;

    private OkHttpClient httpClient;

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient.Builder().connectTimeout(2_000L, TimeUnit.MILLISECONDS)
                .writeTimeout(2_000L, TimeUnit.MILLISECONDS).readTimeout(2_000, TimeUnit.MILLISECONDS).build();
    }

    @Test
    @DisplayName("Electricity message processed")
    public void electricityMessageProcessed() throws Exception {
        // Console application is up
        String electircityPayload =
                "<electricity id='44371914D92A' ver='2.0'><timestamp>1581890782</timestamp><signal rssi='-33' lqi='0'/><battery level='"
                        + E_BATTERY_LEVEL_CURRENT + "%'/><channels><chan id='0'><curr units='w'>"
                        + E_CONSUMPTION_CURRENT + "</curr><day units='wh'>" + E_CONSUMPTION_TODAY
                        + "</day></chan><chan id='1'><curr units='w'>0.00</curr><day units='wh'>10476.92</day></chan><chan id='2'><curr units='w'>0.00</curr><day units='wh'>0.00</day></chan><chan id='3'><curr units='w'>0.00</curr><day units='wh'>0.00</day></chan><chan id='4'><curr units='w'>0.00</curr><day units='wh'>0.00</day></chan><chan id='5'><curr units='w'>0.00</curr><day units='wh'>0.00</day></chan></channels><property><current><watts>1145.00</watts><cost>28.63</cost></current><day><wh>14422.39</wh><cost>238.98</cost></day><tariff time='1581894382'><start>1581811200</start><curr_price>0.25</curr_price><block_limit>4294967295</block_limit><block_usage>7619</block_usage></tariff></property></electricity>";
        String solarPayload = "<solar id='44371914D92A'><timestamp>30632</timestamp><current><generating units='w'>"
                + E_GENERATED_CURRENT + "</generating><exporting units='w'>" + E_EXPORTED_CURRENT
                + "</exporting></current><day><generated units='wh'>" + E_GENERATED_TODAY
                + "</generated><exported units='wh'>" + E_EXPORTED_TODAY + "</exported></day></solar>";

        // publish multicast messages
        publishMulticastMessage("224.0.0.10", 10_000, electircityPayload);
        publishMulticastMessage("224.0.0.10", 10_000, solarPayload);

        // thread to wait for onResponse completion, if times out then fail
        CompletableFuture<Response> responseFuture = new CompletableFuture<>();

        // verify values from prometheus metrics match values in multicast messages just sent
        get(PROMETHEUS_ENDPOINT, new Callback() {
            public void onResponse(Call call, Response response) {
                List<String> responseLines = new ArrayList<>();
                try {
                    responseLines = response.body().string().lines().collect(Collectors.toList());
                } catch (IOException e) {
                    responseFuture.completeExceptionally(e);
                }
                verifyElectricityStats(responseLines);
                verifySolarStats(responseLines);
                responseFuture.complete(response);
            }

            public void onFailure(Call call, IOException e) {
                responseFuture.completeExceptionally(e);
            }
        });

        // wait on response reception and fail if needed
        try {
            responseFuture.get(3, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            fail("Request to get prometheus stats from URL " + PROMETHEUS_ENDPOINT + " failed");
        } catch (TimeoutException e) {
            fail("Request to get prometheus stats from URL " + PROMETHEUS_ENDPOINT + " timed out");
        }
    }

    private void publishMulticastMessage(String multicastAddress, int multicastPort, String payload)
            throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(multicastAddress);
        byte[] buffer = payload.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, multicastPort);
        socket.send(packet);
        socket.close();

    }

    private void get(String url, Callback callback) {
        Request request = new Request.Builder().url(url).get().build();
        httpClient.newCall(request).enqueue(callback);
    }

    private void verifyElectricityStats(List<String> responseLines) {
        int metricsFoundCount = 0;

        for (String line : responseLines) {
            if (line.startsWith(MetricsUtils.ELECTRICITY_CONSUMPTION_CURRENT)) {
                assertEquals(E_CONSUMPTION_CURRENT, Double.valueOf(line.split(" ")[1]), TOLERANCE);
                metricsFoundCount++;
            } else if (line.startsWith(MetricsUtils.ELECTRICITY_CONSUMPTION_TODAY)) {
                assertEquals(E_CONSUMPTION_TODAY, Double.valueOf(line.split(" ")[1]), TOLERANCE);
                metricsFoundCount++;
            } else if (line.startsWith(MetricsUtils.BATTERY_LEVEL_CURRENT)) {
                assertEquals(E_BATTERY_LEVEL_CURRENT, Double.valueOf(line.split(" ")[1]), TOLERANCE);
                metricsFoundCount++;
            }
        }
        assertEquals(3, metricsFoundCount);
    }

    private void verifySolarStats(List<String> responseLines) {
        int metricsFoundCount = 0;

        for (String line : responseLines) {
            if (line.startsWith(MetricsUtils.ELECTRICITY_GENERATED_CURRENT)) {
                assertEquals(E_GENERATED_CURRENT, Double.valueOf(line.split(" ")[1]), TOLERANCE);
                metricsFoundCount++;
            } else if (line.startsWith(MetricsUtils.ELECTRICITY_GENERATED_TODAY)) {
                assertEquals(E_GENERATED_TODAY, Double.valueOf(line.split(" ")[1]), TOLERANCE);
                metricsFoundCount++;
            } else if (line.startsWith(MetricsUtils.ELECTRICITY_EXPORTED_CURRENT)) {
                assertEquals(E_EXPORTED_CURRENT, Double.valueOf(line.split(" ")[1]), TOLERANCE);
                metricsFoundCount++;
            } else if (line.startsWith(MetricsUtils.ELECTRICITY_EXPORTED_TODAY)) {
                assertEquals(E_EXPORTED_TODAY, Double.valueOf(line.split(" ")[1]), TOLERANCE);
                metricsFoundCount++;
            }
        }
        assertEquals(4, metricsFoundCount);
    }

}
