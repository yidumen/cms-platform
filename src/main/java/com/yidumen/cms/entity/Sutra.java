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
public class Sutra implements Serializable {

    private Long id;

    /**
     * 章节标识
     */
    private String partIdentifier;

    /**
     * 标题
     */
    private String title;

    /**
     * 左值
     *
     * @see http://blog.csdn.net/MONKEY_D_MENG/article/details/6647488
     */
    private Long leftValue;

    /**
     * 右值
     *
     * @see http://blog.csdn.net/MONKEY_D_MENG/article/details/6647488
     */
    private Long rightValue;

    private List<Tag> tags;

    /**
     * 佛经内容
     */
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

    public Long getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(Long leftValue) {
        this.leftValue = leftValue;
    }

    public Long getRightValue() {
        return rightValue;
    }

    public void setRightValue(Long rightValue) {
        this.rightValue = rightValue;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
