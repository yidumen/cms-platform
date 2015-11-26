package com.yidumen.cms.entity;

import com.yidumen.dao.constant.TagType;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Tag.findByname", query = "SELECT t FROM Tag t WHERE t.tagname = :tagname"),
    @NamedQuery(name = "Tag.OrderByHints", query = "SELECT t FROM Tag t WHERE t.type = com.yidumen.dao.constant.TagType.CONTENT ORDER BY t.hits DESC")
})
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Basic(optional = false)
    @Column(length = 30, nullable = false)
    private String tagname;
    
    private int hits;

    @ManyToMany
    private Set<Video> videos;

    @ManyToMany
    private Set<Sutra> sutras;
    
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

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    public Set<Sutra> getSutras() {
        return sutras;
    }

    public void setSutras(Set<Sutra> sutras) {
        this.sutras = sutras;
    }


    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

}
