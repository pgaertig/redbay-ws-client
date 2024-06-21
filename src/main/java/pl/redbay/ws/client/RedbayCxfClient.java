package pl.redbay.ws.client;

import org.apache.cxf.feature.transform.XSLTFeature;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.staxutils.StaxUtils;
import org.glassfish.jaxb.runtime.IDResolver;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

public class RedbayCxfClient {

    public final GizaAPIPortType gizaAPIPortType;


    public RedbayCxfClient(String wsdlUrl) {
        this(wsdlUrl, null, false);
    }

    public RedbayCxfClient(String wsdlUrl, boolean logging) {
        this(wsdlUrl, null, logging);
    }

    public RedbayCxfClient(String wsdlUrl, String serviceAddress, boolean logging) {
        System.setProperty(StaxUtils.MAX_ELEMENT_DEPTH, "1000"); //TODO remove once Categories are fixed
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        if(wsdlUrl != null && serviceAddress == null) {
            serviceAddress = wsdlUrl.replace("/server.wsdl","/pl/giza/api/server");
        }
        if(serviceAddress != null) {
            factory.setAddress(serviceAddress);
        }

        factory.setServiceName(new QName("urn:GizaAPI", "GizaAPI"));
        factory.setEndpointName(new QName("urn:GizaAPI", "GizaAPIPort"));
        factory.setServiceClass(GizaAPIPortType.class);

        XSLTFeature xsltFeature = new XSLTFeature();
        xsltFeature.setInXSLTPath("pl/redbay/ws/client/xsi-type-removal.xsl");
        factory.setFeatures(List.of(xsltFeature));

        JAXBDataBinding jaxbDataBinding= new JAXBDataBinding();
        jaxbDataBinding.setUnmarshallerProperties(Map.of(IDResolver.class.getName(), new SoapEncIDResolver()));
        factory.setDataBinding(jaxbDataBinding);

        gizaAPIPortType = factory.create(GizaAPIPortType.class);
    }

    public GizaAPIPortType getAPI() {
        return gizaAPIPortType;
    }

}
