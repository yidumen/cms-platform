package com.yidumen.cms.service.impl;

import com.yidumen.cms.service.exception.IllDataException;
import java.io.IOException;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author 蔡迪旻
 */
public class Util {

    public static CloseableHttpResponse httpRequest(final String url) throws IOException, IllDataException {
        final HttpHost proxy = new HttpHost("10.242.175.127", 3128);
        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials("1336663694481251_default_57", "rad2yu5i2s"));
        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        final HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(RequestConfig.custom().setProxy(proxy).build());
        final CloseableHttpResponse response = httpClientBuilder.build().execute(httpGet);
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 404) {
            throw new IllDataException("视频未部署");
        }
        if (statusCode == 500) {
            throw new IllDataException("请检查视频中转站是否在线");
        }
        return response;
    }
}
