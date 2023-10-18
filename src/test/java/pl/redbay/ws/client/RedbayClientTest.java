package pl.redbay.ws.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.redbay.ws.client.types.ProductCodeCollection;
import pl.redbay.ws.client.types.Ticket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;


//@ExtendWith(WireMockExtension.class)
public class RedbayClientTest {
    private final static String FOLDER = "src/test/resources/wiremock";
    private final static int PORT = 8085;

    private static WireMockServer wireMockServer;
    private GizaAPIPortType api;

    @BeforeAll
    public static void setupWireMockServer() {
        wireMockServer = new WireMockServer(WireMockConfiguration
                .wireMockConfig().withRootDirectory(FOLDER).port(PORT)
                .notifier(new ConsoleNotifier(true)));
        wireMockServer.start();
    }
    @BeforeEach
    public void setup() {

        RedbayCxfClient rbc = new RedbayCxfClient(
                String.format("http://localhost:%d/server.wsdl", PORT),
                true
                );
//        rbc.setAddressLocation(String.format("http://localhost:%d/pl/giza/api/server", PORT));
//        rbc.enableSoapLogging();
        api = rbc.getAPI();

        wireMockServer.resetAll();
        wireMockServer.stubFor(post(urlPathEqualTo("/pl/giza/api/server"))
                .withHeader("Content-Type", containing("text/xml"))
                .withHeader("SoapAction", containing("#createTicket_authorization"))
                .withRequestBody(equalToXml(requestBodyFile("createTicket-request.xml")))
                .willReturn(aResponse().withBodyFile("createTicket-response.xml")));
    }

    @Test
    public void testCreateTicket() {
        Ticket ticket = api.createTicket("REDBAYA-987654321", 123, "123456789");
        assertThat(ticket.getShopName(), is("ShoppingMall"));
    }

    @Test
    public void testGetProductCodeList() {
        wireMockServer.stubFor(post(urlPathEqualTo("/pl/giza/api/server"))
                .withHeader("Content-Type", containing("text/xml"))
                .withHeader("SoapAction", containing("#getProductCodeList_warehouse"))
                .withRequestBody(equalToXml(requestBodyFile("getProductCodeList-request.xml")))
                .willReturn(aResponse().withBodyFile("getProductCodeList-response.xml")));

        Ticket ticket = api.createTicket("REDBAYA-987654321", 123, "123456789");
        ProductCodeCollection productCodes = api.getProductCodeList(ticket);
        assertThat(productCodes, notNullValue());
        assertThat(productCodes.getItems().size(), is(equalTo(8)));
    }

    private String requestBodyFile(String name) {
        try {
            return Files.readString(Path.of(String.format("%s/__files/%s", FOLDER, name)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
