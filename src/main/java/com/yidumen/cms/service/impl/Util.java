package com.yidumen.cms.service.impl;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.jfinal.kit.PropKit;
import com.yidumen.cms.service.exception.IllDataException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 *
 * @author 蔡迪旻
 */
public final class Util {

    private final static OSSClient client = new OSSClient(PropKit.get("oss.ydm.endpoint"),
            PropKit.get("oss.ydm.accessKeyId"),
            PropKit.get("oss.ydm.accessKeySecret"));
    private final static String bucketName = PropKit.get("oss.ydm.bucketName");

    public static boolean isOSSFileExist(String key) throws ClientException {
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
