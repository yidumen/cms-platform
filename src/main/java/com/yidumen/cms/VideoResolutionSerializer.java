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
public class VideoResolutionSerializer extends JsonSerializer<VideoResolution> {

    @Override
    public void serialize(VideoResolution t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString(t.getDescript());
    }
    
}
