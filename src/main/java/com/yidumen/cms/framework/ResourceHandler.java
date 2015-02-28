package com.yidumen.cms.framework;

import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理路径<code>/oss/**</code>的请求，从OSS中获取静态资源。
 *
 * @author 蔡迪旻
 */
public class ResourceHandler extends Handler {
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (!target.startsWith("/oss")) {
            return;
        }
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(PropKit.get("oss.ydm.endpoint") + "/" + PropKit.get("oss.ydm.bucket"));
    }
}
