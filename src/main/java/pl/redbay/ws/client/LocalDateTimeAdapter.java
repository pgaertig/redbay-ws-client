package pl.redbay.ws.client;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm:ss");
    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(
                v.replaceFirst(" ", "T").replace("Z", " "),
                formatter);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        if(v == null) {
            return null;
        } else {
            return v.format(formatter);
        }
    }
}
