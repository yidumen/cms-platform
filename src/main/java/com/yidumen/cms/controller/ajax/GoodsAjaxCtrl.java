package com.yidumen.cms.controller.ajax;

import com.jfinal.aop.Before;
import com.jfinal.ext.render.DwzRender;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yidumen.cms.service.GoodsService;
import com.yidumen.cms.service.ServiceFactory;

/**
 * Created by cdm on 15/2/25.
 */
@Before(Tx.class)
public final class GoodsAjaxCtrl extends BaseAjaxCtrl {
    private final GoodsService service;

    public GoodsAjaxCtrl() {
        this.service = ServiceFactory.generateGoodsService();
    }

    public void info() {
        renderJson(service.findValidate());
    }

    public void unprocess() {
        renderJson(service.findUnProcess());
    }

    public void trashed() {
        renderJson(service.findTrash());
    }

    public void process() {
        service.process(this.getParaToLong(0));
        this.render(DwzRender.success("结缘信息已处理。"));
    }
    
    public void trash() {
        service.trash(this.getParaToLong(0));
        render(DwzRender.success("结缘信息已放入回收站。"));
    }
    
    public void recover() {
        service.recover(this.getParaToLong(0));
        render(DwzRender.success("结缘信息已恢复。"));
    }
}
