package pl.redbay.ws.client;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.eclipse.persistence.internal.oxm.Unmarshaller;

@XmlAccessorType(XmlAccessType.FIELD)
public class SoapEncEntity {

    @XmlAttribute(name = "id")
    @XmlID
    private String encId;

    @XmlAttribute(name = "href")
    private String encHref;

    @XmlAttribute(name = "href")
    @XmlJavaTypeAdapter(MultiRefAdapter.class)
    @XmlIDREF
    private Object encHrefObj;

    String getEncId() {
        return encId;
    }

    String getEncHref() {
        return encHref;
    }

    public boolean hasEncHref() {
        return encHref != null && !encHref.isEmpty();
    }

    public boolean hasEncId() {
        return encId != null && !encId.isEmpty();
    }

    public void setEncId(String encId) {
        this.encId = encId;
    }

    public void setEncHref(String encHref) {
        this.encHref = encHref;
    }
}

