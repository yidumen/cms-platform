package com.yidumen.cms.service;

import com.yidumen.cms.service.impl.*;

/**
 * @author 蔡迪旻
 */
public final class ServiceFactory {

    private static final VideoService VIDEO_SERVICE = new VideoServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final TagService TAG_SERVICE = new TagServiceImpl();
    private static final GoodsService GOODS_SERVICE = new GoodsServiceImpl();
    private static final RecordingService SOURCE_VIDEO_SERVICE = new RecordingServiceImpl();

    public static VideoService generateVideoService() {
        return VIDEO_SERVICE;
    }

    public static UserService generateUserService() {
        return USER_SERVICE;
    }

    public static TagService generateTagService() {
        return TAG_SERVICE;
    }

    public static GoodsService generateGoodsService() {
        return GOODS_SERVICE;
    }

    public static RecordingService generateRecordingService() {
        return SOURCE_VIDEO_SERVICE;
    }
}
