package com.yidumen.cms;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/**
 *
 * @author 蔡迪旻
 */
public class DurationDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        final String target = jp.getText();
        long duration = Long.parseLong(target.substring(0, target.indexOf(":"))) * 60 * 1000;
        duration += Long.parseLong(target.substring(target.indexOf(":") + 1, target.indexOf("."))) * 1000;
        duration += Long.parseLong(target.substring(target.indexOf(".") + 1));
        return duration;
    }

}
