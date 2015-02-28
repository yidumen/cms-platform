package com.yidumen.cms.service.impl;

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

    public static HttpResponse httpRequest(final String url) throws IOException, IllDataException {
        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope("10.242.175.127", 3128), new UsernamePasswordCredentials("1336663694481251_default_57", "rad2yu5i2s"));
        final DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setCredentialsProvider(credsProvider);
        final HttpHost proxy = new HttpHost("10.242.175.127", 3128);
        httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        final HttpGet httpGet = new HttpGet(url);
        final HttpResponse response = httpClient.execute(httpGet);
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 404) {
            throw new IllDataException("视频未部署");
        }
        if (statusCode == 500) {
            throw new IllDataException("请检查文件服务器是否在线");
        }
        return response;
    }
}
