package com.yidumen.cms.entity;

import com.yidumen.cms.TagType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 蔡迪旻 <yidumen.com>
 */
@Entity
@Table(name = "web_tag")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "tag_name", length = 30, nullable = false)
    private String tagname;

    @Column(name = "hits")
    private Integer hits;

    @ManyToMany(mappedBy = "tags")
    private List<Video> videos;

    @ManyToMany(mappedBy = "tags")
    private List<Sutra> sutras;

    @Enumerated(EnumType.ORDINAL)
    private TagType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Sutra> getSutras() {
        return sutras;
    }

    public void setSutras(List<Sutra> sutras) {
        this.sutras = sutras;
    }


    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

}
