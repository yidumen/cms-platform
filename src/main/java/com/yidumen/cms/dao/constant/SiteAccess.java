package com.yidumen.cms.dao.constant;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public enum SiteAccess {

    SINA_WEIBO("新浪微博", "1314587466", "", "sina.gif"),
    TENCENT_WEIBO("腾讯微博", "801322246", "8bba84a3152f29d9cf0df829525ff151", "qq.gif"),
    QQZONE("QQ空间", "801322246", "8bba84a3152f29d9cf0df829525ff151", "qzone.gif");

    private final String name;
    private final String accessKey;
    private final String secret;
    private final String picName;

    private SiteAccess(String name, String accessKey, String secret, String picName) {
        this.name = name;
        this.accessKey = accessKey;
        this.secret = secret;
        this.picName = picName;
    }

    public String getName() {
        return name;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecret() {
        return secret;
    }

    public String getPicName() {
        return picName;
    }
}
