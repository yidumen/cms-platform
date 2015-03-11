package com.yidumen.cms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蔡迪旻
 */
public final class Video extends BaseModel<Video> {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static final Video dao = new Video();

    public Video() {
        super("Video");
    }

    public List<Video> getNew(int limit) {
        return this.find("SELECT * FROM Video ORDER BY pubDate DESC LIMIT ?", limit);
    }

    public Video extInfo() {
        final List<Record> extInfos = Db.find("SELECT * FROM VideoInfo WHERE video_id = ? ORDER BY resolution", this.get("id"));
        return this.put("extInfo", extInfos);
    }

    public Video addTags() {
        return this.put("tags", queryTags());
    }

    public void updateWithRelate() {
        this.update();
        LOG.debug("{}", this.get("extInfo"));
        final List<Record> extInfos = this.get("extInfo");
        if (extInfos != null) {
            for (Record extInfo : extInfos) {
                if (extInfo.get("id") == null) {
                    Db.save("videoinfo", extInfo);
                } else {
                    Db.update("VideoInfo", new Record().setColumns(extInfo));
                }
            }
        }
        /*
        Tag的更新（多对多关联的更新）是难点，需要区别2种不同的tag：
        1. 新增；2. 已删除；
         */
        final List<Tag> tags = this.get("tags");
        if (tags != null) {
            final List<Record> tempRecords = new ArrayList<>();
            final List<Tag> tempTags = new ArrayList<>();
            //1.筛选出不需要更新的记录，排除掉
            final List<Record> videoTags = Db.find("SELECT * FROM Tag_Video WHERE videos_id = ?", this.get("id"));
            for (Record videoTag : videoTags) {
                LOG.debug("videoTag:{}->{}", videoTag.toJson(), videoTag.get("tags_id").getClass().getName());
                for (Tag tag : tags) {
                    LOG.debug("tag:{}->{}", tag.toJson(), tag.get("id").getClass().getName());
                    if (tag.getInt("id").longValue() == videoTag.getLong("tags_id")) {
                        LOG.debug("add  videoTag to remove : {}", videoTag.toJson());
                        tempRecords.add(videoTag);
                        LOG.debug("add tag to remove : {}", tag.toJson());
                        tempTags.add(tag);
                    }
                }
            }
            tags.removeAll(tempTags);
            videoTags.removeAll(tempRecords);
            //2. 现在videoTags中如果还有的记录就是已删除的，tags中如果还有记录就是新增的
            for (Tag tag : tags) {
                LOG.debug("tag on saving : {}", tag.toJson());
                Db.save("Tag_Video", new Record().set("videos_id", this.get("id")).set("tags_id", tag.get("id")));
            }
            for (Record videoTag : videoTags) {
                Db.update("delete from `Tag_Video` where `videos_id` = ? and `tags_id` = ?", videoTag.get("videos_id"), videoTag.get("tags_id"));
            }
        }

    }

    public void saveWithRelate() {
        //1.保存video，拿到video id
        this.save();
        final Long videoId = Db.findFirst("SELECT last_insert_id() AS id").getNumber("id").longValue();
        //2.保存extInfo
        final List<Record> extInfos = this.get("extInfo");
        if (extInfos != null) {
            for (Record extInfo : extInfos) {
                extInfo.set("video_id", videoId);
                if (extInfo.get("id") == null) {
                    Db.save("videoinfo", extInfo);
                } else {
                    Db.update("VideoInfo", new Record().setColumns(extInfo));
                }
            }
        }
        //3.保存tag关联
        final List<Tag> tags = this.get("tags");
        if (tags != null) {
            for (Tag tag : tags) {
                final Record record = new Record().set("tags_id", tag.get("id")).set("videos_id", videoId);
                Db.save("tag_video", record);
            }
        }
    }
    
    private List<Tag> queryTags() {
        return Tag.dao.find("SELECT Tag.* FROM Tag INNER JOIN Tag_Video ON Tag_Video.tags_id = Tag.id INNER JOIN Video ON Tag_Video.videos_id = Video.id WHERE Video.id = ?", this.get("id"));
    }

    public int findSort() {
        return findFirst("SELECT sort FROM video ORDER BY pubDate DESC LIMIT 1").getNumber("sort").intValue();
    }
}
