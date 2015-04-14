package com.yidumen.cms.model;

/**
 * @author 蔡迪旻
 *         2015年03月11日
 */
public class Recording extends BaseModel<Recording> {

    public static final Recording dao = new Recording();

    public Recording() {
        super("recording");
    }
}
