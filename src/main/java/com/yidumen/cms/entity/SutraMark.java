package com.yidumen.cms.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 对某些特定佛经节点进行标记,内部使用,用于加快查询速度.
 * 也可用来调用特定的佛经，也起到与旧数据兼容的作用。
 *
 * @author 蔡迪旻<yidumen.com>
 */
@Entity
@Table(name = "web_sutra_mark")
public class SutraMark implements Serializable {

    /**
     * 标识符,内部约定一个特殊的数字标识,用于快速找到对应的佛经
     */
    @Id
    @Column(name = "id")
    private long id;

    /**
     * 对应的佛经节点
     */
    @OneToOne
    @JoinColumn(name = "sutra_id")
    private Sutra node;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Sutra getNode() {
        return node;
    }

    public void setNode(Sutra node) {
        this.node = node;
    }

}
