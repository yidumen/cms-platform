package com.yidumen.cms.view.ajax;

import com.yidumen.cms.entity.Goods;
import com.yidumen.cms.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
public class GoodsAjaxCtrl {
    @Autowired
    private GoodsService service;

    @Transactional(readOnly = true)
    @RequestMapping("info")
    public List<Goods> info() {
        return service.findValidate();
    }

    @Transactional(readOnly = true)
    @RequestMapping("unprocess")
    public List<Goods> unprocess() {
        return service.findUnProcess();
    }

    @Transactional(readOnly = true)
    @RequestMapping("trashed")
    public List<Goods> trashed() {
        return service.findTrash();
    }

    @Transactional
    @RequestMapping(value = "process/{id}", method = RequestMethod.POST)
    public void process(@PathVariable Long id) {
        service.process(id);
    }

    @Transactional
    @RequestMapping(value = "trash/{id}")
    public void trash(@PathVariable Long id) {
        service.trash(id);
    }

    @Transactional
    @RequestMapping(value = "recover/{id}")
    public void recover(@PathVariable Long id) {
        service.recover(id);
    }
}
