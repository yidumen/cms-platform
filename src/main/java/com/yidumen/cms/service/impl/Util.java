package com.yidumen.cms.service.impl;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.OSSObject;
import com.jfinal.kit.PropKit;

/**
 * @author 蔡迪旻
 */
public final class Util {

    public static boolean isOSSFileExist(String key) throws ClientException {
        final OSSClient client = new OSSClient(PropKit.get("oss.ydm.endpoint"),
                PropKit.get("oss.ydm.accessKeyId"),
                PropKit.get("oss.ydm.accessKeySecret"));
        final String bucketName = PropKit.get("oss.ydm.bucketName");
        try {
            final OSSObject object = client.getObject(bucketName, key);
            if (object != null) {
                return true;
            }
        } catch (OSSException e) {
            return false;
        } catch (ClientException e) {
            throw e;
        }
        return false;
    }

}
