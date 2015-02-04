package com.yidumen.cms.service;

import com.yidumen.cms.service.impl.TagServiceImpl;
import com.yidumen.cms.service.impl.UserServiceImpl;
import com.yidumen.cms.service.impl.VideoServiceImpl;

/**
 *
 * @author 蔡迪旻
 */
public final class ServiceFactory {

    private static final VideoService VIDEO_SERVICE = new VideoServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final TagService TAG_SERVICE = new TagServiceImpl();

    public static VideoService generateVideoService() {
        return VIDEO_SERVICE;
    }

    public static UserService generateUserService() {
        return USER_SERVICE;
    }

    public static TagService generateTagService() {
        return TAG_SERVICE;
    }
}
