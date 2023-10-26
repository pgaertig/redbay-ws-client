package pl.redbay.ws.client;

import jakarta.xml.bind.annotation.*;

import java.lang.reflect.Field;

/**
 * Super-class to catch obsolete id/href SOAP 1.1 Encoded object referencing produced by PHP SOAP Server implementation
 * https://www.php.net/manual/en/class.soapserver.php
 * All methods kept as private - JAXB sees them however it also requires getters.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapEncEntity {

    @XmlAttribute(name = "id")
    @XmlID
    private void setEncId(String id){}
    private String getEncId(){ return null; }

    @XmlAttribute(name = "href")
    @XmlIDREF
    private void setEncHrefObj(SoapEncEntity encHrefObj) {
        copyFields(encHrefObj, this);
    }
    private SoapEncEntity getEncHrefObj() { return null; }

    private static void copyFields(SoapEncEntity source, SoapEncEntity target) {
        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(source);
                field.set(target, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

