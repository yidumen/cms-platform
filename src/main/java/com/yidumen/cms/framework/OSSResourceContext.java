package com.yidumen.cms.framework;

import org.springframework.core.io.Resource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 *
 * @author 蔡迪旻
 */
public final class OSSResourceContext extends AnnotationConfigWebApplicationContext {

    @Override
    public Resource getResource(String location) {
        if (location.startsWith("oss://")) {
            return null;//todo OSS Resource
        } else {
            return super.getResource(location);
        }
    }

}
