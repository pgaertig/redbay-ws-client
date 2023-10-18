package pl.redbay.ws.client;

import com.github.jknack.handlebars.internal.Files;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.feature.transform.XSLTInInterceptor;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiRefFlattenTest {

    @Test
    public void testMultiRefFlattening() throws IOException {
        XSLTInInterceptor interceptor = new XSLTInInterceptor("pl/redbay/ws/client/multi-ref-flatten.xsl");
        SoapMessage message = new SoapMessage(Soap11.getInstance());
        message.setContent(InputStream.class, this.getClass().getResourceAsStream("/multi-ref-in.xml"));
        interceptor.handleMessage(message);
        InputStream is = message.getContent(InputStream.class);
        String result = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));

        String expected = Files.read(this.getClass().getResourceAsStream("/multi-ref-flattened.xml"), Charset.defaultCharset());
        assertEquals(expected, result);
    }
}
