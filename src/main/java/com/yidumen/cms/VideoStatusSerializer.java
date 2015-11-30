package com.yidumen.cms;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yidumen.dao.constant.VideoStatus;
import java.io.IOException;

/**
 *
 * @author 蔡迪旻
 */
public class VideoStatusSerializer extends JsonSerializer<VideoStatus> {

    @Override
    public void serialize(VideoStatus t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString(t.getDescript());
    }

}
