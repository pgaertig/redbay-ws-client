package pl.redbay.ws.client;

import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

class RedbayLoggingHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedbayClient.class);

    @Override
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        logMessage(context);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logMessage(context);
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    private void logMessage(SOAPMessageContext context) {
        if (LOGGER.isDebugEnabled()) {
            try {
                    Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
                    String direction = outboundProperty ? "request: " : "response: ";
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    context.getMessage().writeTo(baos);
                    String messageContent = new String(baos.toByteArray(), StandardCharsets.UTF_8);
                    LOGGER.debug(direction + messageContent);
            } catch (Exception e) {
                LOGGER.error("Exception in handler: {}", e.getMessage(), e);
            }
        }
    }
}

