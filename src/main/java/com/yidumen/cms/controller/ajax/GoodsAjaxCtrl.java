package com.yidumen.cms.controller.ajax;

import com.yidumen.cms.model.Goods;
import com.yidumen.cms.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年11月26日
 */
@RestController
@RequestMapping("goods")
public final class GoodsAjaxCtrl {
    @Autowired
    private GoodsService service;

    @RequestMapping("info")
    public List<Goods> info() {
        return service.findValidate();
    }

    @RequestMapping("unprocess")
    public List<Goods> unprocess() {
        return service.findUnProcess();
    }

    @RequestMapping("trashed")
    public List<Goods> trashed() {
        return service.findTrash();
    }

    @RequestMapping(value = "process/{id}", method = RequestMethod.POST)
    public void process(@PathVariable Long id) {
        service.process(id);
    }

    @RequestMapping(value = "trash/{id}")
    public void trash(@PathVariable Long id) {
        service.trash(id);
    }

    @RequestMapping(value = "recover/{id}")
    public void recover(@PathVariable Long id) {
        service.recover(id);
    }
}
