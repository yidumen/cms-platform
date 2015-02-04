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
        List<Record> extInfos = Db.find("select * from VideoInfo where video_id = ?", this.get("id"));
        return this.put("extInfo", extInfos);
    }
    
    public void updateWithRelate() {
        this.update();
        List<Record> extInfos = this.get("extInfo");
        for (Record extInfo : extInfos) {
            Db.update("VideoInfo", extInfo);
        }
    }
}
