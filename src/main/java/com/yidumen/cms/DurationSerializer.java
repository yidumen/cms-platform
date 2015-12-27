package com.yidumen.cms;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 *
 * @author 蔡迪旻
 */
public class DurationSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        long minute = t / (1000 * 60);
        long second = t / 1000 - minute * 60;
        long mesc = t - minute * 60 * 1000 - second * 1000;
        jg.writeString(String.format("%02d:%02d.%03d", minute, second, mesc));
    }

}
