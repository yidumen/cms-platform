package com.yidumen.cms.constant;

/**
 *
 * @author 蔡迪旻<yidumen.com>
 */
public enum AccountStatus {

    NORMAL("正常"),
    NOT_VERIFY("待验证"),
    DESTROY("注销");

    private final String descript;

    private AccountStatus(final String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }

}
