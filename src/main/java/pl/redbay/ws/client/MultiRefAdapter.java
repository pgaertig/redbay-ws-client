package pl.redbay.ws.client;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class MultiRefAdapter extends XmlAdapter<Object, Object> {
    @Override
    public Object unmarshal(Object v) throws Exception {
        return v;
    }

    @Override
    public Object marshal(Object v) throws Exception {
        return v;
    }
}
