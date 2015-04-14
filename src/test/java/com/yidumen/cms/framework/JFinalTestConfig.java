package com.yidumen.cms.framework;

import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.yidumen.cms.controller.GoodsController;
import com.yidumen.cms.controller.LoginController;
import com.yidumen.cms.controller.VideoController;
import com.yidumen.cms.controller.ajax.GoodsAjaxCtrl;
import com.yidumen.cms.controller.ajax.RecordingAjaxCtrl;
import com.yidumen.cms.controller.ajax.TagAjaxCtrl;
import com.yidumen.cms.controller.ajax.VideoAjaxCtrl;
import com.yidumen.cms.controller.wechat.MessageController;
import com.yidumen.cms.model.*;

/**
 * 配置JFinal
 * @author 蔡迪旻
 */
public final class JFinalTestConfig extends com.jfinal.config.JFinalConfig {
    public JFinalTestConfig() {
        this.loadPropertyFile("appengine-service.properties");
    }

    @Override
    public void afterJFinalStart() {
        
    }

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(false);
        me.setEncoding("UTF-8");

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
        final C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("database.ydm.url"), PropKit.get("database.ydm.username"), PropKit.get("database.ydm.password"), PropKit.get("database.ydm.driverClassName"));
        me.add(c3p0Plugin);
        final ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
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
    }
    
}
