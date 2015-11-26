package com.yidumen.cms.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author 蔡迪旻<yidumen.com>
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Sutra.findNodes", query = "SELECT s FROM Sutra AS s WHERE s.leftValue > :leftValue AND s.rightValue < :rightValue ORDER BY s.leftValue"),
    @NamedQuery(name = "Sutra.findByLeftValue", query = "SELECT s FROM Sutra AS s WHERE s.leftValue = ?1"),
    @NamedQuery(name = "Sutra.findByRightValue", query = "SELECT s FROM Sutra AS s WHERE s.rightValue = ?1"),
    @NamedQuery(name = "Sutra.findParents", query = "SELECT s FROM Sutra AS s WHERE s.leftValue < ?1 AND s.rightValue > ?2 ORDER BY s.leftValue DESC")
})
public class Sutra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 章节标识
     */
    @Column(length = 16)
    private String partIdentifier;

    /**
     * 标题
     */
    @Column(length = 50, nullable = false)
    private String title;

    /**
     * 左值
     *
     * @see http://blog.csdn.net/MONKEY_D_MENG/article/details/6647488
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private long leftValue;

    /**
     * 右值
     *
     * @see http://blog.csdn.net/MONKEY_D_MENG/article/details/6647488
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private long rightValue;

    @ManyToMany(mappedBy = "sutras")
    private List<Tag> tags;

    @OneToOne
    private Video video;

    /**
     * 佛经内容
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartIdentifier() {
        return partIdentifier;
    }

    public void setPartIdentifier(String partIdentifier) {
        this.partIdentifier = partIdentifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(long leftValue) {
        this.leftValue = leftValue;
    }

    public long getRightValue() {
        return rightValue;
    }

    public void setRightValue(long rightValue) {
        this.rightValue = rightValue;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
