package com.yidumen.cms;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.yidumen.dao.constant.VideoStatus;
import java.io.IOException;

/**
 *
 * @author 蔡迪旻
 */
public class VideoStatusDeSerializer extends JsonDeserializer<VideoStatus> {

    @Override
    public VideoStatus deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return VideoStatus.getByDescript(jp.getText());
    }
    
}
