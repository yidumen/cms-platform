package com.yidumen.cms.model;

import com.yidumen.cms.TagType;

import java.util.List;

/**
 *
 * @author 蔡迪旻
 */
public final class Tag extends BaseModel<Tag> {

    public static final Tag dao = new Tag();

    public Tag() {
        super("Tag");
    }

    public List<Tag> findByType(TagType type) {
        return this.find("select * from Tag where type = ?", type.ordinal());
    }
}
