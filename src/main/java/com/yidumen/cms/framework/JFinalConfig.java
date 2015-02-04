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
import com.jfinal.plugin.spring.SpringPlugin;
import com.yidumen.cms.dao.Account;
import com.yidumen.cms.dao.Tag;
import com.yidumen.cms.dao.Video;
import com.yidumen.cms.view.controller.LoginController;
import com.yidumen.cms.view.controller.VideoController;

/**
 * 配置JFinal
 * @author 蔡迪旻
 */
public class JFinalConfig extends com.jfinal.config.JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setEncoding("UTF-8");
        me.setLoggerFactory(new Log4jLoggerFactory());
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", LoginController.class);
        me.add("/video", VideoController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        final ActiveRecordPlugin arp = new ActiveRecordPlugin(DataSourceFactory.getDataSource("ydm"));
        me.add(arp);
        arp.setDialect(new MysqlDialect());
        arp.addMapping("Video", Video.class);
        arp.addMapping("Account", Account.class);
        arp.addMapping("Tag", Tag.class);
        me.add(new SpringPlugin("classpath:applicationContext.xml"));
    }

    @Override
    public void configInterceptor(Interceptors me) {
    }

    @Override
    public void configHandler(Handlers me) {
    }
    
}
