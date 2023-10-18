package pl.redbay.ws.client;

import org.apache.cxf.feature.transform.XSLTFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import javax.xml.namespace.QName;
import java.util.List;

public class RedbayCxfClient {

    public final GizaAPIPortType gizaAPIPortType;


    public RedbayCxfClient(String wsdlUrl) {
        this(wsdlUrl, null, false);
    }

    public RedbayCxfClient(String wsdlUrl, boolean logging) {
        this(wsdlUrl, null, logging);
    }

    public RedbayCxfClient(String wsdlUrl, String serviceAddress, boolean logging) {
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
        xsltFeature.setInXSLTPath("pl/redbay/ws/client/multi-ref-flatten.xsl");
        factory.setFeatures(List.of(xsltFeature));

/*        factory.setDataBinding(new JAXBDataBinding());
        JAXBDataBinding dataBinding = (JAXBDataBinding) factory.getDataBinding();
        dataBinding.setUnmarshallerListener(new Unmarshaller.Listener() {
            @Override
            public void beforeUnmarshal(Object target, Object parent) {
                target.equals(parent);

            }

            @Override
            public void afterUnmarshal(Object target, Object parent) {
                target.equals(parent);
            }
        });
*/

        gizaAPIPortType = (GizaAPIPortType) factory.create();
    }

    public GizaAPIPortType getAPI() {
        return gizaAPIPortType;
    }

}
