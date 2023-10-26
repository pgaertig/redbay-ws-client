package pl.redbay.ws.client;

import jakarta.xml.bind.ValidationEventHandler;
import org.glassfish.jaxb.runtime.IDResolver;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Thread safe
 */
class SoapEncodedIDResolver extends IDResolver {

    private ThreadLocal<HashMap<String, Object>> idMap = ThreadLocal.withInitial(HashMap::new);

    /**
     * Clears reusable cache before document is parsed.
     * @param eventHandler
     *      Any errors found during the unmarshalling should be reported to this object.
     * @throws SAXException
     */
    @Override
    public void startDocument(ValidationEventHandler eventHandler) throws SAXException {
        idMap.get().clear();
    }

    /**
     * Dereferences cache elements
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        idMap.get().clear();
    }

    /**
     * Memoize id="refX" entry to be later referenced by href="#refX".
     *
     * @param id
     *      The ID value found in the document being unmarshalled.

     * @param obj
     *      The object being unmarshalled which is going to own the ID.
     *      Always non-null.
     */
    @Override
    public void bind(String id, Object obj) {
        idMap.get().put("#".concat(id), obj);
    }

    @Override
    public Callable resolve(final String id, Class targetType) {
        return () -> idMap.get().get(id);
    }
}
