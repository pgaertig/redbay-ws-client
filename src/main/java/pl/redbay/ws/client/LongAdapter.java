package pl.redbay.ws.client;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.lang.Long;

public class LongAdapter extends XmlAdapter<String, Long> {
    @Override
    public Long unmarshal(String value) {
        return Long.valueOf(value);
    }

    @Override
    public String marshal(Long value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
