package com.yidumen.cms.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
public final class Video extends BaseModel<Video> {
    public static final Video dao = new Video();

    public Video() {
        super("Video");
    }
    
    public Video extInfo() {
        final List<Record> extInfos = Db.find("select * from VideoInfo where video_id = ?", this.get("id"));
        return this.put("extInfo", extInfos);
    }
    
    public Video addTags() {
        final List<Tag> tags = Tag.dao.find("select Tag.*, Tag_Video.*, Video.id from Tag inner join Tag_Video on Tag_Video.tags_id = Tag.id inner join Video on Tag_Video.videos_id = Video.id where Video.id = ?", this.get("id"));
        return this.put("tags", tags);
    }
    
    public void updateWithRelate() {
        this.update();
        List<Record> extInfos = this.get("extInfo");
        for (Record extInfo : extInfos) {
            Db.update("VideoInfo", extInfo);
        }
    }
}
