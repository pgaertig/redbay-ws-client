package pl.redbay.ws.client;

import jakarta.xml.ws.BindingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * @deprecated Use {@link RedbayCxfClient} which handles id/href references correctly
 */
@Deprecated
public class RedbayClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedbayClient.class);

    private final GizaAPIPortType gizaAPIPortType;

    public RedbayClient(String apiUrl) {
        GizaAPI service;
        if(apiUrl == null) {
            service = new GizaAPI();
        } else {
            try {
                service = new GizaAPI(new URI(apiUrl).toURL());
            } catch (MalformedURLException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        gizaAPIPortType = service.getGizaAPIPort();
    }

    /**
     * Test only constructor using localhost
     */
    RedbayClient() {
        this(null);
    }

    public GizaAPIPortType getAPI() {
        return gizaAPIPortType;
    }

    public void setAddressLocation(String url) {
        BindingProvider bp = (BindingProvider) gizaAPIPortType;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
    }

    public void enableSoapLogging() {
        if(gizaAPIPortType instanceof BindingProvider) {
            BindingProvider bp = (BindingProvider) gizaAPIPortType;
            LOGGER.info("Installing logger");
            var hc = bp.getBinding().getHandlerChain();
            hc.add(new RedbayLoggingHandler());
            bp.getBinding().setHandlerChain(hc);
        }
    }
}
