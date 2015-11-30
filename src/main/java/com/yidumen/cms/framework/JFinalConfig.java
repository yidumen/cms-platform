package com.yidumen.cms.framework;

import com.alibaba.appengine.api.ds.DataSourceFactory;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.log.Log4jLoggerFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.yidumen.cms.controller.GoodsController;
import com.yidumen.cms.controller.LoginController;
import com.yidumen.cms.controller.VideoController;
import com.yidumen.cms.view.ajax.GoodsAjaxCtrl;
import com.yidumen.cms.view.ajax.RecordingAjaxCtrl;
import com.yidumen.cms.view.ajax.TagAjaxCtrl;
import com.yidumen.cms.view.ajax.VideoAjaxCtrl;
import com.yidumen.cms.view.wechat.MessageController;
import com.yidumen.cms.model.Account;
import com.yidumen.cms.model.Goods;
import com.yidumen.cms.model.Recording;
import com.yidumen.cms.model.Tag;
import com.yidumen.cms.model.Video;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 配置JFinal
 *
 * @author 蔡迪旻
 */
public final class JFinalConfig extends com.jfinal.config.JFinalConfig {
    @Override
    public void afterJFinalStart() {
        this.loadPropertyFile("appengine-service.properties");
    }

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setEncoding("UTF-8");
        me.setLoggerFactory(new Log4jLoggerFactory());

        ApiConfigKit.setDevMode(me.getDevMode());
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", LoginController.class);
        me.add("/video", VideoController.class);
        me.add("/goods", GoodsController.class);
        me.add("/ajax/video", VideoAjaxCtrl.class);
        me.add("/ajax/tag", TagAjaxCtrl.class);
        me.add("/ajax/goods", GoodsAjaxCtrl.class);
        me.add("/ajax/recording", RecordingAjaxCtrl.class);

        me.add("/wechat/message", MessageController.class, "/wechat");
    }

    @Override
    public void configPlugin(Plugins me) {
        DataSource ds = null;
        try {
            ds = (DataSource) new InitialContext().lookup("jdbc/yidumen");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        final ActiveRecordPlugin arp = new ActiveRecordPlugin(ds);
        me.add(arp);
        arp.setDialect(new MysqlDialect());
        arp.addMapping("Video", Video.class);
        arp.addMapping("Account", Account.class);
        arp.addMapping("Tag", Tag.class);
        arp.addMapping("Goods", Goods.class);
        arp.addMapping("recording", Recording.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new ResourceHandler());
        me.add(new SecurityHandler());
    }

}
